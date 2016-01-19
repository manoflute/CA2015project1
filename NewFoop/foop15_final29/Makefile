all: 
	javac src/*.java  
runServer: 
	java -classpath src LostLegacy
runClient:
	java -classpath src LostLegacyServer
makeJarServer:
	jar cvfn LostLegacyServer.jar manifest/manifest_Server.mf src/*.class
makeJarClient:
	jar cvfn LostLegacy.jar manifest/manifestClient.mf src/*.class
clean: 
	rm src/*.class LostLegacyServer.jar LostLegacy.jar
