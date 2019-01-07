import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.regex.Pattern;
/*
 * Name: Kirusanth Thiruchelvam
 * Student No: 212918298
 * Course:EECS 3214
 * Professor: Natalija Vlajic
 * Title: PingClient
 * Description: PingClient requests over UDP.
 * Date: Apr 14,2015
 * ********************************************************************************/

public class PingClient {

	private static final int MAX_TIMEOUT = 1000; // 1000ms = 1s Time out for
	// IP address regex
	private static final String IPV4 = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static void main(String[] args) throws Exception {

		// create Pattern object to validate ip addresses
		Pattern reg = Pattern.compile(IPV4);

		// ping command
		String userInput;
		int sequenceNumber = 0;

		// check Command line argument
		if (args.length != 1) {
			System.out.println("Required arguments: port");
			return;
		}
		// Check whether port is valid or not
		else if (Integer.parseInt(args[0]) <= 1024) {
			System.out.println("Port number should be greater than 1024!");
			return;
		}
		// Correct Port ,so display the required inputs from user
		else {
			System.out.println("Command Syntax: ping <destination addr> <destination port>");
			System.out.println("Use CTRL^C to quit");
		}
		// Get port number from command line input and convert it to integer
		int clientPort = Integer.parseInt(args[0]);

		/*
		 * create a datagram socket for sending and receiving UDP packets Port
		 * number of client is given by user through command line (clientPort)
		 */

		DatagramSocket clientSocket = new DatagramSocket(clientPort);

		// create input stream to get ping command
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		// Get the ping command from user
		userInput = inFromUser.readLine();

		String[] commands = userInput.split(" ");
		// check user arguments
		if (commands.length != 2) {
			System.out.println("Required arguments: <destination Address> <port>");
			return;
		}

		// check the destination address is valid or not
		if (!reg.matcher(commands[0]).matches()) {
			System.out.println("Destionation Address is invalid!");
			return;
		}
		// check the validation of destination port
		if (Integer.parseInt(commands[1].trim()) <= 1024) {
			System.out.println("Destination Port number should be greater than 1024!");
			return;
		}

		// declaration of variables
		Inet4Address serverAddress = (Inet4Address) Inet4Address.getByName(commands[0]);
		int serverPort = Integer.parseInt(commands[1]);
		
        //send 9 requests
		while (sequenceNumber < 10) {
			// send packets
			// Timestamp for each packet
			Date timestamp = new Date();
			// create the message to send to server
			String sendDataMessage = " PING " + sequenceNumber + " " + timestamp + "\n";
			byte[] sendData = new byte[1024];
			sendData = sendDataMessage.getBytes();

			// create the ping packet to send to the server
			DatagramPacket ping = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

			// Send the packet through clientSocket (port)
			clientSocket.send(ping);
			// receive packets
			try {
				byte[] receiveData = new byte[1024];
				clientSocket.setSoTimeout(MAX_TIMEOUT);
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String modifiedSentence = new String(receivePacket.getData());
				String ipAddress = receivePacket.getAddress().getHostAddress();
				System.out.println("Received from " + ipAddress + ": " + modifiedSentence.trim());
			} catch (SocketTimeoutException e) {
				System.out.println("PING " + sequenceNumber + " Request timed out.");

			}
			// changing to next packet sequence number
			sequenceNumber++;

		}

		clientSocket.close();

	}

}