compile:
	@echo -en '--- Compilation --- \n'
	javac ./peer/GUIPeer.java
	javac ./discovery_server/GUIDiscovery.java
	
run-peer:
	@echo -en '--- Peer execution --- \n'
	java peer.GUIPeer

run-discovery:
	@echo -en '--- Discovery server execution --- \n'
	java discovery_server.GUIDiscovery

clean:
	$(RM) ./peer/*.class
	$(RM) ./discovery_server/*.class
