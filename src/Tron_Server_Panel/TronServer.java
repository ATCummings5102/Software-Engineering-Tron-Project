package Tron_Server_Panel;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class TronServer extends AbstractServer 
{
	private JTextArea log;
	private JLabel status;
	private boolean started = false;
	
	public TronServer()
	{
		super(12345);
		this.setTimeout(500);
	}
	
	public boolean isStarted()
	{
		return started;
	}
	
	public void setLog(JTextArea log)
	{
		this.log = log;
	}
	
	public void setStatus(JLabel status)
	{
		this.status = status;
	}
	
	public void serverStarted()
	{
		started = true;
		status.setText("Started");
		status.setForeground(Color.GREEN);
		log.append("Server has been started successfully\n");
	}
	
	public void serverStopped()
	{
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server has been stopped.\n");
	}
	
	public void serverClosed()
	{
		started = false;
		status.setText("Closed");
		status.setForeground(Color.RED);
		log.append("Server has been stopped. Press Start to restart server\n");
	}
	
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Client " + client.getId() + " has connected.\n");
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		
	}
}








