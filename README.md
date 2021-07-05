# P2P-SmartParking
P2P Smart Parking with Discovery server

Implemented intelligent parking using peer-to-peer architecture with Discovery server.
The idea is to consider the nodes as if they were the cars that exchange information in and out with the aim of always knowing how many spaces are available 
in the parking lot.

![Communication Peers and Discovery](https://user-images.githubusercontent.com/59747500/124516148-53e79c80-dde1-11eb-9230-7220fa894d0f.png)

"Peer1 Client" represents the client side of the peer, "Peer2 Server" represents the server side of another peer.

The client side has to register to the network through the Discovery server. When it wants to enter it will have to contact the Discovery server to find out who is on the network. Then it will have to contact the server side of all the other peers to find out if it can enter.
