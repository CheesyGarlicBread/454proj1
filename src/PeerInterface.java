import java.rmi.*;
public interface PeerInterface extends java.rmi.Remote {
	public String getIp() throws RemoteException; 
	public void updateFileList(String file) throws RemoteException;
	public byte[] downloadFile(String filename) throws RemoteException;
	
}
