package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pubsub.Message;

public class RecordThread extends Thread {
    private Message message;

    public RecordThread(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            File file = new File(message.getMatch()+".txt");

            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                writer.write(message.getPayload());
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.newLine(); // Add new line
                writer.write(message.getPayload());
                writer.close();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
