package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final String FAN_MESSAGE_TEMPLATE = "[FAN] => [%s]";

	private static Long connectionTimes = 5L;

	static void ehlo(Socket socket, String code) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.println(code + "\n");
		writer.flush();
	}

	static void fan(Socket socket) {
		LOGGER.info("I'm a fan!");

		try (InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			String line;

			while ((line = reader.readLine()) != null) {
				LOGGER.info(String.format(FAN_MESSAGE_TEMPLATE, line));
			}

			throw new PeerClosedException("Closed connection");
		} catch (IOException e) {
			LOGGER.info("Error reader: " + e.getMessage());
		}
	}

	static boolean connect(String hostname, int port) {
		try (Scanner scanner = new Scanner(System.in)) {
			Socket socket = new Socket(hostname, port);
			LOGGER.info("Are you a reporter (y/n): ");

			if (scanner.nextLine().equalsIgnoreCase("y")) {
				ehlo(socket, "NEW_REPORTER_CONNECTED");
				new Reporter(socket).run();
			} else {
				ehlo(socket, "NEW_FAN_CONNECTED");
				fan(socket);
			}

		} catch (PeerClosedException | IOException ex) {
			LOGGER.info("Peer disconnected, need to reconnect");
			return false;
		}

		return true;
	}

	static boolean decrement() {
		return --connectionTimes > 0;
	}

	public static void main(String[] args) {

		while (!connect("localhost", 50051) && decrement()) {
			LOGGER.info("Need reconnect");
		}

		LOGGER.info("Exiting.");
	}

	protected static class PeerClosedException extends RuntimeException {
		PeerClosedException(String msg) {
			super(msg);
		}
	}
}
