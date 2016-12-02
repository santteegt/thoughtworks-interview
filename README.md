# Thoughtworks Exercise for Interview

This standalone application inomlements the Galactic Merchant Guide exercise proposed by Thoughworks as part of the first interview for a positiion in their company.

## Galactic Merchant Guide - Implementation

Basically, the application solves the problem using Regex rules to validate the input file lines and to translate the galactic words into Roman (and finally Roman into Real numbers).

The main classes to focus are:

* *GalacticMerchantApp.java*: runs the application. It contains the main processes for translation.
* *GalacticTranslationReader.java*: parses the input file.
* *GalacticTranslationDocument.java*: represents a translation document to process.
* *RomanRules.java*: Enum containing rules that validate a Roman value as correct.
* *TranslationRules.java*: Enum containing rules to achieve a correct translation from Galactic to Real numbers.

The implementation also includes a little bit of Unit tests.

### Requirements

* Java 8
* Maven 3.x


### How to Run the Code

In a terminal window and under the home directory of the project run the following :

```
$ mvn clean install
$ mvn exec:java
```

### Where does the Output is stored?

The resulting output is stored in the text file *output.txt* under the home directory of this project. 

### How to run the Code using the Test file

If you want to run the code using the given test input file, set the argument -Dshow.file.chooser=false in the pom.xml file.

Alternatively, you can choose the file manually from the resources folder.

### About the Author

* Santiago Gonzalez Toral - [WebSite](https://santteegt.github.io)