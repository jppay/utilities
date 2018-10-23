# utilities

Project:
jppay/utilities

Author: 
Jimmy Paul (jppay)

Description: 
This repository contains simple programs with utility classes and methods. It has three Java classes and methods in each of these classes. Each of these classes helps in solving small problems which I have used in production systems. These classes can be easily integrated into your code, as these are standalone classes. Detailed description of these classes follows. 

The Java class "XMLParseHelp" can be used while ingesting XML messages and escape characters have to be modified for successful parsing. This class has two methods, each handling '&' and '<' respectively. These characters are checked in the String and replaced with its respective escape characters. 

"StringUtil" has a single method used to replace series of characters in String with different series of characters (sub string) with the flag to ignore or mind the case of characters being replaced. This class and its methods build on the String.replace() function available in Java API. 

The last class "OpenCsvExample" is an example which shows how to integrate third-party API. This API helps to generate and read Comma Separated Values (CSV) files. It uses the following methods to -
- write (<i>writeNext(String[])</i>) and read (<i>readNext(String[]), 
- readAll(List<String[]>)</i>) 
...to and/or from CSV files.
