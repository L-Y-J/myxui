package xui.manager;

import tinyioc.beans.BeanDefinition;
import tinyioc.context.AbstractApplicationContext;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yongjie on 15-4-23.
 */
public class XUIManagerUtils {

	public static final void BuildLayoutByManager(AbstractApplicationContext applicationContext) throws Exception {
		Map<String,BeanDefinition> map = applicationContext.getBeanFactory().getBeanDefinitionMap();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
			String name = iterator.next();
			BeanDefinition definition = map.get(name);
			if (definition.getBeanClassName().equals("xui.manager.XUILayoutManager")){
				XUILayoutManager manager = (XUILayoutManager) applicationContext.getBean(name);
				manager.BuildLayout();
			}
		}
	}
}
