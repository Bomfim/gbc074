package com.grpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.grpc.FanServiceGrpc.FanServiceBlockingStub;
import com.grpc.FanServiceGrpc.FanServiceStub;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Fan {
    public static void main(String[] args) throws Exception {
        List<Integer> ports = new ArrayList<>();

        ports.add(50051);
        ports.add(50052);
        ports.add(50053);
        Collections.shuffle(ports);

        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:"+ ports.get(0)).usePlaintext(true).build();

        FanServiceBlockingStub blockingStub = FanServiceGrpc.newBlockingStub(channel);
        FanServiceStub stub = FanServiceGrpc.newStub(channel);
        Scanner s = new Scanner(System.in);

        Match req = Match.newBuilder().setPlayers("SAO VS FLA").build();

        RequestResponse res = blockingStub.subscribe(req);

        if (res.getResponse() == RequestResponse.Status.ACK) {
            stub.getSubscriberHistoryMessages(req, new StreamObserver<Match>() {

                @Override
                public void onNext(Match value) {
                    System.out.println(value.getPlayers());
                    System.out.println(value.getComment());
                }

                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onCompleted() {
                }
            });

            while (true) {
                stub.getSubscriberMessages(req, new StreamObserver<Match>() {

                    @Override
                    public void onNext(Match value) {
                        System.out.println(value.getPlayers());
                        System.out.println(value.getComment());
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onCompleted() {

                        if (!channel.isShutdown()) {

                        } else {
                            s.close();
                            channel.shutdownNow();
                        }
                    }
                });
                Thread.sleep(5000);
            }
        }
    }
}