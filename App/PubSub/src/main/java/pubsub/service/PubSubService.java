package pubsub.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import pubsub.Message;
import pubsub.subscriber.Subscriber;

public class PubSubService {

	// Keeps set of subscriber topic wise, using set to prevent duplicates.
	Map<String, Set<Subscriber>> subscribersMatchMap = new HashMap<String, Set<Subscriber>>();

	// Holds messages published by publishers.
	Queue<Message> messagesQueue = new LinkedList<Message>();

	// Adds message sent by publisher to queue
	public void addMessageToQueue(Message message) {
		messagesQueue.add(message);
	}

	// Add a new Subscriber for a match.
	public void addSubscriber(String match, Subscriber subscriber) {

		if (subscribersMatchMap.containsKey(match)) {
			Set<Subscriber> subscribers = subscribersMatchMap.get(match);
			subscribers.add(subscriber);
			subscribersMatchMap.put(match, subscribers);
		} else {
			Set<Subscriber> subscribers = new HashSet<Subscriber>();
			subscribers.add(subscriber);
			subscribersMatchMap.put(match, subscribers);
		}
	}

	// Remove an existing subscriber for a match
	public void removeSubscriber(String match, Subscriber subscriber) {

		if (subscribersMatchMap.containsKey(match)) {
			Set<Subscriber> subscribers = subscribersMatchMap.get(match);
			subscribers.remove(subscriber);
			subscribersMatchMap.put(match, subscribers);
		}
	}

	// Broadcast new messages added in queue to all subscribers of the match.
	// messagesQueue will be empty after broadcasting...
	public void broadcast() {
		if (messagesQueue.isEmpty()) {
			System.out.println("No messages from publishers to display");
		} else {
			while (!messagesQueue.isEmpty()) {
				Message message = messagesQueue.remove();
				String match = message.getMatch();

				Set<Subscriber> subscribersOfTopic = subscribersMatchMap.get(match);

				for (Subscriber subscriber : subscribersOfTopic) {
					// add broadcasted message to subscribers message queue
					List<Message> subscriberMessages = subscriber.getSubscriberMessages();
					subscriberMessages.add(message);
					subscriber.setSubscriberMessages(subscriberMessages);
				}
			}
		}
	}

	// Sends messages about a match for subscriber at any point
	public void getMessagesForSubscriberOfTopic(String match, Subscriber subscriber) {
		if (messagesQueue.isEmpty()) {
			System.out.println("No messages from publishers to display");
		} else {
			while (!messagesQueue.isEmpty()) {
				Message message = messagesQueue.remove();

				if (message.getMatch().equalsIgnoreCase(match)) {

					Set<Subscriber> subscribersOfMatch = subscribersMatchMap.get(match);

					for (Subscriber _subscriber : subscribersOfMatch) {
						if (_subscriber.equals(subscriber)) {
							// add broadcasted message to subscriber message queue
							List<Message> subscriberMessages = subscriber.getSubscriberMessages();
							subscriberMessages.add(message);
							subscriber.setSubscriberMessages(subscriberMessages);
						}
					}
				}
			}
		}
	}
}