package org.apache.nutch.namespace.page;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;

public class TestXMLReader {
	
	
	@Test
	public void testXMLParse() throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(Page.class);
		
//		JAXBContext jc = JAXBContext.newInstance(org.apache.nutch.jabong.xml.ObjectFactory.class);
		
		Unmarshaller ul = jc.createUnmarshaller();
		JAXBElement<Page> root =  ul.unmarshal(new StreamSource(new File("src/test/org/apache/nutch/namespace/page/page3.xml")),Page.class);//new File("src/test/org/apache/nutch/jabong/xml/page.xml"));
		Page page = root.getValue();
		Assert.assertNotNull(page);
		List<Type> t = page.getType();
		for (Iterator iterator = t.iterator(); iterator.hasNext();) {
			Type type = (Type) iterator.next();
			Assert.assertNotNull(type.getReaders().getReader().get(0).getId());
			Assert.assertNotNull(type.getReaders().getReader().get(0).getKey());
			Assert.assertNotNull(type.getReaders().getReader().get(0).getXpath());
		}
		Assert.assertEquals(page.getType().size(),1);
		
	}
	
	

}
