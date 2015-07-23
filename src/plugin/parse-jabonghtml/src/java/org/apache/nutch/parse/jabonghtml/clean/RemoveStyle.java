package org.apache.nutch.parse.jabonghtml.clean;

import java.util.regex.Pattern;

public class RemoveStyle extends AbstractCleanHTML{

	private Pattern pattern = Pattern.compile("<style[^>]*>[\\s\\S]*?</style>", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);

	@Override
	protected Pattern getPattern() {
		return pattern;
	}
}
