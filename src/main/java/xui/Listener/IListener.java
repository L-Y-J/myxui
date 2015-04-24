package xui.listener;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

/**
 * Created by yongjie on 15-4-24.
 */
public interface IListener {

	public void setSource(LinkedHashMap actionSources) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
