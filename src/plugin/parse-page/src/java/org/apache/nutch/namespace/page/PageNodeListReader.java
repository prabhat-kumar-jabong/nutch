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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PageNodeListReader extends AbstractPageReader{

	 public static final Logger LOG = LoggerFactory.getLogger(PageNodeListReader.class);
	 
	 
	
	@Override
	public void read(PageSource source,Map<String, Object> outputMap,Map<String, String> params) throws PluginRuntimeException {
		
		validate(source, outputMap, params);
		String xpathString = params.get("xpath");
		String key = params.get("key");
		try {
			
			XPathExpression xPathExpression = getXPathExpression(xpathString);
	        Document root = getDocument(source);
	        NodeList nodeList = (NodeList) xPathExpression.evaluate(root, XPathConstants.NODESET);
			if(nodeList!=null && nodeList.getLength()>0){
				List<String> items = new LinkedList<String>();
				for(int index = 0; index < nodeList.getLength() ; index++){					
					Node node = nodeList.item(index);
					LOG.trace(key+":"+node.getNodeValue());
					items.add(node.getNodeValue());									
				}				
				outputMap.put(key, items);
			}
	        
	        int i =0;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			LOG.warn("Error while parsing content for source "+source.getUrl());
		} 
		
	}

}
