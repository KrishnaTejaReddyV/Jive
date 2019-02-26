cd jive
javac -cp "freetts;C:\Users\Teja\Desktop\jive" Jive.java
cd ..
jar -cvfm Jive.jar Manifest.txt jive/*.class jive/freetts.jar
java -jar Jive.jar