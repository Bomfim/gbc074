package pubsub.publisher;

import pubsub.Message;

public interface Publisher {

	void publish(Message message);

}
