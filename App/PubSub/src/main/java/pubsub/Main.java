package pubsub;

import pubsub.publisher.Publisher;
import pubsub.publisher.PublisherImpl;
import pubsub.service.PubSubService;
import pubsub.subscriber.Subscriber;
import pubsub.subscriber.SubscriberImpl;

public class Main {

	public static void main(String args[]) {

		// Instantiate publishers, subscribers and PubSubService
		Publisher reporter = new PublisherImpl();

		Subscriber SAOSubscriber = new SubscriberImpl();
		Subscriber allMatchesSubscriber = new SubscriberImpl();
		Subscriber PALSubscriber = new SubscriberImpl();

		// Declare Messages and Publish Messages to PubSubService
		Message Msg1 = new Message("SAO vs FLA", "1x0");
		Message Msg2 = new Message("COR vs FLU", "0x2");
		Message Msg3 = new Message("PAL vs SAN", "0x0");

		reporter.publish(Msg1);
		reporter.publish(Msg2);
		reporter.publish(Msg3);

		// Declare Subscribers
		SAOSubscriber.addSubscriber("SAO vs FLA");
		PALSubscriber.addSubscriber("PAL vs SAN");
		allMatchesSubscriber.addSubscriber("COR vs FLU");
		allMatchesSubscriber.addSubscriber("SAO vs FLA");
		allMatchesSubscriber.addSubscriber("PAL vs SAN");

		// Trying unSubscribing a subscriber
		allMatchesSubscriber.unSubscribe("PAL vs SAN");

		// Broadcast message to all subscribers. After broadcast, messageQueue will be
		// empty in PubSubService.
		PubSubService.getInstance().broadcast();

		// Print messages of each subscriber to see which messages they got
		System.out.println("Messages of Sao paulino Subscriber are: ");
		SAOSubscriber.printMessages();

		System.out.println("\nMessages of Palmeirense Subscriber are: ");
		PALSubscriber.printMessages();

		System.out.println("\nMessages sem time Subscriber are: ");
		allMatchesSubscriber.printMessages();

		// After broadcast the messagesQueue will be empty, so publishing new messages
		// to server
		System.out.println("\nPublishing 2 more Java Messages...");
		Message Msg4 = new Message("SAO vs FLA", "1x1");
		Message Msg5 = new Message("SAO vs FLA", "2x1");

		reporter.publish(Msg4);
		reporter.publish(Msg5);

		SAOSubscriber.getMessagesForSubscriberOfMatch("SAO vs FLA");
		System.out.println("\nMessages of Sao paulino Subscriber now are: ");
		SAOSubscriber.printMessages();
	}
}
