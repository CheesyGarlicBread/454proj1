
public class Status {
	
	
	// This is very cheesy and very lazy, but the focus of this assignment
    // is not on dynamic containers but on the BT p2p file distribution
	
	// The number of files currently in the system, as viewed by this peer
	private int numFiles;
	
	// The fraction of the file present locally 
    // (= chunks on this peer/total number chunks in the file)
	private double local[] = new double[Peer.maxFiles];
	
	// The fraction of the file present in the system 
    // (= chunks in the system/total number chunks in the file)
    // (Note that if a chunk is present twice, it doesn't get counted twice;
    // this is simply intended to find out if we have the whole file in
    // the system; given that a file must be added at a peer, think about why
    // this number would ever not be 1.)
	private double system[] = new double[Peer.maxFiles];
	
	// Sum by chunk over all peers; the minimum of this number is the least 
    // replicated chunk, and thus represents the least level of replication
    // of the file
	private int leastReplication[] = new int[Peer.maxFiles];
	
	// Sum all chunks in all peers; divide this by the number of chunks in the
    // file; this is the average level of replication of the file
	private double weightedLeastReplication[] = new double[Peer.maxFiles];
	
	public int numberOfFiles()
	{
		return 0;
	}
	public double fractionPresentLocally(int fileNumber)
	{
		return 0;
	}
	public double fractionPresent(int fileNumber)
	{
		return 0;
	}
	public int   minimumReplicationLevel(int fileNumber)
	{
		return 0;
	}
	public double averageReplicationLevel(int fileNumber)
	{
		return 0;
	}	
}
