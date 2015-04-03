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
		// applyPropertyValues(bean, beanDefinition);
		applyPropertyValuesByMethod(bean, beanDefinition);
		return bean;
	}

	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		return beanDefinition.getBeanClass().newInstance();
	}

	/**
	 * 直接设置bean的属性值
	 * param bean
	 * @param mbd
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

	protected void applyPropertyValuesByMethod(Object bean, BeanDefinition mbd) throws Exception {
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName().toLowerCase());
			Method method = bean.getClass()
					.getDeclaredMethod("set" + propertyValue.getName(), new Class[]{declaredField.getType()});
			method.invoke(bean, new Object[]{propertyValue.getValue()});
		}
	}
}
