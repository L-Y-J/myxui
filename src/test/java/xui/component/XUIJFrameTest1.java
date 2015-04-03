package xui.component;

import tinyioc.context.ApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;

import javax.swing.*;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrameTest1 {

	public static void test2() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		JFrame jframe = (JFrame) applicationContext.getBean("xuijframe");
		jframe.setVisible(true);
	}

	public static void main(String[] args) throws Exception{
		test2();
	}
}
