package xui.component;

import org.junit.Test;
import tinyioc.beans.BeanDefinition;
import tinyioc.context.ClassPathXmlApplicationContext;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yongjie on 15-4-3.
 */
public class JunitTest {

	@Test
	public void test() throws Exception {
		print(System.getProperties());
	}

	@Test
	public void testBeanFactory() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("frame.xml");
		Map<String,BeanDefinition> map = context.getBeanFactory().getBeanDefinitionMap();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
			String name = iterator.next();
			BeanDefinition beanDefinition = map.get(name);
			print(name, beanDefinition.getBeanClassName());
		}
	}

	@Test
	public void URLResourceTest() throws Exception {
		URL url = new URL("http://www.springframework.org/schema/beans");
		URLConnection connection = url.openConnection();
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		char[] buffer = new char[1024];
		while (reader.read(buffer) != -1) {
			System.out.println(buffer);
		}
	}

	private void print(Object... o){
		for (Object o1 : o)
			System.out.println(o1.toString());
	}
}
