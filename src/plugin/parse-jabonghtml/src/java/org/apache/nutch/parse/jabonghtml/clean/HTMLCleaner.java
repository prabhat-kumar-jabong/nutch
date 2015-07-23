package org.apache.nutch.parse.jabonghtml.clean;

public class HTMLCleaner {
	
	/**
	 * Sequence is important
	 */
	ICleanHTML cleaner[] = new ICleanHTML[]{
			new ConvertToXHTML(),
			new RemoveStyle(),
			new RemoveScript(),
			new RemoveIE6CSS()
	};

	public String clean(String content){
		
		String _content = content;
		
		for(int i=0;i<cleaner.length;i++){
			_content = cleaner[i].cleanHTML(_content);
		}
		
		return _content;
	}
	
	

}
