package org.apache.nutch.namespace.page;

import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.nutch.plugin.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PageNodeTextReader extends AbstractPageReader{

	 public static final Logger LOG = LoggerFactory.getLogger(PageNodeTextReader.class);
	 
	 
	
	@Override
	public void read(PageSource source,Map<String, Object> outputMap,Map<String, String> params) throws PluginRuntimeException {
		
		validate(source, outputMap, params);
		String xpathString = params.get("xpath");
		String key = params.get("key");
		try {
			
			XPathExpression xPathExpression = getXPathExpression(xpathString);
	        Document root = getDocument(source);
			Node node = (Node) xPathExpression.evaluate(root, XPathConstants.NODE);
			if(node!=null){
				LOG.trace(key+":"+node.getTextContent());
				outputMap.put(key, node.getNodeValue());
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			LOG.warn("Error while parsing content for source "+source.getUrl());
		} catch(Exception e){
			e.printStackTrace();
			LOG.warn("Error while parsing content for source "+source.getUrl());
		}
		
	}

}
