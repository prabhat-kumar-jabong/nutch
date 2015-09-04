package org.apache.nutch.namespace.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.nutch.jabong.JUtil;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PagePDPReader extends AbstractPageReader{

	 public static final Logger LOG = LoggerFactory.getLogger(PagePDPReader.class);
	 
	 
	
	@Override
	public void read(PageSource source,Map<String, Object> outputMap,Map<String, String> params) throws PluginRuntimeException, IOException {
		
		validate(source, outputMap, params);
		String xpathString = params.get("xpath");
		String key = params.get("key");
		try {
			XPathExpression xPathExpression = getXPathExpression(xpathString);
			Document root = getDocument(source);
			Node node = (Node) xPathExpression.evaluate(root, XPathConstants.NODE);
			if(node!=null){
				
				Page page = JUtil.getPage(getConf());
				List<Type> types = page.getType();
				for (Iterator<Type> iterator = types.iterator(); iterator.hasNext();) {
					Type type = iterator.next();
					if(this.getClass().getName().equals(type.getId())){
						Readers readers = type.getReaders();
						List<Reader> readerList = readers.getReader();
						
						Map<String, Object> output = new HashMap<String, Object>();
						
						for (Iterator<Reader> iterator2 = readerList.iterator(); iterator2
								.hasNext();) {
							Reader reader =  iterator2.next();
							PageReader pr = readerFactory.getReader(reader.getId());
							
							Map<String, String> map = new HashMap<String, String>();
							map.put("key", reader.getKey());
							map.put("xpath", reader.getXpath());
							try{
								pr.read(source, output, map);
							}catch(Exception e){
								e.printStackTrace();
								LOG.warn("Error while parsing content for source "+source.getUrl());
							}
						}
						
						outputMap.put(key, output);
					}
				}
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			LOG.warn("Error while parsing content for source "+source.getUrl());
		} 
		
	}

}
