package org.apache.nutch.parser.amazon;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.namespace.ContentParser;
import org.apache.nutch.namespace.ParsedEntity;
import org.apache.nutch.namespace.page.Page;
import org.apache.nutch.namespace.page.Type;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.apache.nutch.util.ObjectCache;
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
	public ParsedEntity parse(InputSource input) throws PluginRuntimeException {
		
		ObjectCache cache = ObjectCache.get(conf);
		Page page = (Page)cache.getObject("amazon.page.xml");
		if(page==null){
		
			try {
				InputStream is = conf.getConfResourceAsInputStream("amazon.page.xml");
				JAXBContext jc = JAXBContext.newInstance(Page.class);
				Unmarshaller ul = jc.createUnmarshaller();
				JAXBElement<Page> root =  ul.unmarshal(new StreamSource(is),Page.class);
				page = root.getValue();
				cache.setObject("amazon.page.xml", page);
				
			} catch (JAXBException e1) {
				throw new PluginRuntimeException("Error while reading amazon.page.xml");
			}
		}
		
		List<Type> types = page.getType();
		for (Iterator<Type> iterator = types.iterator(); iterator.hasNext();) {
			Type type = iterator.next();
			
			
		}
		
//		try {
//			Tidy tidy = new Tidy();
//	        Document root = tidy.parseDOM(input.getByteStream(), null);;
//			String xpathExpression = "//*[@id=\"wayfinding-breadcrumbs_feature_div\"]/ul/li[7]/span/a";
//			XPath xPath = XPathFactory.newInstance().newXPath();
//			NodeList nodes;
//			nodes = (NodeList) xPath.evaluate(xpathExpression,root.getDocumentElement(), XPathConstants.NODESET);
//			
//			List<String> valueList = new LinkedList<String>();
//			for (int i = 0; i < nodes.getLength(); ++i) {
//				valueList.add(nodes.item(i).getChildNodes().item(0).getNodeValue());
//				valueList.add(nodes.item(i).getTextContent());
//			}
//			
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//		}
		return null;
	}

}
