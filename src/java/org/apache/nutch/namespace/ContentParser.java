package org.apache.nutch.namespace;

import org.apache.hadoop.conf.Configurable;
import org.apache.nutch.plugin.Pluggable;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.xml.sax.InputSource;

public interface ContentParser  extends Pluggable, Configurable {
	
	 /** The name of the extension point. */
	  public final static String X_POINT_ID = ContentParser.class.getName();

	  public ParsedEntity parse(InputSource is) throws PluginRuntimeException;
}
