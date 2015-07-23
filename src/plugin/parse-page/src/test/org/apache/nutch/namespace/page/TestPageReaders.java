package org.apache.nutch.namespace.page;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.jabong.JabongKey;
import org.apache.nutch.parse.jabonghtml.clean.HTMLCleaner;
import org.apache.nutch.plugin.PluginRuntimeException;
import org.apache.nutch.util.NutchConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class TestPageReaders {
	
	@Test
	public void testOutlinkReader() throws PluginRuntimeException, IOException{
		
		PageOutlinkReader pop = new PageOutlinkReader();
		Configuration conf = NutchConfiguration.create();
		conf.set(JabongKey.NAMESPACE, "amazon");
		pop.setConf(conf);
		PageSource source = createPS("src/plugin/parse-page/src/test/resources/outlinkamazon.html");
		
		Map<String, Object> outputMap = new HashMap<String, Object>();
		
		/* this xpression is created after getting the cleaned html content */
		Map<String, String> params = getParams("outlinks", "//*[@id=\"FGContainer_dc8459f2395fe64a3ddc1948206a8d45\"]/div/div[2]/ul/li/div/a");
		pop.read(source, outputMap, params);
		
		List<String> outlink = (List<String>)outputMap.get("outlinks");
		Assert.assertNotNull(outlink);
		Assert.assertTrue(outlink.size()>0);
		
	}
	
	
	@Test
	public void testPDPReader() throws PluginRuntimeException, IOException{
		
		PagePDPReader pdp = new PagePDPReader();
		Configuration conf = NutchConfiguration.create();
		conf.set(JabongKey.NAMESPACE, "amazon");
		pdp.setConf(conf);
		 
		PageSource source = createPS("src/plugin/parse-page/src/test/resources/pdpamazon.html");
		
		
		Map<String, String> params = getParams("pdp", "//*[@id=\"ppd\"]");
		Map<String, Object> outputMap = new HashMap<String, Object>();
		pdp.read(source, outputMap, params);
		
		Map<String, Object> metadata = (Map<String, Object>) outputMap.get("pdp");
		Assert.assertNotNull(metadata);
		Assert.assertNotNull(metadata.get("mrp"));
		Assert.assertNotNull(metadata.get("title"));
		Assert.assertNotNull(metadata.get("brand"));
		Assert.assertNotNull(metadata.get("sp"));
		
		
	}
	
	private String getCleanContentReader(java.io.Reader reader) throws IOException{
		HTMLCleaner cleaner = new HTMLCleaner();
		String content = IOUtils.toString(reader);
		return cleaner.clean(content);
	}
	
	private PageSource createPS(String filePath) throws IOException{
		PageSource ps = new PageSource();
		ps.setContent(getCleanContentReader(new FileReader(filePath)));
		ps.setUrl("www.amazon.com");
		return ps;
	}
	
	private Map<String, String> getParams(String key,String xpath){
		Map<String, String> params= new HashMap<String, String>();
		params.put("key", key);
		params.put("xpath", xpath);
		return params;
	}

}
