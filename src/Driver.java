import java.io.*;
public class Driver {
	
	final int chunkSize = 65536;
	final int maxPeers = 6;
	final int maxFiles = 100; 
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("Please enter a command:");
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
	
	}

}
