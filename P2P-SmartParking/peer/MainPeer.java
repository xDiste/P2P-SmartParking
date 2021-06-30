package peer;

public class MainPeer {
    public static void main(String[] args) {

        String address = args[0];   // Peer's address
		int port = Integer.parseInt(args[1]);   // Peer's port

        new Peer(address, port).start();    // Start the peer
    }
}
