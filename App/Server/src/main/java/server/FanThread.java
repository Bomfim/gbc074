package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import pubsub.Message;
import pubsub.subscriber.Subscriber;
import pubsub.subscriber.SubscriberImpl;

public class FanThread extends Thread {
    private Socket socket;

    public FanThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream output = this.socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            System.out.println("New fan!\n");

            Subscriber fan = new SubscriberImpl();

            // TODO treat witch match fan will choose.
            fan.addSubscriber("SAO vs FLA");

            while (true) {
                List<Message> subscriberMessages;
                // fan.getMessagesForSubscriberOfMatch("SAO vs FLA");
                // fan.printMessages();

                subscriberMessages = fan.getSubscriberMessages();

                if (!subscriberMessages.isEmpty()) {
                    for (Message message : subscriberMessages) {
                        writer.println("Reporter: " + message.getMatch() + " : " + message.getPayload());
                    }
                    subscriberMessages.clear();
                    fan.setSubscriberMessages(subscriberMessages);
                }
                Thread.sleep(2000);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
