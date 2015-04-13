package xui.component;

import javax.swing.*;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrame extends JFrame {
	public void setXuiTitle(String xuititle) {
		super.setTitle(xuititle);
	}

	public void setXuiLocation(int x, int y) {
		super.setLocation(x, y);
	}

	public void setXuiSize(int x, int y){
		super.setSize(x, y);
	}
}
