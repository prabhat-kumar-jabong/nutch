package org.apache.nutch.namespace.page;

import java.io.InputStream;


public class PageSource {
	
	private String url;
	private java.io.Reader reader;
	
	private InputStream input ;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public java.io.Reader getReader() {
		return reader;
	}
	public void setReader(java.io.Reader reader) {
		this.reader = reader;
	}
	public InputStream getInput() {
		return input;
	}
	public void setInput(InputStream input) {
		this.input = input;
	}
	

}
