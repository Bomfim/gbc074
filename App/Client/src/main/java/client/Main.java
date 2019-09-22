package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		String hostname = "localhost", text;
		int port = 50051;
		Scanner scanner = new Scanner(System.in);

		try (Socket socket = new Socket(hostname, port)) {

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			writer.println("is there a reporter(y/n) ?");
			if (reader.readLine().equals("n")) {
				System.out.println("I'm the reporter \\o//");
				while (true) {
					text = scanner.nextLine();
					writer.println(text);
				}
			} else {
				while (true) {
					System.out.println("I'm a fan!");
					System.out.println(reader.readLine());
				}
			}
		} catch (

		UnknownHostException ex) {
			scanner.close();
			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {
			scanner.close();
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}
