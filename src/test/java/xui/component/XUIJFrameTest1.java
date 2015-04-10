package xui.component;

import tinyioc.context.ApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrameTest1 {

	private static void print(Object o) {
		System.out.println(o);
	}

	public static void test() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		JFrame jframe1 = (JFrame) applicationContext.getBean("jframe1");
		jframe1.setVisible(true);
	}


	public static void test1() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		JFrame jframe2 = (JFrame) applicationContext.getBean("jframe2");
		jframe2.add(new Button("1"));
		jframe2.add(new Button("2"));
		jframe2.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		test1();
	}
}
