import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.TimeZone;

/*
 * Name:Kirusanth Thiruchelvam
 * Student No: 212918298
 * Course:EECS 3214
 * Professor: Natalija Vlajic
 * Title: NosyServer
 * Description:	NosyServer process the requests of NosyClient over TCP.
 * Date: Apr 14,2015
 * ********************************************************************************/
public class NosyServer {

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("Required arguments: port");
			System.out.println("Command Syntax: java NosyServer <port>");
			return;
		}
		// Check whether port is valid or not
		if (Integer.parseInt(args[0]) <= 1024) {
			System.out.println("Port number should be greater than 1024!");
			return;
		}
		int port = Integer.parseInt(args[0]);

		String clientSentence;
		ServerSocket welcomeSocket = new ServerSocket(port);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			String menu = " = = = = = = = = = = = = = = = = Menu = = = = = = = = = = = = = = = = = = = = = =\n"
					+ " 	date - print the date and time of server's system                    \n"
					+ " 	timezone - print the time zone of server's system                    \n"
					+ " 	OSname - print the name of server's operating system (OS)            \n"
					+ " 	OSversion - print the of version number of server's OS               \n"
					+ " 	user - print the name of the user logged onto (i.e. running) the server \n"
					+ " 	exit - exit the program                                                  \n"
					+ " = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n"
					+ "Enter command > \n";
			Date now = new Date();
			TimeZone timezone = TimeZone.getDefault();

			outToClient.writeBytes(menu + '\n');

			clientSentence = inFromClient.readLine();
			if (clientSentence.equals("date")) {

				outToClient.writeBytes(now.toString() + '\n');
			} else if (clientSentence.equals("timezone")) {
				outToClient.writeBytes(timezone.getDisplayName() + '\n');

			} else if (clientSentence.equals("OSname")) {
				outToClient.writeBytes(System.getProperty("os.name") + '\n');

			} else if (clientSentence.equals("OSversion")) {
				outToClient.writeBytes(System.getProperty("os.version") + '\n');
			} else if (clientSentence.equals("user")) {
				outToClient.writeBytes(System.getProperty("user.name") + '\n');
			} else if (clientSentence.equals("exit")) {
				// empty msg
				outToClient.writeBytes("\n");
			} else {
				// empty msg
				outToClient.writeBytes("Invalid Output\n");

			}

		}
	}
}
