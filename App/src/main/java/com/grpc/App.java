package com.grpc;

import java.io.IOException;

import io.grpc.*;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051).addService(new ReporterServiceImpl())
                .addService(new FanServiceImpl()).build();
        Server server2 = ServerBuilder.forPort(50052).addService(new ReporterServiceImpl())
                .addService(new FanServiceImpl()).build();
        Server server3 = ServerBuilder.forPort(50053).addService(new ReporterServiceImpl())
                .addService(new FanServiceImpl()).build();


        server.start();
        server2.start();
        server3.start();

        System.out.println("Server started at: " + 50051);
        System.out.println("Server2 started at: " + 50052);
        System.out.println("Server3 started at: " + 50053);

        server.awaitTermination();
    }
}
