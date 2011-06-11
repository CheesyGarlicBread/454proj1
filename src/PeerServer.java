import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

public class PeerServer{
	private Peer pi;
	
	
		
	public PeerServer(Peer p){
		pi = p;
	}
}
