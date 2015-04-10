package xui.component;

import org.junit.Test;
import tinyioc.context.ApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;

import javax.swing.*;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrameTest {

	@Test
	public void test() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		JFrame jframe = (JFrame) applicationContext.getBean("jframe1");
		jframe.setVisible(true);
	}

	private void print(Object... o){
		for (Object o1 : o)
			System.out.println(o1.toString());
	}
}
