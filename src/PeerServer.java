import java.net.MalformedURLException;
import java.rmi.*;

public class PeerServer extends Thread{
	public void run(){
		 try {
		       PeerInterface c = new Peer();
		       Naming.rebind("rmi://localhost:1099/PeerService", c);
		 } catch (MalformedURLException e) {
		     System.out.println("Trouble: " + e);
		 } catch (RemoteException e){
			 System.out.println("Ran into remote exception.");
		 }
	}
}
