package tinyioc.beans.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tinyioc.BeanReference;
import tinyioc.beans.AbstractBeanDefinitionReader;
import tinyioc.beans.BeanDefinition;
import tinyioc.beans.ConstructorValue;
import tinyioc.beans.PropertyValue;
import tinyioc.beans.io.ResourceLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public void registerBeanDefinitions(Document doc) throws Exception {
		Element root = doc.getDocumentElement();

		parseBeanDefinitions(root);
	}

	protected void parseBeanDefinitions(Element root) throws Exception {
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
	 *
	 * @param ele bean节点
	 */
	protected void processBeanDefinition(Element ele) throws Exception {
		if (ele.getTagName().equals("import")){
			String location = ele.getAttribute("resource");
			loadBeanDefinitions(location);
			return;
		}
		String name = ele.getAttribute("name");
		String className = ele.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		processProperty(ele, beanDefinition);
		beanDefinition.setBeanClassName(className);
		getRegistry().put(name, beanDefinition);
	}

	/**
	 * 解析property节点
	 *
	 * @param ele            property节点
	 * @param beanDefinition 保存节点信息
	 */
	private void processProperty(Element ele, BeanDefinition beanDefinition) {
		NodeList constructorNodes = ele.getElementsByTagName("constructor-arg");
		if (constructorNodes != null){
			processConstructor(constructorNodes, beanDefinition);
		}
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
				} else if (ref != null && ref.length() > 0) {
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				} else
					processPropertyChild(propertyEle, beanDefinition);
			}
		}
	}

	private void processConstructor(NodeList constructorNodes, BeanDefinition beanDefinition) {
		for (int i = 0; i < constructorNodes.getLength(); i++) {
			Node constructorNode = constructorNodes.item(i);
			if (constructorNode instanceof Element){
				Element constructorEle = (Element) constructorNode;
				String index = constructorEle.getAttribute("index");
				String value = constructorEle.getAttribute("value");
				String ref = constructorEle.getAttribute("ref");
				String type = constructorEle.getAttribute("type");
				beanDefinition.getConstructorArgs().add(new ConstructorValue(index, value, ref, type));
			}
		}
		return;
	}

	private Object castArgValueType(String value, String type) {
		switch (type){
			case "java.lang.String":
				return value;
			case "int":
			case "java.lang.Integer":
				return Integer.parseInt(value);
			case "float":
			case "java.lang.Float":
				return Float.parseFloat(value);
			case "double":
			case "java.lang.Double":
				return Double.parseDouble(value);
			default:
				return null;
		}
	}

	/**
	 * 解析property的孩子节点，例如List，Map等
	 *
	 * @param element        property节点
	 * @param beanDefinition 保存节点信息
	 */
	private void processPropertyChild(Element element, BeanDefinition beanDefinition) {
		NodeList nodeList = element.getChildNodes();         // 第一群孩子
		String propertyName = element.getAttribute("name");
		if (nodeList == null || nodeList.getLength() < 1)
			throw new IllegalArgumentException("xml配置文件错误，property节点："+propertyName+"没有值");
		for (int i = 0; i < nodeList.getLength(); i++) {       // 进入property孩子节点
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (ele.getTagName().equals("map")) {          // 进入map节点
					NodeList entryNodes = ele.getChildNodes();       // 第二群孩子
					if (entryNodes == null || entryNodes.getLength() < 1)
						throw new IllegalArgumentException("xml配置文件错误，map节点没有值");
					Map map = new LinkedHashMap();
					for (int j = 0; j < entryNodes.getLength(); j++) {   // 进入entry节点
						Node entryNode = entryNodes.item(j);
						if (entryNode instanceof Element) {
							Element entryEle = (Element) entryNode;
							String key = entryEle.getAttribute("key");
							String value = entryEle.getAttribute("value");
							Object obj = value;
							if (value == null || value.trim().equals("")) {
								value = entryEle.getAttribute("value-ref");
								obj = new BeanReference(value);
							}
							map.put(key, obj);
						}
					}
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, map));
				} else if (ele.getTagName().equals("list")) {       //进入list节点
					NodeList valueList = ele.getChildNodes();
					if (valueList == null || valueList.getLength() < 1)
						throw new IllegalArgumentException("xml配置文件错误，list节点没有值");
					List list = new ArrayList();
					for (int j = 0; j < valueList.getLength(); j++) {
						Node valueNode = valueList.item(j);
						if (valueNode instanceof Element) {
							Element valueEle = (Element) valueNode;
							if (valueEle.getTagName().equals("value")){
								list.add(valueEle.getTextContent());
							}
							if (valueEle.getTagName().equals("ref")) {
								list.add(new BeanReference(valueEle.getAttribute("bean")));
							}
						}
					}
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, list));
				}
			}
		}
	}
}
