package pubsub.subscriber;

import pubsub.service.PubSubService;

public class SubscriberImpl extends Subscriber {

	// Add subscriber with PubSubService for a match.
	@Override
	public void addSubscriber(String match) {
		PubSubService.getInstance().addSubscriber(match, this);

	}

	// Unsubscribe subscriber with PubSubService for a match
	@Override
	public void unSubscribe(String match) {
		PubSubService.getInstance().removeSubscriber(match, this);

	}

	// Request specifically for messages related to a match from PubSubService
	@Override
	public void getMessagesForSubscriberOfMatch(String match) {
		PubSubService.getInstance().getMessagesForSubscriberOfMatch(match, this);

	}

}
