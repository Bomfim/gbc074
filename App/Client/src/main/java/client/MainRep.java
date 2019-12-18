package client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class MainRep {

	public static void main(String[] args) {

		String hostname = "localhost", text;
		List<Integer> portList = new ArrayList<>() ;
		int numberOfServers = 0;
		Properties props = new Properties();
		try {
			FileInputStream file = new FileInputStream(
					"./config.properties");
			props.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// take arguments
		numberOfServers = Integer.valueOf(props.getProperty("prop.replicas.quantidade"));
		for(int i = 1; i <= numberOfServers ; i++){
			portList.add(5000 + i);
		}
		Collections.shuffle(portList) ;
		Scanner scanner = new Scanner(System.in);

		try (Socket socket = new Socket(hostname, portList.get(0))) {

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			System.out.println("Connecting to " + hostname+ " on port " +  portList.get(0));
			System.out.println("Just connected to "+ socket.getRemoteSocketAddress());
			System.out.println("Hello from "+ socket.getLocalSocketAddress());
			OutputStream outToServer = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF("{\"isUser\":true,\"isReporter\":true}");
				System.out.println("I'm the reporter \\o//");
				while (true) {
					text = scanner.nextLine();
					out.writeUTF(text);
				}
		} catch (UnknownHostException ex) {
			scanner.close();
			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {
			scanner.close();
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}
