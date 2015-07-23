package org.apache.nutch.parse.jabonghtml.clean;

import java.io.OutputStream;
import java.io.StringReader;

import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;


public class ConvertToXHTML implements ICleanHTML{

	
	
	@Override
	public String cleanHTML(String content) {
		
		StringReader sr = new StringReader(content);
		
		Tidy t = new Tidy();
		t.setDocType("omit");
		t.setXHTML(true);
		t.setShowWarnings(false);
		t.setShowErrors(0);
		t.setQuiet(true);
		StringBuffer sb = new StringBuffer();
		OutputStream os = new StringBufferOutputStream(sb);
		
		Document doc = t.parseDOM(sr, null);
		t.pprint(doc, os);
		
		return sb.toString();
	}

}
