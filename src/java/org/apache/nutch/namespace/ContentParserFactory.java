package org.apache.nutch.namespace;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.jabong.JUtil;
import org.apache.nutch.jabong.JabongKey;
import org.apache.nutch.parse.Parser;
import org.apache.nutch.parse.ParserFactory;
import org.apache.nutch.plugin.Extension;
import org.apache.nutch.plugin.ExtensionPoint;
import org.apache.nutch.plugin.PluginRepository;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.apache.nutch.util.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentParserFactory {
	public static final Logger LOG = LoggerFactory.getLogger(ParserFactory.class);
	
	private final Configuration conf;
	private final ExtensionPoint extensionPoint;
	
	
	public ContentParserFactory(Configuration conf) {
	    this.conf = conf;
	    this.extensionPoint = PluginRepository.get(conf).getExtensionPoint(
	    		ContentParser.X_POINT_ID);
	    if (this.extensionPoint == null) {
	      throw new RuntimeException("x point " + Parser.X_POINT_ID + " not found.");
	    }
	    
	  }
	
	public ContentParser getParser() throws PluginRuntimeException{
		String nameSpace  = conf.get(JabongKey.NAMESPACE);
		ObjectCache objectCache = ObjectCache.get(conf);
		
		if(StringUtils.isBlank(nameSpace)){
			throw new RuntimeException("NameSpace is not defined.");
		}
		
		
		Extension ext = getExtension(nameSpace);
		if(ext == null){
			throw new PluginRuntimeException("Missing content parser for "+nameSpace);
		}
		
		ContentParser parser =(ContentParser) objectCache.getObject(ext.getId());
		if(parser==null){
			parser =(ContentParser) ext.getExtensionInstance();
			objectCache.setObject(ext.getId(), parser);
		}
		return parser;
		
	}
	
	
	private Extension getExtension(String nameSpace){
		ObjectCache objectCache = ObjectCache.get(conf);
		Extension ext = (Extension)objectCache.getObject("contentparser."+nameSpace);
		if(ext == null){
			Extension extensions[] = this.extensionPoint.getExtensions();
			for(Extension extension:extensions){
				String ns = extension.getAttribute("namespace");
				if(nameSpace.equalsIgnoreCase(ns)){
					ext = extension;
					objectCache.setObject("contentparser."+nameSpace, ext);
					break;
				}
			}
		}
		
		return ext;
	}

}
