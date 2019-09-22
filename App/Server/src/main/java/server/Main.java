package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import pubsub.service.PubSubService;

public class Main {

	public static void main(String args[]) {

		int port = 50051;
		boolean isReporter = false;
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			PubSubService pubSubService = new PubSubService();

			System.out.println("Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New client connected");

				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));

				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);

				if (reader.readLine().equals("is there a reporter(y/n) ?") && !isReporter) {
					writer.println("n");
					isReporter = true;
					new ReporterThread(socket, pubSubService).start();
				} else {
					writer.println("y");
					new FanThread(socket, pubSubService).start();
				}

			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
