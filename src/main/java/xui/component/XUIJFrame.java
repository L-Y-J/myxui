package xui.component;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-3.
 */
public class XUIJFrame extends JFrame {

	public void setComponent(LinkedHashMap component) {
		String layoutName = this.getContentPane().getLayout().getClass().getName();
		if (layoutName.equals("java.awt.FlowLayout")) {
			Collection values = component.values();
			for (Object o : values) {
				if (o instanceof Component) {
					this.add((Component) o);
				}
			}
		} else if (layoutName.equals("java.awt.BorderLayout")){
			for (java.util.Iterator iterator = component.keySet().iterator(); iterator.hasNext(); ) {
				Object key = iterator.next();
				if (key.equals("WEST"))
					this.add((Component) component.get(key), BorderLayout.WEST);
				else if (key.equals("EAST"))
					this.add((Component) component.get(key), BorderLayout.EAST);
				else if (key.equals("NORTH"))
					this.add((Component) component.get(key), BorderLayout.NORTH);
				else if (key.equals("SOUTH"))
					this.add((Component) component.get(key), BorderLayout.SOUTH);
				else if (key.equals("CENTER"))
					this.add((Component) component.get(key), BorderLayout.CENTER);
			}
		}
	}
}
