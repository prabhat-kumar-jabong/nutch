package org.apache.nutch.jabong;

public interface JabongKey {
	
	String NAMESPACE="namespace";
	
	String TYPE="type";
	
	String HEADER_FILE="header.setting.file";
	
	enum TYPE_ITEM{
		URLLIST("urllist");
		
		String name;
		TYPE_ITEM(String name){
			this.name = name;
		}
	}
	
	

}
