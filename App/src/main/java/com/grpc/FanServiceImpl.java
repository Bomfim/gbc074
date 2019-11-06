package com.grpc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.grpc.FanServiceGrpc.FanServiceImplBase;
import com.grpc.StreamingService.Match;
import com.grpc.StreamingService.RequestResponse;
import com.grpc.StreamingService.RequestResponse.Status;
import com.pubsub.Message;
import com.pubsub.subscriber.Subscriber;
import com.pubsub.subscriber.SubscriberImpl;

import io.grpc.stub.StreamObserver;

public class FanServiceImpl extends FanServiceImplBase {

    Subscriber fan = new SubscriberImpl();

    @Override
    public void getSubscriberMessages(Match request, StreamObserver<Match> responseObserver) {
        List<Message> subscriberMessages;

        subscriberMessages = fan.getSubscriberMessages();

        if (!subscriberMessages.isEmpty()) {
            for (Message message : subscriberMessages) {
                Match response = Match.newBuilder().setId(message.getId()).setPlayers(message.getMatch())
                        .setComment(message.getPayload()).build();
                responseObserver.onNext(response);
            }
            subscriberMessages.clear();
            fan.setSubscriberMessages(subscriberMessages);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getSubscriberHistoryMessages(Match request, StreamObserver<Match> responseObserver) {

        try {
            File file = new File(request.getPlayers() + ".txt");

            if (file.exists()) {
                Scanner sc = new Scanner(file);

                while (sc.hasNextLine()) {
                    String m = sc.nextLine();
                    String id = m.substring(0, 36);
                    Match response = Match.newBuilder().setId(id).setPlayers(request.getPlayers())
                            .setComment(m.substring(37)).build();
                    responseObserver.onNext(response);
                }
                sc.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        responseObserver.onCompleted();
    }

    @Override
    public void subscribe(Match request, StreamObserver<RequestResponse> responseObserver) {
        try {
            fan.addSubscriber(request.getPlayers());
            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.ACK).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.NACK).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();

    }

    @Override
    public void unsubscribe(Match request, StreamObserver<RequestResponse> responseObserver) {
        try {
            fan.unSubscribe(request.getPlayers());
            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.ACK).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            RequestResponse response = RequestResponse.newBuilder().setResponse(Status.NACK).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();

    }
}