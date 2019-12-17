package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainFan {

	public static void main(String[] args) {

		String hostname = "127.0.0.1", text;
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
			writer.println("isUser");
			System.out.println("I'm a fan!");
			 while (true) {
					text = reader.readLine();
					System.out.println(text);
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
