# Html Text Analyzer #

A java command line tool to count unique words on the given web page.
This symbols counts as delimiters: 
```
' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'
```

Results will be shown in terminal and saved in SQLite database file.

Archives with compiled releases can be found at: https://github.com/dream90er/html-text-analyzer/releases

---

## Requirements

>     Java JRE 1.8 or higher

If you want to build project from source code you also need

>     Java JDK 1.8 or higher

## Usage

From project root directory switch to the folder with compiled artifact

```
cd target
```
Run artifact with web url page as argument
```
java -jar html-text-analyzer.jar {WEB_PAGE_URL}
```
{WEB_PAGE_URL} must contain protocol (http or https) and must not be empty.

Example:
```
java -jar html-text-analyzer.jar http://www.example.com
```
And result will be
```
EXAMPLE - 1
DOMAIN - 3
THIS - 2
IS - 1
FOR - 2
USE - 2
IN - 3
ILLUSTRATIVE - 1
EXAMPLES - 1
DOCUMENTS - 1
YOU - 1
MAY - 1
LITERATURE - 1
WITHOUT - 1
PRIOR - 1
COORDINATION - 1
OR - 1
ASKING - 1
PERMISSION - 1
MORE - 1
INFORMATION - 1
```
This result will aslo be saved in SQLite datebase file called <code>htmlTextAnalyzer.db</code>

## Build from source

Execute
```
git clone https://github.com/dream90er/html-text-analyzer.git
cd html-text-analyzer
./mvnw clean install
```
This project contains Maven Wrapper, so you don't need to install Maven.