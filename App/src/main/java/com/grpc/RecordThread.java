package com.grpc;

import com.pubsub.Message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RecordThread extends Thread {
    private Message message;

    public RecordThread(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            File file = new File(message.getMatch() + ".txt");

            if (file.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                writer.write(message.getId() + "-" + message.getPayload());
                writer.newLine(); // Add new line
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(message.getId() + "-" + message.getPayload());
                writer.newLine(); // Add new line
                writer.close();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
