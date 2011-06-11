import java.io.*;
import java.rmi.*;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
public class Driver {
	
	
	
	private static Peer peer;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			peer = new Peer();
		}catch(RemoteException e){
			System.out.println("Remote connection issue.");			
		}
		//keep reading input
		while(true){
			System.out.println("\nPlease enter a command:");
			try{
				String input = br.readLine();
				parseCommand(input);
			}catch (IOException e){
				System.out.println("IOException encountered.");
				System.exit(1);
			}
			
		}
		
	}
	
	public static void parseCommand(String command){		
		//tokenize string
		StringTokenizer st = new StringTokenizer(command, " ");
		int returnCode = 0;
		
		
		if(st.hasMoreTokens()){
			//get command
			String c = st.nextToken().toLowerCase();			
			if(c.equals("insert")){
				try{
					//call insert
					returnCode = peer.insert(st.nextToken());
				}catch(NoSuchElementException e){
					System.out.println("No filename specified");
				}
			}else if(c.equals("query")){
				returnCode = peer.query(peer.getStatus());
			}else if(c.equals("join")){
				returnCode = peer.join(peer.getPeers());
			}else if(c.equals("leave")){
				returnCode = peer.leave();
			}else{
				returnCode = 500;
			}
		}
		
		//check return code
		if(returnCode == 0){
			System.out.println("Command Success");
		}else if(returnCode > 0){
			System.out.print("Ran into a warning: ");
			switch(returnCode){
				case 1:
					System.out.println("Unknown Warning.");
					break;
				case 5:
					System.out.println("Peer not found.");
					break;
				case 500:
					System.out.println("Command not found.");
					break;
				default:
					break;
			}
			
		}else if (returnCode < 0){
			System.out.print("Ran into an error: ");
			switch(returnCode){				
				case -2:
					System.out.println("Unknown Error.");
					break;
				case -3:
					System.out.println("Cannot connect.");
					break;
				case -4:
					System.out.println("Cannot find peer.");
					break;
				default:
					break;
			}
		}
	}

}
