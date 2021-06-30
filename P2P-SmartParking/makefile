compile:
	@echo -en '--- Compilazione --- \n'
	javac ./peer/MainPeer.java
	javac ./discovery_server/Discovery.java
	
run-peer:
	@echo -en '--- Esecuzione peer --- \n'
	java peer.MainPeer $(ADDRESS) $(PORT)

run-discovery:
	@echo -en '--- Esecuione Discovery server --- \n'
	java discovery_server.Discovery

clean:
	$(RM) ./peer/*.class
	$(RM) ./discovery_server/*.class
