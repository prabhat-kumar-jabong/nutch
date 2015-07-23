package org.apache.nutch.namespace.page;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.nutch.plugin.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PageOutlinkReader extends AbstractPageReader{

	 public static final Logger LOG = LoggerFactory.getLogger(PageOutlinkReader.class);
	
	
	@Override
	public void read(PageSource source,Map<String, Object> outputMap,Map<String, String> params) throws PluginRuntimeException {
		
		validate(source, outputMap, params);
		String xpathString = params.get("xpath");
		String key = params.get("key");
		
		try {
			XPathExpression xPathExpression = getXPathExpression(xpathString);
	        Document root  = getDocument(source);
	        
//			XPath xPath = XPathFactory.newInstance().newXPath();
//			NodeList nodes = (NodeList) xPath.evaluate(xpathString,root, XPathConstants.NODESET);
	        
			NodeList nodes = (NodeList) xPathExpression.evaluate(root, XPathConstants.NODESET);
			
		    List<String> valueList = new LinkedList<String>();
			for (int i = 0; i < nodes.getLength(); ++i) {
				NamedNodeMap nnm = nodes.item(i).getAttributes();
				Node node = nnm.getNamedItem("href");
				valueList.add(node.getNodeValue());
			}
			
			outputMap.put(key, valueList);
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			LOG.warn("Error while parsing content for source "+source.getUrl());
		} 
		
	}


}
