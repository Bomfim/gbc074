package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import pubsub.Message;
import pubsub.publisher.Publisher;
import pubsub.publisher.PublisherImpl;
import pubsub.service.PubSubService;
import pubsub.subscriber.Subscriber;
import pubsub.subscriber.SubscriberImpl;

public class ServerThread extends Thread {
    private Socket socket;
    private PubSubService pubSubService = new PubSubService();
    private boolean isReporter = false;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = this.socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = this.socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            if (!this.isReporter) {
                Publisher reporter = new PublisherImpl();
                while (true) {
                    Message m = new Message("SAO vs FLA", reader.readLine());
                    reporter.publish(m, this.pubSubService);
                    this.pubSubService.broadcast();
                }
            } else {
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
                        Thread.sleep(1000);
                        // fan.setSubscriberMessages(Collections.<Message>emptyList());
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
