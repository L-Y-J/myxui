package tinyioc.beans.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author yihua.huang@dianping.com
 */
public class ResourceLoader {

    public Resource getResource(String location) throws MalformedURLException {
		String[] typeAndPath = location.split(":");
		if (typeAndPath.length > 1){
			if (typeAndPath[0].startsWith("classpath")){
				location = typeAndPath[1];
			}
			else if (typeAndPath[0].startsWith("http")){
				URL resource = new URL(typeAndPath[1]);
				return new UrlResource(resource);
			}
		}
		URL resource = this.getClass().getClassLoader().getResource(location);
		return new UrlResource(resource);
    }
}
