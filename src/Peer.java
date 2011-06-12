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
	
	private LinkedList<FileElement> localList = new LinkedList<FileElement>();
	
	private enum State { connected, disconnected, unknown }
	
	private State state; 
	  
	public Peer() throws java.rmi.RemoteException {
		this("127.0.0.1","80");
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
		File file = new File(filename);
		FileElement newElement = new FileElement(filename, file.length(), chunkSize);
		
		//If localFiles vector already contains the filename, error out
		if ((localFiles.contains(filename)) || (localList.contains(newElement)))
		{
			return -1;
		}
		
		//Add new filename into localFiles vector
		localFiles.add(filename);
		
		//Insert FileElelment object into linkedlist
		localList.add(newElement);
		
		//If file exists locally and is being added to the Peer,
		//Fill block complete variable with true to indicate complete file,
		//else fill block_complete with false and begin download
		if (file.exists())
		{
			System.out.println("File exists locally. Adding to local list.");
			//Fill the block_complete array since the file is local and complete
			Arrays.fill(newElement.block_complete, true);
			
			//TEMPORARY, DELETE THIS LINE
			//################AAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHH
			System.out.println("Downloading file anyway, just cause'");
			downloadFile(newElement);
		}
		else
		{
			System.out.println("File does not exist locally. Beginning download.");
			//Fill the block_complete array since the file is local and complete
			Arrays.fill(newElement.block_complete, false);
			downloadFile(newElement);
		}
		
		
		//Take string filename
		//Add to local peer
		
		//Store with local files
		
		//Push file out
		
		//If files DNE, return negative
		//Return 0 if successful
		return 0;
	}
	
	private int downloadFile(FileElement file)
	{
		String server = "rmi://localhost:10042/PeerService";
		try
		{
			//Connect to remote host
			PeerInterface newpeer = (PeerInterface)Naming.lookup(server);
			
			//Create a byte buffer sizeof(filename)
			byte bytefile[] = new byte[(int) file.filesize];
			
			//Chunk buffer for downloaded data
			byte[] filebuffer = null;
			
			//RandomAccessFile to write chunks to
			File newfile = new File("test4.jpg");
			RandomAccessFile output = new RandomAccessFile(newfile, "rw");
			
			//Track current chunk location, incriment by 'chunkSize' in while loop below
			int currentChunk = 0;
			
			//Download byte array from remote host
			for(int i = 0; i < (Math.ceil(file.filesize / chunkSize) + 1); i++)
			{
				filebuffer = downloadFileChunk(file, currentChunk, chunkSize, server);
				System.out.println(filebuffer);
				System.out.println("Downloading Chunk: " + currentChunk);
				
				output.seek(currentChunk);
				output.write(filebuffer);
				
				
				//Increment to next chunk
				currentChunk += chunkSize;
			}	
		
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
	
	private byte[] downloadFileChunk(FileElement file, int chunkID, int chunkSize, String server)
	{
		byte[] filebuffer = null;
		
		try
		{
			//Connect to remote host
			PeerInterface newpeer = (PeerInterface)Naming.lookup(server);
			
			//Chunk buffer for downloaded data
			 filebuffer = newpeer.uploadFileChunk(file.filename, chunkID, chunkSize);
		
		}catch(RemoteException e){
			System.out.println(e);
		}catch(MalformedURLException e){
			System.out.println(e);
		}catch(NotBoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}
		
		return filebuffer;
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
	
	public LinkedList<FileElement> returnList()
	{
		return localList;
	}
	
	public int join(Peers peers)
	{
		try
		{
			//Search through all peers
			//for (peer e : Peers)
			//{
				PeerInterface newpeer = (PeerInterface)Naming.lookup("rmi://localhost:10042/PeerService");
				System.out.println(newpeer.getIp());
				
				LinkedList<FileElement> tmpList = newpeer.returnList();
				
				for (FileElement e : tmpList)
				{
					if (localFiles.contains(e.filename))
					{
						//Already have this filename
						downloadFile(e);
					}
					else
					{
						//Insert FileElement object into linkedlist
						Arrays.fill(e.block_complete, false);
						localList.add(e);
						downloadFile(e);
					}
				}
			//}
		}catch(RemoteException e){
			
		}catch(MalformedURLException e){
			
		}catch(NotBoundException e){
			
		}
	    
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
