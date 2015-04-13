package xui.component;

import javax.swing.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yongjie on 15-4-10.
 */
public class XUIJMenuBar extends JMenuBar {

	public void setJMenu(Map menuList){
		if (menuList instanceof LinkedHashMap){
			LinkedHashMap linkedHashMap = (LinkedHashMap) menuList;
			Collection values = linkedHashMap.values();
			for (Object o : values) {
				if (o instanceof JMenu){
					super.add((JMenu) o);
				}else {
					System.out.println("错误类型："+o.toString());
				}
			}
		}
	}
}
