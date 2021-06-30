package peer;

import java.io.OutputStream;
import java.net.Socket;


public class PeerServerThread implements Runnable{
    private Socket socket;	// Socket that serves to handle the request
	private Peer peer;		// It needs to get information about the peer status 
    
	// Constructor
    public PeerServerThread(Socket socket, Peer peer){
		this.peer = peer;		
        this.socket = socket;	
    }
    
    public void run() {
		try {
			// Send the status to the requesting
			OutputStream output = socket.getOutputStream();
			output.write(String.valueOf(peer.getStatus()).getBytes());
			// Close the connection
			output.close();
			socket.close();
		} catch (Exception e) { 
			System.out.println(e);
		}
	}
}