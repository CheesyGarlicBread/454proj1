import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.*;

public class Peer extends java.rmi.server.UnicastRemoteObject implements PeerInterface{
	
	// Return codes
	// This is a defined set of return codes.
	// Using enum here is a bad idea, unless you spell out the values, since it is
	// shared with code you don't write.
	final int errOK             =  0; // Everything good
	final int errUnknownWarning =  1; // Unknown warning
	final int errUnknownFatal   = -2; // Unknown error
	final int errCannotConnect  = -3; // Cannot connect to anything; fatal error
	final int errNoPeersFound   = -4; // Cannot find any peer (e.g., no peers in a peer file); fatal
	final int errPeerNotFound   =  5; // Cannot find some peer; warning, since others may be connectable
	
	final static int chunkSize = 65536;
	final static int maxPeers = 6;
	final static int maxFiles = 100;
	
    private Peers peers;
    private Status status;
	private String ip;
	private String port;
	private Vector<String> localFiles = new Vector<String>();
	
	private enum State { connected, disconnected, unknown }
	  
	public Peer() throws java.rmi.RemoteException {
		this("127.0.0.1","80");
	}
	
	public Peer(String ip, String port) throws java.rmi.RemoteException {
		super();
		this.ip = ip;
		this.port = port;
		
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public Peers getPeers() {
		return peers;
	}

	public void setPeers(Peers peers) {
		this.peers = peers;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	  
	public int insert(String filename)
	{
		//Take string filename
		//Add to local peer
		
		//Store with local files
		
		//Push file out
		
		//If files DNE, return negative
		//Return 0 if successful
		return 0;
	}
	
	public int query(Status status)
	{
		//Populate parameter status with details for each file
		//1. The fraction of the file that is available locally
		//2. The fraction of the file that is available in the system
		//3. The least replication level
		//4. The weighted least-replication level
		return 0;
	}
	
	public int join(Peers peers)
	{
		try{
			PeerInterface newpeer = (PeerInterface)Naming.lookup("rmi://localhost:1099/PeerService");
			System.out.println(newpeer.getIp());
		}catch(RemoteException e){
			
		}catch(MalformedURLException e){
			
		}catch(NotBoundException e){
			
		}
		/*
		String filename1 = "c:\\tmp\\file.png";
		FileInputStream fis = new FileInputStream(filename1);
	    int size = 1024;
	    byte buffer[] = new byte[size];

	    int count = 0;
	    while (true) {
	      int i = fis.read(buffer, 0, size);
	      if (i == -1)
	        break;
	      String filename2 = "c:\\tmp\\filep.png";
	      String filename = filename2 + count;
	      FileOutputStream fos = new FileOutputStream(filename);
	      fos.write(buffer, 0, i);
	      fos.flush();
	      fos.close();

	      ++count;
	    }*/
	    
		//Container is Peers
		//Attempt to join set
		//Push all local files
		//Pull all files that don't exist in set
		return 0;
	}
	
	public int leave()
	{
		//Leave set of peers
		//Close socket connections
		//Inform peers that it is leaving
		//Ideally, unique blocks are pushed before disconnection
		//Only push if the number of blocks is low
		return 0;
	}
}
