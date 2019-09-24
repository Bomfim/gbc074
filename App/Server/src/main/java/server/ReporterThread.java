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

    public ReporterThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = this.socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            System.out.println("New reporter!\n");
            Publisher reporter = new PublisherImpl();
            
            while (true) {
                String text = reader.readLine();
                Message m = new Message("SAO vs FLA", text);
                reporter.publish(m);
                PubSubService.getInstance().broadcast();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
