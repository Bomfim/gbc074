package client;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Main {

	public static void main(String[] args) {

		String hostname = "localhost";
		int port = 50051;
		Scanner s;

		try (Socket socket = new Socket(hostname, port)) {

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			s = new Scanner(System.in);
			String text;

			do {
				System.out.print("Enter a text: ");
				text = s.nextLine();

				writer.println(text);

				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));

				String time = reader.readLine();

				System.out.println(time);

			} while (!text.equals("bye"));

			socket.close();

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}
