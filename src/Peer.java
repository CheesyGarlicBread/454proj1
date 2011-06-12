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
	
	//final static int chunkSize = 65536;
	final static int chunkSize = 65536;
	final static int maxPeers = 6;
	final static int maxFiles = 100;
	
    private Peers peers;
    private Status status;    
	private String ip;
	private String port;
	private Vector<String> localFiles = new Vector<String>();
	
	private enum State { connected, disconnected, unknown }
	
	private State state; 
	  
	public Peer() throws java.rmi.RemoteException {
		this("localhost","10042");
	}
	
	public Peer(String ip, String port) throws java.rmi.RemoteException {
		super();
		peers = new Peers();		
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
	
	private void updateFileList()
	{
		/*
		String server;
		for (int i = 0; i < peers.peers.size(); i++)
		{
			//Connect to remote host
			PeerInterface newpeer = (PeerInterface)Naming.lookup(server);
			
			//Get file size from remote host
			int filesize = newpeer.filesize(filename);
		
		}
		*/
	}
	
	public int insert(String filename)
	{	
		//If localFiles vector already contains the filename, error out
		if (localFiles.contains(filename))
		{
			return -1;
		}
		
		//Add new filename into localFiles vector
		localFiles.add(filename);
		
		downloadFilename(filename, "rmi://localhost:10042/PeerService");
		
		//Take string filename
		//Add to local peer
		
		//Store with local files
		
		//Push file out
		
		//If files DNE, return negative
		//Return 0 if successful
		return 0;
	}
	
	private int downloadFilename(String filename, String server)
	{
		try
		{
			//Connect to remote host
			PeerInterface newpeer = (PeerInterface)Naming.lookup(server);
			
			//Get file size from remote host
			int filesize = newpeer.filesize(filename);
			
			//Create a byte buffer sizeof(filename)
			byte bytefile[] = new byte[filesize];
			
			//Chunk buffer for downloaded data
			byte[] filebuffer;
			
			//Track current chunk location, incriment by 'chunkSize' in while loop below
			int currentChunk = 0;
			
			//Download byte array from remote host
			for(int i = 0; i < (Math.ceil(filesize / chunkSize) + 1); i++)
			{
				filebuffer = newpeer.uploadFileChunk(filename, currentChunk, chunkSize);
				System.out.println(filebuffer);
				
				//Copy the chunk into the main buffer
				for (int j = 0; j < chunkSize; j++)
				{
					if((j+currentChunk) > (filesize-1)) break;
					bytefile[currentChunk+j] = filebuffer[j];
				}
				
				//Increment to next chunk
				currentChunk += chunkSize;
			}
			
			//Write buffer byte data to new file locally
			File file = new File("hello.jpg");
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getName()));
			output.write(bytefile,0,bytefile.length);

			output.flush();
			output.close();
			
			System.out.println("File write complete.");
			
			}catch(RemoteException e){
				System.out.println(e);
			}catch(MalformedURLException e){
				System.out.println(e);
			}catch(NotBoundException e){
				System.out.println(e);
			}catch(FileNotFoundException e){
				System.out.println(e);
			}catch (IOException e){
				System.out.println(e);
			}
		
		return 0;
	}
	
	public byte[] uploadFileChunk(String filename, int offset, int length)
	{
		System.out.println("Upload requested");
		try
		{
			//Create a byte buffer of size: 
			File file = new File(filename);
			byte buffer[] = new byte[length];
			
			/*
			//Read file data into buffer
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
			//input.read(buffer,offset,length);
			input.close();
			*/
			
			RandomAccessFile input = new RandomAccessFile(file,"r");
			input.seek(offset);
			if ((offset+length) > file.length()){
				input.readFully(buffer,0,(int)(file.length()-offset));
			}
			else{
				input.readFully(buffer, 0, length);
			}
			
			//Return byte buffer to caller
			return (buffer);
		} catch(Exception e){
			System.out.println("FileImpl: "+e);
		}
		return null;
	}
	
	//TODO change source of filename size - This won't work when an incomplete file exists
	public int filesize(String filename)
	{
		//Return size of local filename
		File file = new File(filename);
		return (int)file.length();
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
		try
		{
			PeerInterface newpeer = (PeerInterface)Naming.lookup("rmi://localhost:10042/PeerService");
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
		this.state = State.connected;
		
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

	@Override
	public void updateFileList(String file) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
