package discovery_server;

// Imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class DiscoveryThread extends Thread{
    private Socket socket;			// Socket that serves to handle the request
    private Discovery discovery;	// It needs to get the number of peers
    private GUIDiscovery sg;
	// Constructor
    public DiscoveryThread(Socket socket, Discovery discovery, GUIDiscovery sg){
        this.socket = socket;
        this.discovery = discovery;
		this.sg = sg;
    }

	// Method in order to compute the response for the peers
	// request format: typeRequest,address,port
	// typeRequest --> 0 registration, 1 overlay network
    private synchronized InetSocketAddress[] computeResponse(String request) throws UnknownHostException{
		String[] parts = request.split(",");
		// If the peer wants to only register
		if(parts[0].equals("0")){
			String address = parts[1];
			int port = Integer.valueOf(parts[2].trim());
			sg.appendEvent("Registration: " + parts[1] + ", " + parts[2]);
			// Add the peer inside the network
			discovery.addPeer(address, port);
			sg.appendEvent("\nThere are " + discovery.getNumPeers() + " peers inside the network\n");
		}
		// If the peer wants to know who there is inside the network
		else if(parts[0].equals("1")){
			// Return the updated array that contains the overlay network
			return discovery.getOverlayNetwork();
		}
		return null;
    }

    public void run() {
		String request = "EmptyRequest";	// init at "EmptyRequest" for log purpouse
		try { 
			// take streams
			DataInputStream input = new DataInputStream(socket.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			DataOutputStream out = new DataOutputStream((socket.getOutputStream()));
			// take request
			byte[] b = new byte[100];
			if(input.read(b) > 0)	// if don't read anything, leave "EmptyRequest" string
				request = new String(b);

			// compute and send the response
			InetSocketAddress[] overNet = computeResponse(request);
			if(!(overNet == null)){
				output.writeObject(computeResponse(request));
				out.writeInt(discovery.getNumPeers());
			}
			// close streams
			output.close();
			input.close();
			socket.close();	// no keep alive
		} catch (Exception e) { 
            System.out.println(e);
		}
	}

}
