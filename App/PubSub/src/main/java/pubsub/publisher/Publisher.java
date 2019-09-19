package pubsub.publisher;

import pubsub.Message;
import pubsub.service.PubSubService;

public interface Publisher {

	void publish(Message message, PubSubService pubSubService);

}
