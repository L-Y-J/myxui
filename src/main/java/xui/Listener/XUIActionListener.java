package xui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-22.
 */
public class XUIActionListener implements ActionListener, IListener{
	/**
	 * Invoked when an action occurs.
	 *
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("事件触发：" + actionCommand);
	}

	@Override
	public void setSource(LinkedHashMap actionSources) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		for (Iterator iterator = actionSources.values().iterator(); iterator.hasNext(); ) {
			Object source = iterator.next();
			Method addActionListener = source.getClass().getMethod("addActionListener", new Class[]{ActionListener.class});
			addActionListener.invoke(source, this);

		}
	}
}
