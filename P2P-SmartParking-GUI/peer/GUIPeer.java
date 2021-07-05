package peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIPeer extends JFrame implements ActionListener, WindowListener{
    // The stop and startStop buttons
	private JButton startStop;
	// JTextArea for the peer-client and the peer-server
	private JTextArea client, server;
    // The address
    private JTextField tAddress;
	// The port number
	private JTextField tPortNumber;
	// My peer
	private Peer peer;
	
	
	// server constructor that receive the port to listen to for connection as parameter
	public GUIPeer() {
		super("Peer");
		// in the NorthPanel the PortNumber the startStop and Stop buttons
		JPanel north = new JPanel();

        north.add(new JLabel("Address: "));
		tAddress = new JTextField(10);
		north.add(tAddress);

		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField(10);
		north.add(tPortNumber);

		// to stop or startStop the peer, we startStop with "startStop"
		startStop = new JButton("Start");
		startStop.addActionListener(this);
		north.add(startStop);
		add(north, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(1,2));

		client = new JTextArea(80,80);
		client.setEditable(false);
        appendEvent("Peer client.\n");
        center.add(new JScrollPane(client));
        
        server = new JTextArea(80, 80);
        server.setEditable(false);
        appendServer("Peer server.\n");
        center.add(new JScrollPane(server));
        add(center);
		
		// need to be informed when the user click the close button on the frame
		addWindowListener(this);
		setSize(600, 600);
		setVisible(true);
	}		

	// append message to the two JTextArea
	// position at the end
    void appendServer(String str) {
        server.append(str + "\n");
        server.setCaretPosition(server.getText().length() - 1);
    }
        
	public void appendEvent(String str) {
		client.append(str + "\n");
		client.setCaretPosition(client.getText().length() - 1);
	}
	
	// startStop or stop where clicked
	public void actionPerformed(ActionEvent e) {
        if(peer != null) {
            peer.termination();
			peer = null;
			tPortNumber.setEditable(true);
            tAddress.setEditable(true);
			startStop.setText("Start");
			return;
		}
        String address;
		int port;
		try {
            address = tAddress.getText().trim();
			port = Integer.parseInt(tPortNumber.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Invalid port number or address");
			return;
		}
		// ceate a new Peer
		peer = new Peer(address, port, this);
		
		// and startStop it as a thread
		new PeerRunning().start();

        appendServer("Server is running.\n");
		appendEvent("Client is running.\n");

        startStop.setText("Stop");
        tPortNumber.setEditable(false);
        tAddress.setEditable(false);
	}
	
	// Entry point to startStop the Peer
	public static void main(String[] arg) {
		new GUIPeer();
	}

	/*
	 * If the user click the X button to close the application
	 * I need to close the connection with the Peer to free the port
	 */
	public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
	}

	// I can ignore the other WindowListener method
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/*
	 * A thread to run the Peer
	 */
	class PeerRunning extends Thread {
		public void run() {
			peer.run();         // should execute until if fails
		}
	}
}
