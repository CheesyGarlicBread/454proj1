import java.rmi.*;
public interface PeerInterface extends java.rmi.Remote {
	public String getIp() throws RemoteException; 
	public updateFileList(String file) throws RemoteException;
}
