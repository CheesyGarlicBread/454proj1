import java.io.*;
import java.util.*;

public class Peers {
	    
    private Vector<Peer> peers = new Vector<Peer>();
    
    // The peersFile is the name of a file that contains a list of the peers
    // Its format is as follows: in plaintext there are up to maxPeers lines,
    // where each line is of the form: <IP address> <port number>
    // This file should be available on every machine on which a peer is started,
    // though you should exit gracefully if it is absent or incorrectly formatted.
    // After execution of this method, the _peers should be present.
    public int initialize(String peersFile)
    {
    	try{
			BufferedReader br = new BufferedReader(new FileReader(peersFile));
			String line;
			while((line = br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line, " ");
				Peer p = new Peer(st.nextToken(),st.nextToken());
				peers.add(p);
			}
			
			br.close();			
		}catch(FileNotFoundException e){
			System.out.println("File could not be found!");
		}catch(IOException e){
			System.out.println("An IOException has occurred.");		
		}catch(NoSuchElementException e){
			System.out.println("Incorrectly formatted file");
		}
    	return 0;
    }
    	
    
    //Peer operator()(int i);
    
    // You will likely want to add methods such as visit()


}
