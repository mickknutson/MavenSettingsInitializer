package com.baselogic.maven;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/*
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <!-- localRepository
    | The path to the local repository maven will use to store artifacts.
    |
    | We DONT want to use this:
    | Default: ${user.home}/.m2/repository
    |
    | Setting on the commandline:
      mvn -Dmaven.repo.local=<path> ...
    |
    | Example:
      mvn -Dmaven.repo.local=/Users/mickknutson/StudentWork/Tools/local/repository

    | This Does NOT work on Mac:
    <localRepository>${env.M2_REPO}</localRepository>

    |
    | Windows example of hard-coding the path:
    <localRepository>C:/StudentWork/Tools/local/repository</localRepository>
    |
    | Mac version of hard coding the path to the Maven local repository:
    -->
    <localRepository>/Users/mickknutson/Documents/workspace/TRIVERA/Trivera_Spring_Boot/StudentWork/Tools/local/repository</localRepository>

    <offline>true</offline>

</settings>

java -jar MavenSettingsInitializer.jar

 */
public class SettingsXMLDocument {

    public static void main(String[] args) {

        String settingsXMLStringValue = null;

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Create <settings> element
            Element settingsRootElement = doc.createElement("settings");
            settingsRootElement.setAttribute("xmlns", "http://maven.apache.org/SETTINGS/1.0.0");
            settingsRootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            settingsRootElement.setAttribute("xsi:schemaLocation", "http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd");
            doc.appendChild(settingsRootElement);

            // Create <localRepository> Element
            Element localRepositoryElement = doc.createElement("localRepository");
            String repository = getCurrentDirectory() + "/local/repository";
            localRepositoryElement.appendChild(doc.createTextNode(repository));
            settingsRootElement.appendChild(localRepositoryElement);

            // Create <offline> Element
            Element offlineElement = doc.createElement("offline");
            String offline = getOfflineMode(args);
            offlineElement.appendChild(doc.createTextNode(offline));
            settingsRootElement.appendChild(offlineElement);

            // Transform Document to XML String
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();

            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            // Get the String value of final xml document
            settingsXMLStringValue = writer.getBuffer().toString();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("personXMLStringValue = " + settingsXMLStringValue);
        createSettingsFile(settingsXMLStringValue);

    }

    // Save file as 'custom-maven-settings.xml'
    private static void createSettingsFile(String contents){
        try {
            FileWriter writer = new FileWriter(getCurrentDirectory() + "/custom-maven-settings.xml", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(contents);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static String getCurrentDirectory(){
        String USER_DIR_KEY = "user.dir";
        String currentDir = System.getProperty(USER_DIR_KEY);

        System.out.println("Working Directory: " + currentDir);
        return currentDir;
    }

    private static String getOfflineMode(String[] args){
        System.out.println("*** args: " + (args != null));
        System.out.println("*** args: " + args.length);
        if(args.length > 0 && args[0] != null){
            return "false";
        }
        else{
            System.out.println("* args: FALSE");

            return "true";
        }
    }

} // The End...
