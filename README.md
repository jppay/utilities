# utilities

**__Project__**:
jppay/utilities

**__Author__**: 
Jimmy Paul (jppay)

**__Description__**: 
The repository contains some Java programs with utility classes and methods. Each of these program helps is a fix I used to solve problems encountered used in production systems. These programs can be easily integrated into your code, as these are standalone. Detailed description of these programs is provided. 

**`XMLParseHelp`**: Suppose an XML file must be processed which contains special characters such as '&' and '<'. The program replaces the special characters to escape characters and hence ensures successful parsing. There's a class with two methods, each handling '&' and '<' respectively. This program can be expanded to handle any such special characters through replacement with respective escape characters. 

**`StringUtil`**: Program to replace series of characters in String with different series of characters (sub string) with the flag to ignore or mind the case of characters being replaced. The class and its methods in the program build on the `String.replace()` function available in Java API. 

**`OpenCsvExample`**: A program which shows how to integrate third-party API. This API helps to generate and read Comma Separated Values (CSV) files. The action and its associated method in the program are -
- write:  `writeNext(String[])`
- read:   `readNext(String[]`
- readAll: `List<String[]>` 

**`LevenshteinAbbrScoring`**: This is my implementation to calculate Levenshtein distance. In Computer Science (and few other fields too), Levenshtein distance is associated with measuring the difference between two sequences. It is named after the Soviet mathematician Vladimir Levenshtein. For more details on, please refer https://en.wikipedia.org/wiki/Levenshtein_distance
