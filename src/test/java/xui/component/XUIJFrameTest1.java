package xui.component;

import tinyioc.context.ApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrameTest1 {

	private static void print(Object... o) {
		for (Object o1 : o) {
			System.out.println(o1.toString());
		}

	}

	public static void test() throws Exception {
		JFrame jFrame = new JFrame();
		Method method = jFrame.getClass().getMethod("setLocation", new Class[]{int.class, int.class});
		method.invoke(jFrame, new Object[]{new Integer(200), new Integer(200)});
		jFrame.setVisible(true);
	}


	public static void test1() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		JFrame jframe2 = (JFrame) applicationContext.getBean("jframe2");
		jframe2.add(new Button("1"));
		jframe2.add(new Button("2"));
		jframe2.setVisible(true);
		print(jframe2.getJMenuBar());
		print(jframe2.getJMenuBar().getMenuCount());
		print(jframe2.getJMenuBar().getMenu(0));
		print(jframe2.getJMenuBar().getMenu(0).getName());
	}

	public static void main(String[] args) throws Exception {
		test1();
	}
}
