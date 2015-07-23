package org.apache.nutch.namespace.page;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class TidyXHTML {
	
	
	public static void main(String args[]) throws FileNotFoundException{
		
		FileInputStream fis = new FileInputStream("src/plugin/parse-page/src/test/resources/outlinkamazon.html");
		Tidy t = new Tidy();
		t.setDocType("omit");
		t.setXHTML(true);
		t.setShowWarnings(false);
		t.setShowErrors(0);
		t.setQuiet(true);
		
		StringBuffer sb = new StringBuffer();
		StringBufferOutputStream sbos = new StringBufferOutputStream(sb);
		Document root = t.parseDOM(fis, (OutputStream) null);
		t.pprint(root, sbos);
		System.out.println(sb.toString());
		
	}

}
