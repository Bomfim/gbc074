package com.grpc;

import com.grpc.ReporterServiceGrpc.ReporterServiceImplBase;
import com.grpc.StreamingService.Match;
import io.grpc.stub.StreamObserver;

public class ReporterServiceImpl extends ReporterServiceImplBase {

    @Override
    public void publish(Match request, StreamObserver<Match> responseObserver) {
        // HelloRequest has toString auto-generated.
        System.out.println(request);

        // You must use a builder to construct a new Protobuffer object
        Match response = Match.newBuilder().setId(0).setPlayers("")
                .setComment("").build();

        // Use responseObserver to send a single response back
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onNext(response);

        // When you are done, you must call onCompleted.
        responseObserver.onCompleted();
    }
}