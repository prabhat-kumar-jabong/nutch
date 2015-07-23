package org.apache.nutch.parse.jabonghtml.clean;

import java.util.regex.Pattern;

public class RemoveIE6CSS extends AbstractCleanHTML {
	
	Pattern pattern = Pattern.compile("<![^>-]*>");

	@Override
	protected Pattern getPattern() {
		return pattern;
	}

}
