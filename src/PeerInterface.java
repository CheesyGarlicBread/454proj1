import java.rmi.*;
public interface PeerInterface extends java.rmi.Remote {
	public String getIp() throws RemoteException;
	public String getPort() throws RemoteException; 
	public void updateFileList(String file) throws RemoteException;
	//public byte[] downloadFile(String filename) throws RemoteException;
	//public byte[] downloadFile(String filename) throws RemoteException;
	public byte[] uploadFileChunk(String filename, int offset, int length) throws RemoteException;
	public int filesize(String filename) throws RemoteException;
}
