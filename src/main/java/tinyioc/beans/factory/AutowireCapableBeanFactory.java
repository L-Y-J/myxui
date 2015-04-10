package tinyioc.beans.factory;

import tinyioc.BeanReference;
import tinyioc.beans.BeanDefinition;
import tinyioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
		return beanDefinition.getBeanClass().newInstance();
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
			try {
				declaredField.set(bean, value);
			} catch (IllegalArgumentException e) {
				applyPropertyValuesByMethod(bean, propertyValue);
			}
		}
	}


	protected void applyPropertyValuesByMethod(Object bean, PropertyValue propertyValue) throws Exception {
		String methodName = "set" + firstCharUp(propertyValue.getName().toLowerCase());
		Object value = propertyValue.getValue();
		if (value instanceof BeanReference) {
			BeanReference beanReference = (BeanReference) value;
			value = getBean(beanReference.getName());
		}
		Method[] methods = bean.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				Class<?>[] parameterTypes = methods[i].getParameterTypes();
				try {
					if (parameterTypes.length == 1) {
						methods[i].invoke(bean, value);
					} else {
						List temp = (List) value;
						Object[] args = temp.toArray();
						castArgsType(args, parameterTypes);
						methods[i].invoke(bean, args);
					}
					break;
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}
	}

	private String firstCharUp(String s) {
		char[] chars = s.toCharArray();
		char temp = chars[0];
		if (temp >= 65 && temp <= 90)
			return s;
		chars[0] = (char) (temp - ('a' - 'A'));
		return new String(chars);
	}

	private void castArgsType(Object[] args, Class[] parameterTypes){
		if (args.length != parameterTypes.length)
			throw new IllegalArgumentException();
		for (int i = 0; i < parameterTypes.length; i++) {
			String className = parameterTypes[i].getName();
			if (className.equals("int") || className.equals("java.lang.Integer"))
				args[i] = Integer.parseInt(args[i].toString());
			else if (className.equals("long") || className.equals("java.lang.Long"))
				args[i] = Long.parseLong(args[i].toString());
			else if (className.equals("float") || className.equals("java.lang.Float"))
				args[i] = Float.parseFloat(args[i].toString());
			else if (className.equals("double") || className.equals("java.lang.Double"))
				args[i] = Double.parseDouble(args[i].toString());
			else if (className.equals("char") || className.equals("java.lang.Character"))
				args[i] = args[i].toString().toCharArray()[0];
			else if (className.equals("boolean") || className.equals("java.lang.Boolean"))
				args[i] = args[i].toString().equals("true") ? true : false;
		}
	}
}
