package org.apache.nutch.parser.amazon;

import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.namespace.ContentParser;
import org.apache.nutch.namespace.ParsedEntity;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;

public class AmazonParser implements ContentParser {
	
	private Configuration conf;
	
	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration arg0) {
		this.conf = arg0;
	}

	@Override
	public ParsedEntity parse(InputSource input) {
		try {
			Tidy tidy = new Tidy();
	        Document root = tidy.parseDOM(input.getByteStream(), null);;
			String xpathExpression = "//*[@id=\"wayfinding-breadcrumbs_feature_div\"]/ul/li[7]/span/a";
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes;
			nodes = (NodeList) xPath.evaluate(xpathExpression,root.getDocumentElement(), XPathConstants.NODESET);
			
			List<String> valueList = new LinkedList<String>();
			for (int i = 0; i < nodes.getLength(); ++i) {
				valueList.add(nodes.item(i).getChildNodes().item(0).getNodeValue());
				valueList.add(nodes.item(i).getTextContent());
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
