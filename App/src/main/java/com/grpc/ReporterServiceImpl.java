package com.grpc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.grpc.ReporterServiceGrpc.ReporterServiceImplBase;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;
import com.grpc.StreamingService.RequestResponse.Status;
import com.pubsub.Message;
import com.pubsub.publisher.Publisher;
import com.pubsub.publisher.PublisherImpl;
import com.pubsub.service.PubSubService;

import io.grpc.stub.StreamObserver;

public class ReporterServiceImpl extends ReporterServiceImplBase {
    //TODO:remover hardCode, gerar um id pra cada reporter
    Publisher reporter = new PublisherImpl("192.168.0.1");
    private BlockingQueue<Message> blockingQueue = new LinkedBlockingDeque<Message>();
    private Message message;

    @Override
    public void publish(Match request, StreamObserver<RequestResponse> responseObserver) {

        try {
            message = new Message(request.getId(), request.getPlayers(),
                    new SimpleDateFormat("HH:mm").format(new Date()) + ' ' + request.getComment());
            reporter.publish(message);
            //TODO:remover hardCode, gerar um id pra cada reporter
            PubSubService.getInstance("192.168.0.1").broadcast();
            blockingQueue.put(message);
            new RecordThread(blockingQueue).start();

            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.ACK).build();
            responseObserver.onNext(response);

        } catch (Exception e) {
            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.NACK).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}