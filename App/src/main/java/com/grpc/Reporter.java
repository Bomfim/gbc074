package com.grpc;

import com.grpc.ReporterServiceGrpc.ReporterServiceStub;
import com.grpc.StreamingService.Match;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

public class Reporter {
    public static void main(String[] args) throws Exception {
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext(true).build();

        // ReporterServiceBlockingStub stub =
        // ReporterServiceGrpc.newBlockingStub(channel);
        ReporterServiceStub stub = ReporterServiceGrpc.newStub(channel);

        Match req = Match.newBuilder().setId(2).setPlayers("COR VS PAL").setComment("Olha a batiiiida!").build();

        stub.publish(req, new StreamObserver<Match>() {

            @Override
            public void onNext(Match value) {
                System.out.println(value);

            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                channel.shutdownNow();
            }
        });

    }
}