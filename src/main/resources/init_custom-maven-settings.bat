#-----------------------------------------------------------------------------#
# Create custom-maven-settings.xml
#-----------------------------------------------------------------------------#
# Offline mode set to TRUE by default:
java -jar MavenSettingsInitializer.jar

java -jar ${artifactId}-${version}-jar-with-dependencies.${packaging}

#-----------------------------------------------------------------------------#
# Offline mode set to FALSE:
java -jar MavenSettingsInitializer.jar false

#-----------------------------------------------------------------------------#
