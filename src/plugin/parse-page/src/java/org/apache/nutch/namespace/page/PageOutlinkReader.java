package org.apache.nutch.namespace.page;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

public class PageOutlinkReader extends AbstractPageReader{

	 public static final Logger LOG = LoggerFactory.getLogger(PageOutlinkReader.class);
	
	private Configuration conf;
	
	@Override
	public void read(PageSource source,Map<String, Object> outputMap,Map<String, String> params) throws PluginRuntimeException {
		
		String key = params.get("key");
//		String xpathExpression = params.get("xpath");
		//System.out.println(xpathExp);
		String xpathExpression = "//*[@id=\"FGContainer_32e19abc8a8832790e88790758f277fc\"]/div/div[2]/ul/li/div/a";// "html"; //params.get("xpath");	
		System.out.println(xpathExpression);
		if(StringUtils.isBlank(key)||StringUtils.isBlank(xpathExpression)){
			LOG.warn("Missing key or xpath for source "+source.getUrl());
		}
		
		if(outputMap==null){
			throw new PluginRuntimeException("outputMap is not instantiated.");
		}
		
		try {
			
			Tidy tidy = new Tidy();
		    tidy.setQuiet(true);
		    tidy.setShowWarnings(false);
	        Document root = tidy.parseDOM(source.getInput(),null);
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes;
			nodes = (NodeList) xPath.evaluate(xpathExpression,root.getDocumentElement(), XPathConstants.NODESET);
			
		    List<String> valueList = new LinkedList<String>();
			for (int i = 0; i < nodes.getLength(); ++i) {
				NamedNodeMap nnm = nodes.item(i).getAttributes();
				Node node = nnm.getNamedItem("href");
				valueList.add(node.getNodeValue());
			}
			
			outputMap.put(key, valueList);
			
			
//			DocumentBuilderFactory builderFactory =
//			        DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = null;
//			try {
//			    builder = builderFactory.newDocumentBuilder();
//			    Document document = builder.parse(source.getInput());
//			    XPath xPath = XPathFactory.newInstance().newXPath();
//			    NodeList nodes = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);
//			    List<String> valueList = new LinkedList<String>();
//				for (int i = 0; i < nodes.getLength(); ++i) {
//					NamedNodeMap nnm = nodes.item(i).getAttributes();
//					Node node = nnm.getNamedItem("href");
//					valueList.add(node.getNodeValue());
//				}
//				
//				outputMap.put(key, valueList);
//			} catch (ParserConfigurationException e) {
//			    e.printStackTrace();  
//			} catch (SAXException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			LOG.warn("Error while parsing content for source "+source.getUrl());
		}
		
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration arg0) {
		this.conf = arg0;
	}

}
