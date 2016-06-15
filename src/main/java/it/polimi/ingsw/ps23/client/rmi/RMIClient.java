package it.polimi.ingsw.ps23.client.rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.ServerInterface;

class RMIClient implements ClientInterface {
	
	private static final int RMI_PORT_NUMBER = 1099;
	private static final String POLICY_NAME = "COF Server";
	
	private RemoteView remoteView;
	
	private RMIClient() {
		remoteView = new RemoteView(this);
	}
	
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress(), RMI_PORT_NUMBER);
			ServerInterface server = (ServerInterface) registry.lookup(POLICY_NAME);
			ClientInterface client =  new RMIClient();
			ClientInterface stub = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);
			server.registerClient("my_name", stub);
			server.notify("OOOO");
			
		} catch (RemoteException | UnknownHostException | NotBoundException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Cannot connect to registry.", e);
		}
	}

	@Override
	public void notify(String message) throws RemoteException {
		//remoteView.notify(message);
		System.out.println(message);
	}
	
}
