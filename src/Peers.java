
public class Peers {
	
    private int numPeers;
    private Peer peers[] = new Peer[Peer.maxPeers];
    
    public int initialize(String peersFile)
    {
    	
    	return 0;
    }
    	// The peersFile is the name of a file that contains a list of the peers
      // Its format is as follows: in plaintext there are up to maxPeers lines,
      // where each line is of the form: <IP address> <port number>
      // This file should be available on every machine on which a peer is started,
      // though you should exit gracefully if it is absent or incorrectly formatted.
      // After execution of this method, the _peers should be present.
    
    //Peer operator()(int i);
    
    // You will likely want to add methods such as visit()


}
