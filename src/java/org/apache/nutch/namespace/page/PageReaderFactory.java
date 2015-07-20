package org.apache.nutch.namespace.page;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.parse.Parser;
import org.apache.nutch.plugin.Extension;
import org.apache.nutch.plugin.ExtensionPoint;
import org.apache.nutch.plugin.PluginRepository;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.apache.nutch.util.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageReaderFactory {
	public static final Logger LOG = LoggerFactory.getLogger(PageReaderFactory.class);
	
	private final Configuration conf;
	private final ExtensionPoint extensionPoint;
	
	
	public PageReaderFactory(Configuration conf) {
	    this.conf = conf;
	    this.extensionPoint = PluginRepository.get(conf).getExtensionPoint(
	    		PageReader.X_POINT_ID);
	    if (this.extensionPoint == null) {
	      throw new RuntimeException("x point " + Parser.X_POINT_ID + " not found.");
	    }
	    
	  }
	
	public PageReader getReader(String ref) throws PluginRuntimeException{
		ObjectCache objectCache = ObjectCache.get(conf);
		if(StringUtils.isBlank(ref)){
			throw new RuntimeException("PluginId is not specified.");
		}
		Extension ext = getExtension(ref);
		if(ext == null){
			throw new PluginRuntimeException("Missing reader for "+ref);
		}
		
		PageReader parser =(PageReader) objectCache.getObject(ext.getId());
		if(parser==null){
			parser =(PageReader) ext.getExtensionInstance();
			objectCache.setObject(ext.getId(), parser);
		}
		return parser;
		
	}
	
	
	private Extension getExtension(String ref){
		String extKey = "extension.".concat(ref);
		ObjectCache objectCache = ObjectCache.get(conf);
		Extension ext = (Extension)objectCache.getObject(extKey);
		if(ext == null){
			Extension extensions[] = this.extensionPoint.getExtensions();
			for(Extension extension:extensions){
				String id = extension.getId();
				if(id.equals(ref)){
					ext = extension;
					objectCache.setObject(extKey, ext);
					break;
				}
			}
		}
		
		return ext;
	}

}
