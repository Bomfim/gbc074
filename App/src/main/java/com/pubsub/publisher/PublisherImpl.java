package com.pubsub.publisher;

import com.pubsub.Message;
import com.pubsub.service.PubSubService;

public class PublisherImpl implements Publisher {

	public void publish(Message message) {
		PubSubService.getInstance().addMessageToQueue(message);

	}

}
