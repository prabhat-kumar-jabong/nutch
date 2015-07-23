package org.apache.nutch.parse.jabonghtml.clean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCleanHTML implements ICleanHTML{
	
	protected abstract Pattern getPattern();
	
	@Override
	public String cleanHTML(String content) {
		Matcher matcher = getPattern().matcher(content);
		return matcher.replaceAll("");
	}

}
