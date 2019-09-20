package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import pubsub.publisher.Publisher;
import pubsub.publisher.PublisherImpl;
import pubsub.service.PubSubService;

public class ReporterThread extends Thread {
	private Socket socket;
	private Scanner s = new Scanner(System.in);
	private PubSubService pubSubService;
	private String text;

	public ReporterThread(Socket socket, PubSubService pubSubService) {
		this.socket = socket;
		this.pubSubService = pubSubService;
	}

	public void run() {
		try {

			Publisher reporter = new PublisherImpl();
			
			do {
                text = s.nextLine();
                
                System.out.println("Messages of São paulino Subscriber are:");
 
            } while (!text.equals("bye"));

			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
