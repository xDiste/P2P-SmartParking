# P2P Smart Parking
P2P Smart Parking with Discovery server

General explanation
-------------------------------------------------------

Implemented intelligent parking using peer-to-peer architecture with Discovery server.
The idea is to consider the nodes as if they were the cars that exchange information in and out with the aim of always knowing how many spaces are available 
in the parking lot.

![sequence](https://user-images.githubusercontent.com/59747500/126041270-865feca6-c2bc-4d1b-9358-da92118bc18c.png)


"PeerClient" represents the client side of the peer, "Peer_nServer" represents the server side of another peer.

The client side has to register to the network through the Discovery server. When it wants to enter it will have to contact the Discovery server to find out who is on the network. Then it will have to contact the server side of all the other peers to find out if it can enter.

Peer
-------------------------------------------------------

![peer](https://user-images.githubusercontent.com/59747500/126041611-dd38c08a-ff12-4fac-a9a5-1b2e7ffd2f68.png)

Once the peer is started, a GUI will open, after entering the address and port, to create the peer it is necessary to press the start key. The newly created peer in turn will manage two threads, one of which will act as a client while the other as a server that will manage the requests of the other peers in the network, responding with its status, how many nodes are in the parking lot and with a timestamp indicating the " freshness "of information

![peerGUI](https://user-images.githubusercontent.com/59747500/126041641-3854fddd-7ead-4d09-b943-609f34d5ef9c.png)

Discovery Server
-------------------------------------------------------

![Discovery](https://user-images.githubusercontent.com/59747500/126041694-c7a2efb9-4527-4343-a320-7aadec841bbc.png)

Starting the Discovery will open a GUI that will create the Discovery Server after entering the port on which it will listen and pressing the start button. In turn it will create n threads to meet all requests.

![discoveryGUI](https://user-images.githubusercontent.com/59747500/126041739-eeae554a-f917-43b4-a4a4-9e4c360474f2.png)
