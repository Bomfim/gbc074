package server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

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
            String match = "SAO vs FLA";
            // TODO treat witch match fan will choose.
            fan.addSubscriber(match);

            showHistoryMessages(match, writer);

            while (true) {
                List<Message> subscriberMessages;

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

    private void showHistoryMessages(String match, PrintWriter writer) {
        try {
            File file = new File(match + ".txt");

            if (file.exists()) {
                Scanner sc = new Scanner(file);

                while (sc.hasNextLine())
                    writer.println("Reporter: " + match + " : " + sc.nextLine());
                sc.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
