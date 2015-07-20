package org.apache.nutch.namespace.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.nutch.plugin.PluginRuntimeException;
import org.junit.Test;

public class TestPageOutlinkReader {
	
	@Test
	public void testOutlinkReader() throws FileNotFoundException, PluginRuntimeException{
		
		
		PageOutlinkReader pop = new PageOutlinkReader();
//		java.io.Reader reader = new FileReader(new File("src/plugin/parse-page/src/test/resources/amazon.html"));
		
		PageSource source = new PageSource();
//		source.setReader(reader);
		source.setInput(new FileInputStream(new File("src/plugin/parse-page/src/test/resources/amazon.html")));
		source.setUrl("www.amazon.com");
		Map<String, Object> outputMap = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", "outlinks");
		params.put("xpath", "//*[@id=\"FGContainer_32e19abc8a8832790e88790758f277fc\"]/div/div[2]/ul/li/div/a");
		pop.read(source, outputMap, params);
		
		
	}

}
