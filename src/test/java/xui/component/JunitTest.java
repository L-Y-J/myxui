package xui.component;

import org.junit.Test;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yongjie on 15-4-3.
 */
public class JunitTest {

	@Test
	public void test() throws Exception {
		String test3 = "aaa";
		String[] split = test3.split(":");
		print(split.length, split[0]);
	}

	@Test
	public void URLResourceTest() throws Exception {
		URL url = new URL("http://www.springframework.org/schema/beans");
		URLConnection connection = url.openConnection();
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		char[] buffer = new char[1024];
		while (reader.read(buffer) != -1) {
			System.out.println(buffer);
		}
	}

	private void print(Object... o){
		for (Object o1 : o)
			System.out.println(o1.toString());
	}
}
