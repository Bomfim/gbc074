package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

import pubsub.Message;
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

			OutputStream output = this.socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			Subscriber fan = new SubscriberImpl();

			List<Message> subscriberMessages;

			// TODO treat witch match fan will choose.
			fan.addSubscriber("SAO vs FLA", this.pubSubService);

			while (true) {
				subscriberMessages = fan.getSubscriberMessages();
				if (!subscriberMessages.isEmpty()) {
					for (Message message : subscriberMessages) {
						writer.println("Reporter: " + message.getMatch() + " : " + message.getPayload());
					}
					Thread.sleep(4000);
					//fan.setSubscriberMessages(Collections.<Message>emptyList());
				}
			}
			// socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
