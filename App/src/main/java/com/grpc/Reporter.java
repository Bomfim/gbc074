package com.grpc;

import java.util.Scanner;
import java.util.UUID;

import com.grpc.ReporterServiceGrpc.ReporterServiceBlockingStub;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Reporter {
    public static void main(String[] args) throws Exception {
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext(true).build();

        ReporterServiceBlockingStub stub = ReporterServiceGrpc.newBlockingStub(channel);
        Scanner s = new Scanner(System.in);

        while (s.hasNextLine()) {

            Match req = Match.newBuilder().setId(UUID.randomUUID().toString()).setPlayers("SAO VS FLA").setComment(s.nextLine()).build();

            RequestResponse res = stub.publish(req);

            System.out.println(res);
        }
        
        s.close();
        channel.shutdownNow();
    }
}