package xui.component;

import tinyioc.context.ApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;

import java.awt.*;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrameTest1 {

	private static void print(Object o){
		System.out.println(o);
	}

	public static void test2() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xui_1.xml");
		XUIJFrame jframe = (XUIJFrame) applicationContext.getBean("xuiJFrame");
		jframe.add(new Button("1"), BorderLayout.NORTH);
		jframe.add(new Button("2"), BorderLayout.SOUTH);
		jframe.add(new Button("3"), BorderLayout.EAST);
		jframe.add(new Button("4"), BorderLayout.CENTER);
		jframe.add(new Button("5"), BorderLayout.WEST);
		jframe.setVisible(true);
		print(jframe.getLayout().getClass().toString());

	}

	public static void main(String[] args) throws Exception{
		test2();
	}
}
