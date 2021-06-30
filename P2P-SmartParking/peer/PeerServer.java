package peer;

// Imports
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PeerServer extends Thread{
	private ServerSocket serverSocket;				// Socket that serves to receive the requestes
	private ThreadPoolExecutor threadPool;			// Threadpool for handle the requestes
	private Peer peer;								// It needs to get information about status, address and port
	
	// Constructor
	public PeerServer(Peer peer) {
		this.peer = peer;
		this.threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	}
	
	public void run(){
		try {
			// Open the socket in order to receive the requests
			this.serverSocket = new ServerSocket(peer.getPort());
			this.serverSocket.setSoTimeout(20*1000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				Socket clientSocket = this.serverSocket.accept();	// Wait until a request is found
				// create thread to execute the request
				this.threadPool.execute(new PeerServerThread(clientSocket, peer));
			} catch (IOException e) {
				break;
			}
		}
		try {
			System.out.println("Closing peer-server...");
			this.serverSocket.close();	// Close peer-server
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Peer-server closed.");
    }
}