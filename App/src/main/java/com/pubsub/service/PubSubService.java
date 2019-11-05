package com.pubsub.service;

import java.util.*;

import com.pubsub.Message;
import com.pubsub.service.consistentHash.ConsistentHashRouter;
import com.pubsub.service.consistentHash.Node;
import com.pubsub.subscriber.Subscriber;

public final class PubSubService implements Node {

	private static PubSubService INSTANCE;

	private static ConsistentHashRouter<PubSubService> consistentHashRouter;

	private final String ip;
	private final int port;

	public PubSubService(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public static synchronized PubSubService getInstance(String requestId) {
		if(consistentHashRouter == null){
			PubSubService node1 = new PubSubService("127.0.0.1",8080);
			PubSubService node2 = new PubSubService("127.0.0.1",8081);
			PubSubService node3 = new PubSubService("127.0.0.1",8082);

			consistentHashRouter = new ConsistentHashRouter<>(Arrays.asList(node1,node2,node3),10);
		}
		return consistentHashRouter.routeNode(requestId);
	}

	// Keeps set of subscriber match wise, using set to prevent duplicates.
	public Map<String, Set<Subscriber>> subscribersMatchMap = new HashMap<String, Set<Subscriber>>();

	// Holds messages published by publishers.
	public Queue<Message> messagesQueue = new LinkedList<Message>();

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

				Set<Subscriber> subscribersOfMatch = subscribersMatchMap.getOrDefault(match, Collections.emptySet());

				for (Subscriber subscriber : subscribersOfMatch) {
					// add broadcasted message to subscribers message queue
					List<Message> subscriberMessages = subscriber.getSubscriberMessages();
					subscriberMessages.add(message);
					subscriber.setSubscriberMessages(subscriberMessages);
				}
			}
		}
	}

	// Sends messages about a match for subscriber at any point
	public void getMessagesForSubscriberOfMatch(String match, Subscriber subscriber) {
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

	@Override
	public String getKey() {
		return ip + ":" + port;
	}
}
