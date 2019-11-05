package com.pubsub.publisher;

import com.pubsub.Message;
import com.pubsub.service.PubSubService;

public class PublisherImpl implements Publisher {

	public String requestId;

	public PublisherImpl(String requestId) {
		this.requestId = requestId;
	}

	public void publish(Message message) {
		PubSubService.getInstance(requestId).addMessageToQueue(message);

	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
