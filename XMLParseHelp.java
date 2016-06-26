

public class XMLParseHelp {
	
	private String escapeAmpersands(String unescapedMessageText) {
		/*
		 * This escaping is a bit tortured. Apparently there is a mixture of escaped characters and 
		 * un-escaped ampersands being passed in. So any un-escaped ampersands are escaped and then  
		 * the process reversed for already-escaped characters. StringUtil doesn't have any regex-matching
		 * capability so it is necessary to do it in this manner.
		 */
		
		//first the un-escape ampersands and less than are escaped
		String partiallyEscapedMessageText = StringUtil.replace(unescapedMessageText, "&", "&amp;", false);
		partiallyEscapedMessageText = StringUtil.replace(partiallyEscapedMessageText, "<", "&lt;", false);
        
	        //then the process reversed for already-escaped characters
	        partiallyEscapedMessageText = StringUtil.replace(partiallyEscapedMessageText, "&amp;quot;", "&quot;", false);
	        partiallyEscapedMessageText = StringUtil.replace(partiallyEscapedMessageText, "&amp;apos;", "&apos;", false);
	        partiallyEscapedMessageText = StringUtil.replace(partiallyEscapedMessageText, "&amp;lt;"  , "&lt;"  , false);
	        partiallyEscapedMessageText = StringUtil.replace(partiallyEscapedMessageText, "&amp;gt;"  , "&gt;"  , false);
	        String escapedMessageText  = StringUtil.replace(partiallyEscapedMessageText, "&amp;amp;" , "&amp;" , false);
	        
	        //Escape '<' character available in real-time data
			escapedMessageText = parseLessThan(escapedMessageText, "<", "&lt;");
	        
	        if(log.isDebugEnabled())
	        {
	            log.debug("Any ampersands and < in the messageText have been escaped to '&amp;' and '&lt;'");
	            // dump the Text message
	            log.debug("Escaped message is now:\n<" + escapedMessageText + ">");
	        }
		return escapedMessageText;
	}
	
	
	  /*
	   * Escape less than character in the real-time data
	   * <P>The following characters are replaced with corresponding character entities : 
	   * <tr><th> Character </th><th> Encoding </th></tr>
	   * <tr><td> < </td><td> &lt; </td></tr>
	   * <P>
	   */
	private String parseLessThan(
		String text,
		String oldSubString,
		String newSubString) 
	{
		String searchString = text;
		String searchPattern = oldSubString;
		int index = searchString.indexOf("<", 1);
		StringBuffer buf = new StringBuffer(text);
		while (index != -1) {
				
				if((buf.charAt(index - 1) != '>') && (buf.charAt(index + 1) != '/'))
				{
					buf.replace(index, index + searchPattern.length(), newSubString);
				}
	
				text = buf.toString();
				searchString = text;
				index = searchString.indexOf(searchPattern, index + newSubString.length());
		}
	
		return text;
	}
	
}

