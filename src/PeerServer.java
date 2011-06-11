import java.net.MalformedURLException;
import java.rmi.*;

public class PeerServer implements Runnable{
	private PeerInterface pi;
	public void run(){
		 try {
			 System.out.println("Started Server");
			 java.rmi.registry.LocateRegistry.createRegistry(1099);
		     Naming.rebind("rmi://localhost:1099/PeerService", pi);
		 } catch (MalformedURLException e) {
		     System.out.println("Malformed URL: " + e);
		 } catch (RemoteException e){
			 System.out.println("Ran into remote exception: " + e);
		 }
	}
	
		
	public PeerServer(PeerInterface p){
		pi = p;
	}
}
