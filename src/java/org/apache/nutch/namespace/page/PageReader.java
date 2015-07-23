package org.apache.nutch.namespace.page;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configurable;
import org.apache.nutch.plugin.Pluggable;
import org.apache.nutch.plugin.PluginRuntimeException;

public interface PageReader extends Pluggable, Configurable {
	
	/** The name of the extension point. */
	  public final static String X_POINT_ID = PageReader.class.getName();

	void read(PageSource source, Map<String, Object> outputMap,
			Map<String, String> params) throws PluginRuntimeException, IOException;
	
}
