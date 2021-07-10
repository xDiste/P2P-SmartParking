package discovery_server;

// Imports
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Discovery implements Runnable{
    private int port;                        // Default port
    
    private boolean isStopped = false;              // Status of Discovery server
    private ServerSocket serverSocket;				// Socket where we receive the requests
	private ThreadPoolExecutor threadPool;			// Threadpool for handle the requests

    private InetSocketAddress[] overlayNetwork;     // Array that contains the overlay network
    private int nPeers;                             // Variable that contains how many peers there are into the network

    private GUIDiscovery gui;

    // Constructor
    public Discovery(int port, GUIDiscovery gui){
        this.threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.port = port;
        overlayNetwork = new InetSocketAddress[100];
        nPeers = 0;
        this.gui = gui;
    }

    // Method in order to get the array that contains the overlay network
    public InetSocketAddress[] getOverlayNetwork(){
        return this.overlayNetwork;
    }

    // Method in order to get how many peer there are into the network
    public int getNumPeers(){
        return nPeers;
    }

    // Method that control if a peer is already registered inside network
    private boolean isIn(String address, int port) throws UnknownHostException{
        address = Inet4Address.getByName(address).getHostAddress().toString();
        for(int i = 0; i < getNumPeers(); ++i){
            if(overlayNetwork[i].getAddress().getHostAddress().toString().equals(address) && overlayNetwork[i].getPort() == port)
                return true;
        }
        return false;
    }

    // Method that add a peer in overlay network
    public void addPeer(String address, int port) throws UnknownHostException {
        if(!isIn(address, port)){
            InetSocketAddress inetSocketAddress = new InetSocketAddress(address, port);
            overlayNetwork[getNumPeers()] =  inetSocketAddress;
            nPeers++;
        }
    } 

    // Method that servers to know the server status 
    private boolean isStopped(){
        return this.isStopped;
    }

    // Method in order to shutdown the servers
    public void stop() {
		synchronized(this){
			this.isStopped = true;
	        notifyAll();				// notify the server is closing
	    }
		try {
			this.serverSocket.close();	// close the socket that handle the requests
			this.threadPool.shutdown();	// end the current trasmission
			gui.appendEvent("\n############################\nStopping Server.\n\nServer is being stop.\nReject latest request waiting for response cause 'Closing Server'");
			
			if (!this.threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
       			this.threadPool.shutdownNow(); // cancel currently executing tasks
            }
		} catch (Exception e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

    public void run() {
        try {
            // Open the socket where we receive the requests
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // while the server is not shut down
		while (!isStopped()) {
			try {

				Socket clientSocket = null;		// new socket for client
				clientSocket = this.serverSocket.accept();	// wait until a request is found
				// create thread to execute the request with reference to this for send the overlay network
				this.threadPool.execute(new DiscoveryThread(clientSocket, this, gui));

			} catch (IOException e) {
				if (isStopped())	// if we are here because the server is being stop
					break;	
				throw new RuntimeException("Error accepting client connection", e);	// accept error
			}
		}
    }

    public void run(Discovery discoveryServer){
        new Thread(discoveryServer).start();
        try {
			Thread.sleep(60 * 1000);	// seconds until stop (default 1 minute)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if(!isStopped())
            discoveryServer.stop();	// end
    }
}
