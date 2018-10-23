# utilities

Project:
jppay/utilities

Author: 
Jimmy Paul (jppay)

Description: 
This repository contains simple programs with utility classes and methods. It has three Java classes and methods in each of these classes. Each of these classes helps in solving small problems which I have used in production systems. These classes can be easily integrated into your code, as these are standalone classes. Detailed description of these classes follows. 

<br>There are two Java classes with methods in it which hels in parsing Strings and XML files. It is designed to parse special characters (such as <, >) which are also part of the incoming data in XML. <br>
<br>XMLParseHelp.java -
<br>Contains two methods: 
<br><i>escapeAmpersands(String unescapedMessageText) </i> 
<br><i>parseLessThan(String text, String oldSubString, String newSubString)</i>

<br><b>StringUtil.java</b> -
<br>Contains one method: 
<br><i>replace(String text, String oldSubString, String newSubString, boolean ignoreCase)</i>


<br>----------------------------------------------------------<br>
<br><b>OpenCsvExample.java</b> -
<br>This Java class is an example of integrating third-party API to generate and read Comma Separated Values (CSV) files. It uses the methods available to write (<i>writeNext(String[])</i>) and read (<i>readNext(String[]), readAll(List<String[]>)</i>) to/from CSV files
