package peer;

public class Peer extends Thread{
	public static final int numParks = 2;	// Each peer knows how many parking spot there are

	// Information about this peer
	private String address;		// Its address
	private int port;			// Its port
	private boolean inside;		// Its status (if it's inside or not)
	private boolean close;
	private GUIPeer gui;

	// Constructor
	public Peer(String address, int port, GUIPeer gui){
		this.address = address;
		this.port = port;
		this.inside = false;
		this.close = false;
		this.gui = gui;
	}

	// Method used to set its status
	public void setStatus(boolean status){this.inside = status;}

	// Method used to know its status
	public boolean getStatus(){return inside;}

	// Method used to know its address
	public String getAddress(){return this.address;}

	// Method used to know its port
	public int getPort(){return this.port;}

	public void termination(){this.close = true;}

	public boolean isClosed(){return this.close;}

    public void run() {
		// Peer create a peer-server
		PeerServer peerServer = new PeerServer(this, this.gui);
		peerServer.start();
        
        // Peer create a peer-client
        PeerClient peerClient = new PeerClient(this, this.gui);
		peerClient.start();
		/*
		try {
			Thread.sleep(60 * 1000);	// seconds until stop (default 1 minute)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if(!isClosed())
            this.termination();	// end */
	}
}