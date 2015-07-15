package org.apache.nutch.namespace;

import org.apache.hadoop.conf.Configurable;
import org.apache.nutch.namespace.ParserFilterBuilder.ParserFilterStatus;
import org.apache.nutch.plugin.Pluggable;
import org.apache.nutch.storage.WebPage;

public interface ParseFilter extends Pluggable, Configurable {
	  /** The name of the extension point. */
	  public final static String X_POINT_ID = ParseFilter.class.getName();

	  /*
	   * Interface for a filter that transforms a URL: it can pass the original URL
	   * through or "delete" the URL by returning null
	   */
	  public ParserFilterStatus filter(String url, WebPage page);
	
}
