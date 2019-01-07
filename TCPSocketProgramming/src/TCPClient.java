import java.io.*;
import java.net.*;

public class TCPClient {
	public static void main(String argv[]) throws Exception {
		String sentence="";
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket("192.168.0.11", 5000);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		while (!(modifiedSentence = inFromServer.readLine()).isEmpty() ){
	         System.out.println(modifiedSentence);
        }
		
		sentence = inFromUser.readLine();
		outToServer.writeBytes(sentence + '\n');
		modifiedSentence = inFromServer.readLine();
		if(!modifiedSentence.isEmpty()){
		System.out.println(modifiedSentence);
	    }
		clientSocket.close();

	
		
		
	}	

}