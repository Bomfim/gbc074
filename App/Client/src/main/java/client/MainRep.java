package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainRep {

	public static void main(String[] args) {

		String hostname = "localhost", text;
		List<Integer> portList = new ArrayList<>() ;
		for(int i = 1; i <= 5 ; i++){
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
			writer.println("isUser-isReporter");

				System.out.println("I'm the reporter \\o//");
				while (true) {
					text = scanner.nextLine();
					writer.println(text);
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
