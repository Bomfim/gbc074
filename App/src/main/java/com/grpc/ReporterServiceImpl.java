package com.grpc;

import com.grpc.ReporterServiceGrpc.ReporterServiceImplBase;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;
import com.grpc.StreamingService.RequestResponse.Status;
import com.pubsub.Message;
import com.pubsub.publisher.Publisher;
import com.pubsub.publisher.PublisherImpl;
import com.pubsub.service.PubSubService;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.grpc.stub.StreamObserver;

public class ReporterServiceImpl extends ReporterServiceImplBase {

    Publisher reporter = new PublisherImpl();
    Message m;

    @Override
    public void publish(Match request, StreamObserver<RequestResponse> responseObserver) {

        try {
            m = new Message(request.getId(), request.getPlayers(),
                    new SimpleDateFormat("HH:mm").format(new Date()) + ' ' + request.getComment());
            reporter.publish(m);
            PubSubService.getInstance().broadcast();
            new RecordThread(m).start();

            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.ACK).build();
            responseObserver.onNext(response);

        } catch (Exception e) {
            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.NACK).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}