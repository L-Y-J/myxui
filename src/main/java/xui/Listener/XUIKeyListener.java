package xui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-22.
 */
public class XUIKeyListener implements KeyListener, IListener {
	/**
	 * Invoked when a key has been typed.
	 * See the class description for {@link java.awt.event.KeyEvent} for a definition of
	 * a key typed event.
	 *
	 * @param e
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("KeyTyped");
	}

	/**
	 * Invoked when a key has been pressed.
	 * See the class description for {@link java.awt.event.KeyEvent} for a definition of
	 * a key pressed event.
	 *
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("KeyPressed");
	}

	/**
	 * Invoked when a key has been released.
	 * See the class description for {@link java.awt.event.KeyEvent} for a definition of
	 * a key released event.
	 *
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("KeyReleased");
	}

	@Override
	public void setSource(LinkedHashMap actionSources) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		for (Iterator iterator = actionSources.values().iterator(); iterator.hasNext(); ) {
			Object source = iterator.next();
			Method addKeyListener = source.getClass().getMethod("addKeyListener", new Class[]{KeyListener.class});
			addKeyListener.invoke(source, this);
		}
	}
}
