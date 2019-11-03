package com.grpc;

import java.util.Random;
import java.util.Scanner;

import com.grpc.ReporterServiceGrpc.ReporterServiceBlockingStub;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;

import io.grpc.*;

public class Reporter {
    public static void main(String[] args) throws Exception {
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext(true).build();

        ReporterServiceBlockingStub stub = ReporterServiceGrpc.newBlockingStub(channel);
        Scanner s = new Scanner(System.in);

        while (s.hasNextLine()) {

            Match req = Match.newBuilder().setId(new Random().nextInt(100)).setPlayers("SAO VS FLA").setComment(s.nextLine()).build();

            RequestResponse res = stub.publish(req);

            System.out.println(res);
        }
        
        s.close();
        channel.shutdownNow();
    }
}