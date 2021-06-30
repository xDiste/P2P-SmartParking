package peer;

public class Peer extends Thread{
	public static final int numParks = 2;	// Each peer knows how many parking spot there are

	// Information about this peer
	private String address;		// Its address
	private int port;			// Its port
	private boolean inside;		// Its status (if it's inside or not)

	// Constructior
	public Peer(String address, int port){
		this.address = address;
		this.port = port;
		this.inside = false;
	}

	// Method used to set its status
	public void setStatus(boolean status){this.inside = status;}

	// Method used to know its status
	public boolean getStatus(){return inside;}

	// Method used to know its address
	public String getAddress(){return this.address;}

	// Method used to know its port
	public int getPort(){return this.port;}

    public void run() {
		// Peer create a peer-server
		new PeerServer(this).start();
        
        // Peer create a peer-client
        new PeerClient(this).start();

	}
}