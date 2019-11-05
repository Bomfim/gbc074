package com.pubsub.subscriber;

import com.pubsub.service.PubSubService;

public class SubscriberImpl extends Subscriber {

	public String requestId;

	// Add subscriber with PubSubService for a match.
	@Override
	public void addSubscriber(String match) {
		PubSubService.getInstance(getRequestId()).addSubscriber(match, this);

	}

	// Unsubscribe subscriber with PubSubService for a match
	@Override
	public void unSubscribe(String match) {
		PubSubService.getInstance(getRequestId()).removeSubscriber(match, this);

	}

	// Request specifically for messages related to a match from PubSubService
	@Override
	public void getMessagesForSubscriberOfMatch(String match) {
		PubSubService.getInstance(getRequestId()).getMessagesForSubscriberOfMatch(match, this);

	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
