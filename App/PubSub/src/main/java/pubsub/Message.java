package pubsub;

public class Message {
	private String match;
	private String payload;

	public Message() {
	}

	public Message(String match, String payload) {
		this.match = match;
		this.payload = payload;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
