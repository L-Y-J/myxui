package xui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-22.
 */
public class XUIMouseMotionListener implements MouseMotionListener, IListener {
	/**
	 * Invoked when a mouse button is pressed on a component and then
	 * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
	 * delivered to the component where the drag originated until the
	 * mouse button is released (regardless of whether the mouse position
	 * is within the bounds of the component).
	 * <p/>
	 * Due to platform-dependent Drag&Drop implementations,
	 * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
	 * Drag&Drop operation.
	 *
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("mouseDragged");
	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component
	 * but no buttons have been pushed.
	 *
	 * @param e
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("mouseMoved");
	}

	@Override
	public void setSource(LinkedHashMap actionSources) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		for (Iterator iterator = actionSources.values().iterator(); iterator.hasNext(); ) {
			Object source = iterator.next();
			Method addMouseMotionListener = source.getClass().getMethod("addMouseMotionListener", new Class[]{MouseMotionListener.class});
			addMouseMotionListener.invoke(source, this);
		}
	}
}
