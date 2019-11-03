package com.grpc;

import java.io.IOException;

import io.grpc.*;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051).addService(new ReporterServiceImpl())
                .addService(new FanServiceImpl()).build();

        server.start();

        System.out.println("Server started at: " + 50051);
        server.awaitTermination();
    }
}
