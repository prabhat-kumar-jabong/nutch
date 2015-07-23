package org.apache.nutch.jabong;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.namespace.page.Page;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.apache.nutch.util.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JUtil {
	
	public static final Logger LOG = LoggerFactory
		      .getLogger("org.apache.nutch.jabong.JUtil");
	
	public static String getValueByNameSpace(Configuration conf,String key){
		return getValue(conf, key, JabongKey.NAMESPACE);
	}
	
	public static String getValue(Configuration conf,String nutchKey,String key){
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(conf.get(key))){
			sb.append(conf.get(key)).append(".");
		}
		sb.append(nutchKey);
		return conf.get(sb.toString());
	}
	
	
	public static boolean isValidType(String type){
		try{
			JabongKey.TYPE_ITEM.valueOf(type.toUpperCase());
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}
	
	
	 public static Page getPage(Configuration conf) throws PluginRuntimeException{
			ObjectCache cache = ObjectCache.get(conf);
			
			String ns = conf.get(JabongKey.NAMESPACE);
			if(StringUtils.isBlank(ns)){
				LOG.warn("namespace is not provided this will be treated as normal parsing.");
				return null;
			}
			
			String key = ns.concat(".page.xml");
			
			Page page = (Page)cache.getObject(key);
			if(page==null){
				try {
					
					InputStream is = conf.getConfResourceAsInputStream(key);
					JAXBContext jc = JAXBContext.newInstance(Page.class);
					Unmarshaller ul = jc.createUnmarshaller();
					JAXBElement<Page> root =  ul.unmarshal(new StreamSource(is),Page.class);
					page = root.getValue();
					cache.setObject(key, page);
					
				} catch (JAXBException e) {
					e.printStackTrace();
					LOG.error("Error while reading "+key, e);
					page = null;
				}
			}
			
			return page;
		}
	  

}
