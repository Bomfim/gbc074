package server;

import java.io.IOException;
import java.net.Socket;
import pubsub.service.PubSubService;
import pubsub.subscriber.Subscriber;
import pubsub.subscriber.SubscriberImpl;

public class FanThread extends Thread {

	private Socket socket;
	private PubSubService pubSubService;

	public FanThread(Socket socket, PubSubService pubSubService) {
		this.socket = socket;
		this.pubSubService = pubSubService;
	}

	public void run() {
		try {

			Subscriber fan = new SubscriberImpl();

			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
