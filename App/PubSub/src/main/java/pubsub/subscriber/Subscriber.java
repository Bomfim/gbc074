package pubsub.subscriber;

import java.util.ArrayList;
import java.util.List;
import pubsub.Message;
import pubsub.service.PubSubService;

public abstract class Subscriber {

	// Store all messages received by the subscriber.
	private List<Message> subscriberMessages = new ArrayList<Message>();

	public List<Message> getSubscriberMessages() {
		return subscriberMessages;
	}

	public void setSubscriberMessages(List<Message> subscriberMessages) {
		this.subscriberMessages = subscriberMessages;
	}

	/// Add subscriber with PubSubService for a match.
	public abstract void addSubscriber(String match, PubSubService pubSubService);

	// Unsubscribe subscriber with PubSubService for a match.
	public abstract void unSubscribe(String match, PubSubService pubSubService);

	// Request specifically for messages related to a match from PubSubService
	public abstract void getMessagesForSubscriberOfMatch(String match, PubSubService pubSubService);

	// Print all messages received by the subscriber
	public void printMessages() {
		for (Message message : subscriberMessages) {
			System.out.println("Match Message ---> " + message.getMatch() + " : " + message.getPayload());
		}
	}
}
