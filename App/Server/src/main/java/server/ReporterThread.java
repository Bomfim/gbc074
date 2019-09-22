package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import pubsub.Message;
import pubsub.publisher.Publisher;
import pubsub.publisher.PublisherImpl;
import pubsub.service.PubSubService;

public class ReporterThread extends Thread {
	private Socket socket;
	private PubSubService pubSubService;

	public ReporterThread(Socket socket, PubSubService pubSubService) {
		this.socket = socket;
		this.pubSubService = pubSubService;
	}

	public void run() {
		try {
			InputStream input = this.socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			Publisher reporter = new PublisherImpl();

			while (true) {
				Message m = new Message("SAO vs FLA", reader.readLine());
				reporter.publish(m, this.pubSubService);
				this.pubSubService.broadcast();
			}

			//socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
