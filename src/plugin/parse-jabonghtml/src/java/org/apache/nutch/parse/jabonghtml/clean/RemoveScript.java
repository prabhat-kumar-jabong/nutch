package org.apache.nutch.parse.jabonghtml.clean;

import java.util.regex.Pattern;

public class RemoveScript extends AbstractCleanHTML{
	
	private Pattern pattern = Pattern.compile("<script[^>]*>[\\s\\S]*?</script>", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);

	@Override
	protected Pattern getPattern() {
		return pattern;
	}

}
