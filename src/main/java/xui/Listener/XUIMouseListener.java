package xui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-22.
 */
public class XUIMouseListener implements MouseListener, IListener {
	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
	 *
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mousePressed");
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased");
	}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}

	@Override
	public void setSource(LinkedHashMap actionSources) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		for (Iterator iterator = actionSources.values().iterator(); iterator.hasNext(); ) {
			Object source = iterator.next();
			Method addMouseListener = source.getClass().getMethod("addMouseListener", new Class[]{MouseListener.class});
			addMouseListener.invoke(source, this);
		}
	}
}
