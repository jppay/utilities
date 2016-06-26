class StringUtil {
	
	/**
	 * Replaces all occurences of oldSubString in string with the newSubString.
	 * If ignoreCase replacements is asked, the search for oldSubString does
	 * a case insensitive search for the oldSubString.
	 * 
	 * @param text the text for which the replacement has to be performed
	 * @param oldSubString the text which has to be replaced
	 * @param newSubString the text which has to replace the oldSubString
	 * occurrences
	 * @param ignoreCase <code>true</code> if the replacement should be case
	 * insensitive
	 * @return the text with all occurrences of oldSubString replaced with
	 * newSubString
	 */
	public static String replace(
		String text,
		String oldSubString,
		String newSubString,
		boolean ignoreCase) {
			
		if (!ignoreCase){
			return replace(text, oldSubString, newSubString,-1);
		}else{
			String searchString = text;
			String searchPattern = oldSubString;
			if (ignoreCase) {
				searchString = searchString.toLowerCase();
				searchPattern = searchPattern.toLowerCase();
			}
	
			int index = searchString.indexOf(searchPattern);
			while (index != -1) {
				StringBuffer buf = new StringBuffer(text);
				buf.replace(index, index + searchPattern.length(), newSubString);
	
				text = buf.toString();
				searchString = text;
				if (ignoreCase)
					searchString = searchString.toLowerCase();
	
				index = searchString.indexOf(searchPattern, index + newSubString.length());
			}
	
			return text;
		}
	}
	
	
}
