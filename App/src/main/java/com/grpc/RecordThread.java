package com.grpc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.pubsub.Message;

public class RecordThread extends Thread {

    private BlockingQueue<Message> blockingQueue = new LinkedBlockingDeque<Message>();

    public RecordThread(BlockingQueue<Message> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            Message message = blockingQueue.take();
            File file = new File(message.getMatch() + ".txt");

            if (file.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                writer.write(message.getId() + " " + message.getPayload());
                writer.newLine(); // Add new line
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(message.getId() + " " + message.getPayload());
                writer.newLine(); // Add new line
                writer.close();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
