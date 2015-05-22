package xui.component;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-5-22.
 */
public class XUITreeNode extends DefaultMutableTreeNode {

	/**
	 * Creates a tree node with no parent, no children, but which allows
	 * children, and initializes it with the specified user object.
	 *
	 * @param userObject an Object provided by the user that constitutes
	 *                   the node's data
	 */
	public XUITreeNode(Object userObject) {
		super(userObject);
	}

	public void setChildrenNode(LinkedHashMap childrenNode){
		for (Iterator iterator = childrenNode.values().iterator(); iterator.hasNext();){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) iterator.next();
			super.add(node);
		}
	}
}
