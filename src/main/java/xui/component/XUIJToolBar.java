package xui.component;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-22.
 */
public class XUIJToolBar extends JToolBar {

	public void setJToolBar(LinkedHashMap jToolButtons){
		for (Iterator iterator = jToolButtons.values().iterator(); iterator.hasNext(); ) {
			Component jToolButton = (Component) iterator.next();
			super.add(jToolButton);
		}
	}
}
