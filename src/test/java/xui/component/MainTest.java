package xui.component;

import tinyioc.context.AbstractApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;
import xui.manager.XUIManagerUtils;

import javax.swing.*;
import java.lang.reflect.Method;

/**
 * Created by yongjie on 15-4-3.
 */
public class MainTest {

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
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("frame.xml");
		JFrame jframe2 = (JFrame) applicationContext.getBean("jframe2");
		XUIManagerUtils.BuildLayoutByManager(applicationContext);
		jframe2.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		test1();
	}
}
