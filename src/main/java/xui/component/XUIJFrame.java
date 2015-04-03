package xui.component;

import javax.swing.*;
import java.util.Map;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrame extends JFrame {
	public void setXuititle(String xuititle) {
		super.setTitle(xuititle);
	}

	public void setXuilocation(Map<String, String> xuilocation) {
		super.setLocation(Integer.parseInt(xuilocation.get("x")), Integer.parseInt(xuilocation.get("y")));
	}

	public void setXuisize(Map<String, String> xuisize) {
		super.setSize(Integer.parseInt(xuisize.get("x")), Integer.parseInt(xuisize.get("y")));
	}

	String xuititle;
	Map<String, String> xuilocation;
	Map<String, String> xuisize;
}
