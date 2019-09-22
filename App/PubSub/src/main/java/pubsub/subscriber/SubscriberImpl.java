package pubsub.subscriber;

import pubsub.service.PubSubService;

public class SubscriberImpl extends Subscriber {

	// Add subscriber with PubSubService for a match.
	@Override
	public void addSubscriber(String match, PubSubService pubSubService) {
		pubSubService.addSubscriber(match, this);

	}

	// Unsubscribe subscriber with PubSubService for a match
	@Override
	public void unSubscribe(String match, PubSubService pubSubService) {
		pubSubService.removeSubscriber(match, this);

	}

	// Request specifically for messages related to a match from PubSubService
	@Override
	public void getMessagesForSubscriberOfMatch(String match, PubSubService pubSubService) {
		pubSubService.getMessagesForSubscriberOfMatch(match, this);

	}

}
