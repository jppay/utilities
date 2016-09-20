# utilities
Some programs with utility classes and methods

<br>The following Java classes and its methods hels in parsing Strings and XML files. It is designed to parse special characters (such as <, >) which are also part of the incoming data in XML. <br>
<br><b>XMLParseHelp.java</b> -
<br>Contains two methods: 
<br><i>escapeAmpersands(String unescapedMessageText) </i> 
<br><i>parseLessThan(String text, String oldSubString, String newSubString)</i>

<br><b>StringUtil.java</b> -
<br>Contains one method: 
<br><i>replace(String text, String oldSubString, String newSubString, boolean ignoreCase)</i>


<br>----------------------------------------------------------<br>
<br><b>OpenCsvExample.java</b> -
<br>This Java class is an example of integrating third-party API to generate and read Comma Separated Values (CSV) files. It uses the methods available to write (<i>writeNext(String[])</i>) and read (<i>readNext(String[]), readAll(List<String[]>)</i>) to/from CSV files
