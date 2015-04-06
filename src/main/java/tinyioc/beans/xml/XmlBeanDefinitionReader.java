package tinyioc.beans.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tinyioc.BeanReference;
import tinyioc.beans.AbstractBeanDefinitionReader;
import tinyioc.beans.BeanDefinition;
import tinyioc.beans.PropertyValue;
import tinyioc.beans.io.ResourceLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yihua.huang@dianping.com
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
		doLoadBeanDefinitions(inputStream);
	}

	protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputStream);
		// 解析bean
		registerBeanDefinitions(doc);
		inputStream.close();
	}

	public void registerBeanDefinitions(Document doc) {
		Element root = doc.getDocumentElement();

		parseBeanDefinitions(root);
	}

	protected void parseBeanDefinitions(Element root) {
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}

	/**
	 * 解析bean节点
	 * @param ele bean节点
	 */
	protected void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("name");
		String className = ele.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		processProperty(ele, beanDefinition);
		beanDefinition.setBeanClassName(className);
		getRegistry().put(name, beanDefinition);
	}

	/**
	 * 解析property节点
	 * @param ele property节点
	 * @param beanDefinition 保存节点信息
	 */
	private void processProperty(Element ele, BeanDefinition beanDefinition) {
		NodeList propertyNode = ele.getElementsByTagName("property");
		for (int i = 0; i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if (node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");
				String ref = propertyEle.getAttribute("ref");
				if (value != null && value.length() > 0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				}
				else if (ref != null && ref.length() > 0){
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
				else
					proccessPropertyChilds(propertyEle, beanDefinition);
			}
		}
	}

	/**
	 * 解析property的孩子节点，例如List，Map等
	 * @param element property节点
	 * @param beanDefinition 保存节点信息
	 */
	private void proccessPropertyChilds(Element element, BeanDefinition beanDefinition) {
		NodeList nodeList = element.getChildNodes();         // 第一群孩子
		String propertyName = element.getAttribute("name");
		if (nodeList == null || nodeList.getLength() < 1)
			throw new IllegalArgumentException("configuration file error(property node have no child)");
		for (int i = 0; i < nodeList.getLength(); i++){       // 进入property孩子节点
			Node node = nodeList.item(i);
			if (node instanceof Element){
				Element ele = (Element) node;
				if (ele.getTagName().equals("map")){          // 进入map节点
					NodeList entryNodes = ele.getChildNodes();       // 第二群孩子
					if (entryNodes == null || entryNodes.getLength() < 1)
						throw new IllegalArgumentException("configuration file error(map node have no child)");
					Map map = new HashMap();
					for (int j = 0; j<entryNodes.getLength(); j++){   // 进入entry节点
						Node entryNode = entryNodes.item(j);
						if (entryNode instanceof Element){
							Element entryEle = (Element) entryNode;
							NodeList valueNodes = entryEle.getChildNodes();    // 第三群孩子
							if (valueNodes == null || valueNodes.getLength() < 1)
								throw new IllegalArgumentException("configuration file error(entry node have no child)");
							for (int k = 0; k < valueNodes.getLength(); k++){       // 进入value节点
								Node valueNode = valueNodes.item(k);
								if (valueNode instanceof Element){
									Element valueEle = (Element) valueNode;
									String key = entryEle.getAttribute("key");
									String value = valueEle.getFirstChild().getNodeValue();
									map.put(key, value);
								}
							}
						}
					}
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, map));
				}
				else if (ele.getTagName().equals("list")){
					//TODO 支持list解析
				}
			}
		}
	}
}
