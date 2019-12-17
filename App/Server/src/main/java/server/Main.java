package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Arrays;

public class Main {

	public static void main(String args[]) {

		// testCRUD();

		int port = 50051;

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New client connected: " + socket);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String whoIs = reader.readLine();

				if (whoIs != null && whoIs.equals("NEW_REPORTER_CONNECTED")) {
					new ReporterThread(socket).start();
				} else {
					new FanThread(socket).start();
				}
			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
