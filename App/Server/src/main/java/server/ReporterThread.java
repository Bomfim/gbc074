package server;

import pubsub.Message;
import pubsub.publisher.Publisher;
import pubsub.publisher.PublisherImpl;
import pubsub.service.PubSubService;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporterThread extends Thread {
    private Socket socket;

    public ReporterThread(Socket socket) {
        this.socket = socket;
    }

    private void ack(PrintWriter writer) {
        writer.println("ACK");
    }

    @Override
    public void run() {
        try (InputStream input = this.socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true)) {

            System.out.println("New reporter!\n");
            Publisher reporter = new PublisherImpl();
            String text;

            while ((text = reader.readLine()) != null) {
                Message m = new Message("SAO vs FLA", new SimpleDateFormat("HH:mm").format(new Date()) + ' ' + text);
                ack(writer);
                reporter.publish(m);
                PubSubService.getInstance().broadcast();
                new RecordThread(m).start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
