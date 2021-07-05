package discovery_server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUIDiscovery extends JFrame implements ActionListener, WindowListener{
	// the stop and startStop buttons
	private JButton startStop;
	// JTextArea for the chat room and the events
	private JTextArea event;
	// The port number
	private JTextField tPortNumber;
	// my server
	private Discovery server;
	
	
	// server constructor that receive the port to listen to for connection as parameter
	public GUIDiscovery() {
		super("Discovery Server");
		// in the NorthPanel the PortNumber the startStop and Stop buttons
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField(10);
		north.add(tPortNumber);

		// to stop or startStop the server, we startStop with "startStop"
		startStop = new JButton("Start");
		startStop.addActionListener(this);
		north.add(startStop);
		add(north, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(1,1));
		event = new JTextArea(80,80);
		event.setEditable(false);
		appendEvent("--- Discovery server ---\n");
		center.add(new JScrollPane(event));	
		add(center);
		
		// need to be informed when the user click the close button on the frame
		addWindowListener(this);
		setSize(600, 600);
		setVisible(true);
	}		

	// append message to the two JTextArea
	// position at the end
	public void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(event.getText().length() - 1);
		
	}
	
	// startStop or stop where clicked
	public void actionPerformed(ActionEvent e) {
		// if running we have to stop
		if(server != null) {
			server.stop();
			server = null;
			tPortNumber.setEditable(true);
			startStop.setText("Start");
			return;
		}
		int port;
		try {
			port = Integer.parseInt(tPortNumber.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Invalid port number");
			return;
		}
		// ceate a new Server
		server = new Discovery(port, this);
		// and startStop it as a thread
		new ServerRunning().start();
		appendEvent("Server is running.\n");
		startStop.setText("Stop");
		tPortNumber.setEditable(false);
	}
	
	// entry point to startStop the Server
	public static void main(String[] arg) {
		new GUIDiscovery();
	}

	/*
	 * If the user click the X button to close the application
	 * I need to close the connection with the server to free the port
	 */
	public void windowClosing(WindowEvent e) {
		// dispose the frame
		if(server != null) {
			try {
				server.stop();			// ask the server to close the conection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
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
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {
		public void run() {
			server.run(server);         // should execute until if fails
			// the server failed
			startStop.setText("Start");
			tPortNumber.setEditable(true);
			appendEvent("Server closed\n");
			server = null;
		}
	}
    
}

