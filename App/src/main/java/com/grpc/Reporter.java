package com.grpc;

import java.util.*;

import com.grpc.ReporterServiceGrpc.ReporterServiceBlockingStub;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Reporter {
    public static void main(String[] args) throws Exception {
        List<Integer> ports = new ArrayList<>();

        ports.add(50051);
        ports.add(50052);
        ports.add(50053);
        Collections.shuffle(ports);

        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:"+ ports.get(0)).usePlaintext(true).build();

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