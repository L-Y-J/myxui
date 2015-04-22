package xui.manager;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-21.
 */
public class XUILayoutManager {

	private Component father;
	LinkedHashMap child;
	private LayoutManager layout;

	public void BuildLayout() {
		if (father instanceof JFrame) {
			JFrame jFrame = (JFrame) father;
			jFrame.setLayout(layout);
			if (layout instanceof BorderLayout) {
				for (Iterator iterator = child.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					if (key.equals("WEST"))
						jFrame.add((Component) child.get(key), BorderLayout.WEST);
					else if (key.equals("EAST"))
						jFrame.add((Component) child.get(key), BorderLayout.EAST);
					else if (key.equals("NORTH"))
						jFrame.add((Component) child.get(key), BorderLayout.NORTH);
					else if (key.equals("SOUTH"))
						jFrame.add((Component) child.get(key), BorderLayout.SOUTH);
					else if (key.equals("CENTER"))
						jFrame.add((Component) child.get(key), BorderLayout.CENTER);
				}
			} else if (layout instanceof FlowLayout) {
				for (Object o : child.values()) {
					jFrame.add((Component) o);
				}
			}
		}
	}

	public void setChild(LinkedHashMap child) {
		this.child = child;
	}

	public void setLayout(LayoutManager layout) {
		this.layout = layout;
	}

	public void setFather(Component father) {
		this.father = father;
	}
}
