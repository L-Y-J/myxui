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
	public void test() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		JFrame jframe = (JFrame) applicationContext.getBean("jframe");
		jframe.setTitle("测试");
		jframe.setVisible(true);
	}
}
