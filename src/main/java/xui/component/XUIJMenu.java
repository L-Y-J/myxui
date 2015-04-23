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
			super.add((JMenuItem) o);
		}
	}
}
