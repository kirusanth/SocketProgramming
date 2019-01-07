import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.TimeZone;

class TCPServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence = new String();
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(5000);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			Date now = new Date();
			TimeZone timezone = TimeZone.getDefault();
			String menu = " = = = = = = = = = = = = = = = = Menu = = = = = = = = = = = = = = = = = = = = = =\n" +
	                      " 	date - print the date and time of server's system                    \n" +
	                      " 	timezone - print the time zone of server's system                    \n" +
	                      " 	OSname - print the name of server's operating system (OS)            \n" +
	                      " 	OSversion - print the of version number of server's OS               \n" +
	                      " 	user - print the name of the user logged onto (i.e. running) the server \n" +
	                      " 	exit - exit the program                                                  \n" +
	                      " = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n"+
	                      "Enter command > \n";
			outToClient.writeBytes(menu + '\n');
			
		    clientSentence = inFromClient.readLine();
			if (clientSentence.equals("date")){
				
				outToClient.writeBytes(now.toString()+ '\n');
			}else if (clientSentence.equals("timezone")){
				outToClient.writeBytes(timezone.getDisplayName()+ '\n');

			}else if (clientSentence.equals("OSname")){
				outToClient.writeBytes(System.getProperty("os.name")+ '\n');
				
			}else if (clientSentence.equals("OSversion")){
				outToClient.writeBytes(System.getProperty("os.version")+ '\n');
			}else if (clientSentence.equals("user")){
				outToClient.writeBytes(System.getProperty("user.name")+ '\n');
			}
			else if (clientSentence.equals("exit")){
				//empty msg
				outToClient.writeBytes("\n");
			}
			else{
				//empty msg
				outToClient.writeBytes("\n");

			}
			
			
		
		}
	}
}