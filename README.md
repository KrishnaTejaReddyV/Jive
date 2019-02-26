# Jive
Aim: To build a Java IDE which can serve as a better learning tool for beginners as compared to current IDEs

## Features
-	Supports basic text editor functionalities
-	Allows compilation and run to happen separately so that users get to interact with both .java and .class files 
-	Highlights the source of runtime exceptions in the file, provides elaborate description about the cause, and reads out the cause of exception (using text-to-speech conversion) when clicked on the highlighted area
-	Facilitates running of Client-Server programs in the same window, unlike any other IDE, using Multi-threading concepts of Java
-	Apache Ant is used to build the files of this project and generate a jar

## How to use Ant
-	Install Apache Ant
-	Set it as path variable
-	Navigate to the project directory with build.xml
-	Run the command: > ant

## Folder Structure
```
Jive:
    src: Contains .java files
    build:
        classes: Contains .class files
        lib: Contains dependent .jar files
    build.xml: XML file for Apache Ant
    Jive.jar: jar file to run the application
```
