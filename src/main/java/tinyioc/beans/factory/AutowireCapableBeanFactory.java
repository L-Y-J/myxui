package tinyioc.beans.factory;

import tinyioc.BeanReference;
import tinyioc.beans.BeanDefinition;
import tinyioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 可自动装配内容的BeanFactory
 * 
 * @author yihua.huang@dianping.com
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
		// TODO 先通过属性注入，再通过方法注入
		// applyPropertyValues(bean, beanDefinition);
		applyPropertyValuesByMethod(bean, beanDefinition);
		return bean;
	}

	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		return beanDefinition.getBeanClass().newInstance();
	}

	/**
	 * 通过反射bean属性，设置bean的属性值
	 * param bean 设置的实体对象
	 * @param mbd 需要注入bean的属性值，属性值为引用对象则递归设置引用对象的值
	 * @throws Exception
	 */
	protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
			declaredField.setAccessible(true);
			Object value = propertyValue.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}
			if (declaredField.getType().toString().equals("int")){
				value = Integer.parseInt(value.toString());
			}
			declaredField.set(bean, value);
		}
	}

	/**
	 * 通过反射bean方法，设置bean的属性值
	 * @param bean 设置的实体对象
	 * @param mbd 需要注入bean的属性值，属性值为引用对象则递归设置引用对象的值
	 * @throws Exception
	 */
	protected void applyPropertyValuesByMethod(Object bean, BeanDefinition mbd) throws Exception {
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
			Method method = bean.getClass()
					.getDeclaredMethod("set" + charUp(propertyValue.getName()), new Class[]{declaredField.getType()});

			Object value = propertyValue.getValue();
			if (value instanceof BeanReference){
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}
			method.invoke(bean, new Object[]{ value });
		}
	}

	private String charUp(String s){
		char[] chars = s.toCharArray();
		char temp = chars[0];
		if (temp >= 65 && temp <= 90)
			return s;
		chars[0] = (char) (temp - ('a'-'A'));
		return new String(chars);
	}
}
