package server;

import java.io.IOException;
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

				if (isReporter) {
					isReporter = true;
					new ReporterThread(socket, pubSubService).start();
				}
				else {
					new FanThread(socket, pubSubService).start();
				}
			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
