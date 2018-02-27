package winter.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtil {
	
	/**
	 * 解析由路径指定的xml文档
	 * @param path XML文件路径
	 */
    public static Document queryXml(String path){
        try{
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文档读入DOM解析器
            Document doc = dbBuilder.parse(path);
            return doc;
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    
    /**
	 * 解析String类型的xml
	 * @param stringxml String类型的xml文档内容
	 */
    public static Document queryStringXml(String stringxml){
        try{
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文档读入DOM解析器
            Document doc=dbBuilder.parse(new ByteArrayInputStream(stringxml.getBytes()));
            return doc;
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据节点名及其属性值查找对应的节点  并将其与其子节点的全部属性名/值以Map<String,List<Map<String,String>>>返回
     * List<Map<String,String>> 存储一个节点的全部属性名、值
     * 子节点名必须相同
     * @param doc 
     * @param nodeName 节点名
     * @param attr	属性名
     * @param dealkey 属性值
     * @return
     */
    public static Map<String,List<Map<String,String>>> analysisDoc(Document doc,String nodeName, String attr, String dealkey){
    	Map<String,List<Map<String,String>>> map = new HashMap<String,List<Map<String,String>>>();
        try{
            NodeList nodeList = doc.getElementsByTagName(nodeName);
            for (int i = 0; i < nodeList.getLength(); i++) {
            	if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
            		Element nodeElement = (Element)nodeList.item(i);
            		if (nodeElement.getAttribute(attr).equals(dealkey)) {
            			List<Map<String,String>> nodeArrayList = new ArrayList<Map<String,String>>();
    					Map<String,String> nodeMap = new HashMap<String,String>();
    					for (int attributeLength = 0; attributeLength < nodeElement.getAttributes().getLength(); attributeLength++) {
    						nodeElement.getAttributes().item(attributeLength).getNodeName();
    						nodeMap.put(nodeElement.getAttributes().item(attributeLength).getNodeName(), nodeElement.getAttributes().item(attributeLength).getNodeValue());
						}
    					nodeArrayList.add(nodeMap);
    					NodeList childNodesList = nodeElement.getChildNodes();
    					List<Map<String,String>> childNodesArrayList = new ArrayList<Map<String,String>>();
    					Map<String,String> childNodesMap;
    					String childNodesName = "";
    					for (int j = 0; j < childNodesList.getLength(); j++) {
    		            	if (childNodesList.item(j).getNodeType() == Node.ELEMENT_NODE) {
    		            		Element childNodes = (Element)childNodesList.item(j);
    		            		childNodesMap = new HashMap<String,String>();
    	    					for (int attributeLength = 0; attributeLength < childNodes.getAttributes().getLength(); attributeLength++) {
    	    						childNodesMap.put(childNodes.getAttributes().item(attributeLength).getNodeName(), childNodes.getAttributes().item(attributeLength).getNodeValue());
    							}
    	    					childNodesArrayList.add(childNodesMap);
    	    					childNodesName = childNodes.getNodeName();
							}
						}
    					map.put(nodeName, nodeArrayList);
    					map.put("feilds", childNodesArrayList);
    				}
				}
			}
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }
    
    //获取全部节点
    public static List<Map<String,List<Map<String,String>>>> analysisDoc(Document doc,String nodeName){
    	List<Map<String,List<Map<String,String>>>> cacheList = new ArrayList<Map<String,List<Map<String,String>>>>();
    	Map<String,List<Map<String,String>>> map = null;
        try{
            NodeList nodeList = doc.getElementsByTagName(nodeName);
            for (int i = 0; i < nodeList.getLength(); i++) {
            	map = new HashMap<String,List<Map<String,String>>>();
            	if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
            		Element nodeElement = (Element)nodeList.item(i);
        			List<Map<String,String>> nodeArrayList = new ArrayList<Map<String,String>>();
					Map<String,String> nodeMap = new HashMap<String,String>();
					for (int attributeLength = 0; attributeLength < nodeElement.getAttributes().getLength(); attributeLength++) {
						nodeElement.getAttributes().item(attributeLength).getNodeName();
						nodeMap.put(nodeElement.getAttributes().item(attributeLength).getNodeName(), nodeElement.getAttributes().item(attributeLength).getNodeValue());
					}
					nodeArrayList.add(nodeMap);
					NodeList childNodesList = nodeElement.getChildNodes();
					List<Map<String,String>> childNodesArrayList = new ArrayList<Map<String,String>>();
					Map<String,String> childNodesMap;
					String childNodesName = "";
					for (int j = 0; j < childNodesList.getLength(); j++) {
		            	if (childNodesList.item(j).getNodeType() == Node.ELEMENT_NODE) {
		            		Element childNodes = (Element)childNodesList.item(j);
		            		childNodesMap = new HashMap<String,String>();
	    					for (int attributeLength = 0; attributeLength < childNodes.getAttributes().getLength(); attributeLength++) {
	    						childNodesMap.put(childNodes.getAttributes().item(attributeLength).getNodeName(), childNodes.getAttributes().item(attributeLength).getNodeValue());
							}
	    					childNodesArrayList.add(childNodesMap);
	    					childNodesName = childNodes.getNodeName();
						}
					}
					map.put(nodeName, nodeArrayList);
					map.put(childNodesName, childNodesArrayList);
					cacheList.add(map);
				}
			}
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return cacheList;
    }
    
    //将一个map转为一条xml
    public static String mapToXML(Map<String,String> map,String nodename) {  
    	StringBuffer sb = new StringBuffer();
        sb.append("<" + nodename + " ");
        map.keySet().stream().forEach(key ->{
        	String value = map.get(key);
        	if(null == value){
        		value = "";
        	}
			if(value.indexOf("(")!=-1){
				value = value.replace("(","（");
			}
			if(value.indexOf(")")!=-1){
				value = value.replace(")","）");
			}
			if(value.indexOf(";")!=-1){
				value = value.replace(";","；");
			}
			if(value.indexOf("'")!=-1){
				value = value.replace("'","’");
			}
			if(value.indexOf("\"")!=-1){
				value = value.replace("\"","”");
			}
			if(value.indexOf(",")!=-1){
				value = value.replace(",","，");
			}
			if(value.indexOf(">")!=-1){
				value = value.replace(">","＞");
			}
			if(value.indexOf("<")!=-1){
				value = value.replace("<","＜");
			}
			if(value.indexOf("%")!=-1){
				value = value.replace("%","％");
			}
        	sb.append(key + "='" + value + "' ");
        });
        sb.append("/>");
        return sb.toString();
    } 
    
    /** 
     *  doc xmldate被解析后的数据
	 *  @param  xmlData 符合招投标类bid结构的xmldata或与之类似结构的xmldate
	 *  @return  map key[feild:key] value[feild:value];
     *  */
    public static Map<String,Object> xmlDate(String xmlData){
		Document doc= XMLUtil.queryStringXml(xmlData);
    	Map<String,Object> map = new HashMap<String,Object>();
    	NodeList nodeList = doc.getElementsByTagName("target");
    	for (int i = 0; i < nodeList.getLength(); i++) {
        	if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
        		Element nodeElement = (Element)nodeList.item(i);
				NodeList childNodesList = nodeElement.getChildNodes();
				for (int j = 0; j < childNodesList.getLength(); j++) {
	            	if (childNodesList.item(j).getNodeType() == Node.ELEMENT_NODE) {
	            		Element childNodes = (Element)childNodesList.item(j);
    					for (int attributeLength = 0; attributeLength < childNodes.getAttributes().getLength(); attributeLength++) {
    						map.put(childNodes.getAttributes().getNamedItem("key").getNodeValue(), childNodes.getAttributes().getNamedItem("value").getNodeValue());
						}
					}
				}
			}
		}
    	return map;
    }
    
    
    public static List<Map<String,Object>> cacheXmlDate(Document doc,String nodeName){
    	List<Map<String,Object>> cacheList = new ArrayList<Map<String,Object>>();
    	Map<String,Object> map = null;
        try{
            NodeList nodeList = doc.getElementsByTagName(nodeName);
            for (int i = 0; i < nodeList.getLength(); i++) {
            	map = new HashMap<String,Object>();
            	if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
            		Element nodeElement = (Element)nodeList.item(i);
					for (int attributeLength = 0; attributeLength < nodeElement.getAttributes().getLength(); attributeLength++) {
						map.put(nodeElement.getAttributes().item(attributeLength).getNodeName(), nodeElement.getAttributes().item(attributeLength).getNodeValue());
					}
					NodeList childNodesList = nodeElement.getChildNodes();
					List<Map<String,String>> childNodesArrayList = new ArrayList<Map<String,String>>();
					Map<String,String> childNodesMap;
					for (int j = 0; j < childNodesList.getLength(); j++) {
		            	if (childNodesList.item(j).getNodeType() == Node.ELEMENT_NODE) {
		            		Element childNodes = (Element)childNodesList.item(j);
		            		childNodesMap = new HashMap<String,String>();
	    					for (int attributeLength = 0; attributeLength < childNodes.getAttributes().getLength(); attributeLength++) {
	    						childNodesMap.put(childNodes.getAttributes().item(attributeLength).getNodeName(), childNodes.getAttributes().item(attributeLength).getNodeValue());
							}
	    					childNodesArrayList.add(childNodesMap);
						}
					}
					map.put("feilds", childNodesArrayList);
					cacheList.add(map);
				}
			}
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return cacheList;
    }


	/**
	 * 解析XML 将xml中存在值的标签以KEY value存放于Map中
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> parseXml(String xml) throws Exception{
		Map<String,String> returnMap=new HashMap<String,String>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		StringReader read = new StringReader(xml);
		InputSource source = new InputSource(read);
		parser.parse(source,new DefaultHandler(){
			String tempKey="";
			@Override
			public void startElement(String uri, String localName,
									 String qName, Attributes attributes)
					throws SAXException {
				// TODO Auto-generated method stub
				super.startElement(uri, localName, qName, attributes);
				tempKey=qName;
			}

			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				// TODO Auto-generated method stub
				super.characters(ch, start, length);
				String temp=new String(ch, start, length).replace("\r|\n","");
				if(temp!=null&&!temp.trim().equals("")){
					returnMap.put(tempKey,temp.toString());
				}

			}
		});
		return returnMap;
	}
}
