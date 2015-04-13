package xui.component;

import javax.swing.*;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-10.
 */
public class XUIJMenu extends JMenu {

	public void setMenuItem(LinkedHashMap menuItems){
		Collection values = menuItems.values();
		for (Object o : values) {
			if (o instanceof JMenuItem){
				super.add((JMenuItem) o);
			}else{
				System.out.println("错误类型："+o.toString());
			}
		}
	}
}
