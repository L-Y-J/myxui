package xui.Listener;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by yongjie on 15-4-22.
 */
public class ToolBarAction extends AbstractAction {
	/**
	 * Creates an {@code Action} with the specified name and small icon.
	 *
	 * @param name the name ({@code Action.NAME}) for the action; a
	 *             value of {@code null} is ignored
	 * @param icon the small icon ({@code Action.SMALL_ICON}) for the action; a
	 *             value of {@code null} is ignored
	 */
	public ToolBarAction(String name, Icon icon) {
		super(name, icon);
	}

	public ToolBarAction(String name, String icon) {
		super(name, new ImageIcon(icon));
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
