package tinyioc.beans.factory;

import tinyioc.BeanReference;
import tinyioc.beans.BeanDefinition;
import tinyioc.beans.ConstructorValue;
import tinyioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 可自动装配内容的BeanFactory
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		applyPropertyValues(bean, beanDefinition);
		return bean;
	}

	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		List constructorArgs = beanDefinition.getConstructorArgs();
		if (constructorArgs.size() > 0){
			Class []parameterTypes = new Class[constructorArgs.size()];
			Object []initArgs = new Object[constructorArgs.size()];
			for (int i = 0; i < constructorArgs.size(); i++) {
				ConstructorValue constructorValue = (ConstructorValue) constructorArgs.get(i);
				// todo fix:添加更多的基础类型判断
				if (constructorValue.getType().equals("int"))
					parameterTypes[i] = int.class;
				else
					parameterTypes[i] = Class.forName(constructorValue.getType());
				if (!"".equals(constructorValue.getValue().trim()))
					initArgs[i] = castConstructorArgs(constructorValue.getValue(), constructorValue.getType());
				else{
					initArgs[i] = getBean(constructorValue.getRef());
				}
			}
			return beanDefinition.getBeanClass().getConstructor(parameterTypes).newInstance(initArgs);
		}
		return beanDefinition.getBeanClass().newInstance();
	}

	private Object castConstructorArgs(String value, String type) throws Exception {
		switch (type){
			case "java.lang.String":
				return value;
			case "java.lang.Object":
				return value.toString();
			case "int":
				return (int)Integer.parseInt(value);
			case "java.lang.Integer":
				return Integer.parseInt(value);
			case "long":
			case "java.lang.Long":
				return Long.parseLong(value);
			case "float":
			case "java.lang.Float":
				return Float.parseFloat(value);
			case "double":
			case "java.lang.Double":
				return Double.parseDouble(value);
			case "boolean":
			case "java.lang.Boolean":
				return value.equals("true")?true:false;
			case "java.net.URL":
				return this.getClass().getClassLoader().getResource(value);
			default:
				throw new Exception("不支持的构造参数类型:"+type);
		}
	}

	/**
	 * 通过反射bean属性，设置bean的属性值
	 * param bean 设置的实体对象
	 *
	 * @param mbd 需要注入bean的属性值，属性值为引用对象则递归设置引用对象的值
	 * @throws Exception
	 */
	protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Field declaredField = null;
			try {
				declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
			} catch (NoSuchFieldException e) {
				applyPropertyValuesByMethod(bean, propertyValue);
				continue;
			}
			declaredField.setAccessible(true);
			Object value = propertyValue.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}
			String fieldTypeName = declaredField.getType().getName();
			if (fieldTypeName.equals("int") || fieldTypeName.equals("java.lang.Integer"))
				value = Integer.parseInt(value.toString());
			else if (fieldTypeName.equals("long") || fieldTypeName.equals("java.lang.Long"))
				value = Long.parseLong(value.toString());
			else if (fieldTypeName.equals("float") || fieldTypeName.equals("java.lang.Float"))
				value = Float.parseFloat(value.toString());
			else if (fieldTypeName.equals("double") || fieldTypeName.equals("java.lang.Double"))
				value = Double.parseDouble(value.toString());
			else if (fieldTypeName.equals("java.lang.String"))
				value = value.toString();
			else if (fieldTypeName.equals("char") || fieldTypeName.equals("java.lang.Character"))
				value = value.toString().toCharArray()[0];
			else if (fieldTypeName.equals("boolean") || fieldTypeName.equals("java.lang.Boolean"))
				value = value.toString().toLowerCase().equals("true") ? true : false;
			else if (fieldTypeName.equals("java.util.LinkedHashMap")){
				castMapArgs((LinkedHashMap) value);
			}
			else if (fieldTypeName.equals("java.util.List")){
				// TODO 需要测试field注入List是否成功
				List list = (List)value;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) instanceof BeanReference){
						Object obj = getBean(((BeanReference) list.get(i)).getName());
						list.set(i, obj);
					}
				}
				value = list;
			}
			try {
				declaredField.set(bean, value);
			} catch (IllegalArgumentException e) {
				applyPropertyValuesByMethod(bean, propertyValue);
			}
		}
	}


	protected void applyPropertyValuesByMethod(Object bean, PropertyValue propertyValue) throws Exception {
		String methodName = "set" + propertyValue.getName().toLowerCase();
		Object value = propertyValue.getValue();
		Method[] methods = bean.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().toLowerCase().equals(methodName)) {
				Class<?>[] parameterTypes = methods[i].getParameterTypes();
				Object args = castArgsType(value, parameterTypes);
				if (args == null)
					continue;
				if (args instanceof List){
					Object[] argList = new Object[((List) args).size()];
					for (int j = 0; j < argList.length; j++) {
						argList[j] = ((List) args).get(j);
					}
					methods[i].invoke(bean, argList);
					break;
				}
				methods[i].invoke(bean, args);
				break;
			}
		}
	}

	private Object castArgsType(Object value, Class[] parameterTypes) throws Exception {
		if (value instanceof BeanReference){
			BeanReference beanReference = (BeanReference) value;
			return castArgsType(getBean(beanReference.getName()), parameterTypes);
		}
		else if (value instanceof List){
			List argsList = (List) value;
			if (argsList.size() != parameterTypes.length)
				return null;
			return castListArgs(argsList, parameterTypes);
		}
		else if (value instanceof Map){
			LinkedHashMap argsMap = (LinkedHashMap) value;
			return castMapArgs(argsMap);
		}
		else {
			if (parameterTypes.length != 1)
				return null;
			String className = parameterTypes[0].getName();
			Object args = null;
			args = castOneArgs(value, className);
			return args;
		}
	}

	private LinkedHashMap castMapArgs(LinkedHashMap argsMap) throws Exception {
		for (Iterator iterator = argsMap.keySet().iterator(); iterator.hasNext(); ) {
			Object key = iterator.next();
			Object value = argsMap.get(key);
			if (value instanceof BeanReference){
				Object o = getBean(((BeanReference) value).getName());
				argsMap.put(key, o);
			}
		}
		return argsMap;
	}

	private List castListArgs(List argsList, Class[] parameterTypes) throws Exception {
		for (int i = 0; i < parameterTypes.length; i++) {
			if (argsList.get(i) instanceof BeanReference){
				BeanReference beanReference = (BeanReference) argsList.get(i);
				argsList.set(i, getBean(beanReference.getName()));
			}
			else if (argsList.get(i) instanceof List) {
				argsList.set(i, (List)argsList.get(i));
			}
			else{
				Object args = castOneArgs(argsList.get(i), parameterTypes[0].getName());
				argsList.set(i, args);
			}
		}
		return argsList;
	}

	private Object castOneArgs(Object value, String className) {
		Object args = null;
		if (className.equals("int") || className.equals("java.lang.Integer"))
			args = Integer.parseInt(value.toString());
		else if (className.equals("long") || className.equals("java.lang.Long"))
			args = Long.parseLong(value.toString());
		else if (className.equals("float") || className.equals("java.lang.Float"))
			args = Float.parseFloat(value.toString());
		else if (className.equals("double") || className.equals("java.lang.Double"))
			args = Double.parseDouble(value.toString());
		else if (className.equals("char") || className.equals("java.lang.Character"))
			args = value.toString().toCharArray()[0];
		else if (className.equals("boolean") || className.equals("java.lang.Boolean"))
			args = value.toString().equals("true") ? true : false;
		else if (className.equals("int") || className.equals("java.lang.Integer"))
			args = Integer.parseInt(value.toString());
		else if (className.equals("long") || className.equals("java.lang.Long"))
			args = Long.parseLong(value.toString());
		else if (className.equals("float") || className.equals("java.lang.Float"))
			args = Float.parseFloat(value.toString());
		else if (className.equals("double") || className.equals("java.lang.Double"))
			args = Double.parseDouble(value.toString());
		else if (className.equals("char") || className.equals("java.lang.Character"))
			args = value.toString().toCharArray()[0];
		else if (className.equals("boolean") || className.equals("java.lang.Boolean"))
			args = value.toString().equals("true") ? true : false;
		else
			args = value;
		return args;
	}
}
