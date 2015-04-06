package xui.component;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrame extends JFrame {
	public void setXuiTitle(String xuititle) {
		super.setTitle(xuititle);
	}

	public void setXuiLocation(Map<String, String> xuilocation) {
		super.setLocation(Integer.parseInt(xuilocation.get("x")), Integer.parseInt(xuilocation.get("y")));
	}

	public void setXuiSize(Map<String, String> xuisize) {
		super.setSize(Integer.parseInt(xuisize.get("x")), Integer.parseInt(xuisize.get("y")));
	}

	public void setXuiClose(String xuiClose) {
		if (xuiClose.equals("EXIT_ON_CLOSE"))
			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (xuiClose.equals("HIDE_ON_CLOSE"))
			super.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		if (xuiClose.equals("DISPOSE_ON_CLOSE"))
			super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		if (xuiClose.equals("DO_NOTHING_ON_CLOSE"))
			super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public void setLayout(LayoutManager layout) {
		super.setLayout(layout);
	}

	String xuiTitle;
	String xuiClose;
	Map<String, String> xuiLocation;
	Map<String, String> xuiSize;
	LayoutManager layout;
}
