package org.apache.nutch.namespace.page;

import java.io.StringReader;
import java.util.Map;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.apache.nutch.util.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;


public abstract class AbstractPageReader implements PageReader{
	
	 public static final Logger LOG = LoggerFactory.getLogger(AbstractPageReader.class);
	 
	 protected Configuration conf;
	 
	 protected PageReaderFactory readerFactory = null;
	
	protected void validate(PageSource source,Map<String, Object> outputMap,Map<String, String> params) throws PluginRuntimeException{
		
		String key = params.get("key");
		String xpathExpression = params.get("xpath");
		if(StringUtils.isBlank(key)||StringUtils.isBlank(xpathExpression)){
			LOG.warn("Missing key or xpath for source "+source.getUrl());
		}
		
		if(outputMap==null){
			throw new PluginRuntimeException("outputMap is not instantiated.");
		}
		
	}
	
	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration arg0) {
		this.conf = arg0;
		this.readerFactory = new PageReaderFactory(conf);
	}
	
	
	protected Document getDocument(PageSource source){
		Tidy tidy = new Tidy();
		tidy.setXmlTags(true);
        return tidy.parseDOM(new StringReader(source.getContent()),null);
	}
	
	
	protected XPathExpression getXPathExpression(String xpathString) throws XPathExpressionException{
		
		
		ObjectCache cache = ObjectCache.get(getConf());
		XPathExpression xPathExpression = (XPathExpression)cache.getObject(xpathString);
		if(xPathExpression == null){
			xPathExpression = XPathFactory.newInstance().newXPath().compile(xpathString);
			cache.setObject(xpathString, xPathExpression);
		}
		
		return xPathExpression;
	}
	
	

}
