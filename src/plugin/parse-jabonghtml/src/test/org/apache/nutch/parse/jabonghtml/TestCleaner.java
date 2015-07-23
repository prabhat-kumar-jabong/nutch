package org.apache.nutch.parse.jabonghtml;

import org.apache.nutch.parse.jabonghtml.clean.ConvertToXHTML;
import org.apache.nutch.parse.jabonghtml.clean.ICleanHTML;
import org.apache.nutch.parse.jabonghtml.clean.RemoveScript;
import org.apache.nutch.parse.jabonghtml.clean.RemoveStyle;
import org.junit.Assert;
import org.junit.Test;

public class TestCleaner {
	
	@Test
	public void testXHTMLConversion(){
		
		String content = "<html><body><div>abc</div><p>somehting<img src=\"/img.png\"></body></html>";
		ICleanHTML xhtmlTest = new ConvertToXHTML();
		String cleanContent = xhtmlTest.cleanHTML(content);
		Assert.assertTrue(cleanContent.contains("</p>"));
		
		
	}
	
	@Test
	public void testRemoveScript(){
		
		String content = "<html><script>adsasdasdasdasd\n\r new line to print $.query(<something \n\r" +
				"</script><body><div><script type=\"text/html\"> $sdas %67 sdr soemthing </script>abc</div><p>somehting<img src=\"/img.png\"></body></html>";
		ICleanHTML xhtmlTest = new ConvertToXHTML();
		String cleanContent = xhtmlTest.cleanHTML(content);
		ICleanHTML scriptTest = new RemoveScript();
		cleanContent = scriptTest.cleanHTML(cleanContent);
		Assert.assertFalse(cleanContent.contains("<script"));
		Assert.assertFalse(cleanContent.contains("</script>"));
		
	}
	
	@Test
	public void testRemoveStyle(){
		
		String content = "<html><style>adsasdasdasdasd\n\r new line to print $.query(<something \n\r" +
				"</style><body><div><style type=\"text/html\"> $sdas %67 sdr soemthing </style>abc</div><p>somehting<img src=\"/img.png\"></body></html>";
		ICleanHTML xhtmlTest = new ConvertToXHTML();
		String cleanContent = xhtmlTest.cleanHTML(content);
		ICleanHTML scriptTest = new RemoveStyle();
		cleanContent = scriptTest.cleanHTML(cleanContent);
		Assert.assertFalse(cleanContent.contains("<style"));
		Assert.assertFalse(cleanContent.contains("</style>"));
		
	}

}
