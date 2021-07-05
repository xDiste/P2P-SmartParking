compile:
	@echo -en '--- Compilazione --- \n'
	javac ./peer/GUIPeer.java
	javac ./discovery_server/GUIDiscovery.java
	
run-peer:
	@echo -en '--- Esecuzione peer --- \n'
	java peer.GUIPeer

run-discovery:
	@echo -en '--- Esecuione Discovery server --- \n'
	java discovery_server.GUIDiscovery

clean:
	$(RM) ./peer/*.class
	$(RM) ./discovery_server/*.class
