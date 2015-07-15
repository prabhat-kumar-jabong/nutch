package org.apache.nutch.namespace;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.nutch.parse.ParseException;
import org.xml.sax.InputSource;

public class ContentParserUtil extends Configured {
	private Configuration conf;
	private ContentParserFactory parserFactory;
	
	
	public ContentParserUtil(Configuration conf) {
	    super(conf);
	    setConf(conf);
	    parserFactory = new ContentParserFactory(conf);
	  }
	
	 @Override
	  public Configuration getConf() {
	    return conf;
	  }

	  @Override
	  public void setConf(Configuration conf) {
	    this.conf = conf;
	  }
	  
	  
	  public ParsedEntity parse(InputSource is) throws ParseException{
		  try{
			  ContentParser parser = parserFactory.getParser();
			  return parser.parse(is);
		  }catch(Exception e){
			  throw new ParseException(e);
		  }
	  }

}
