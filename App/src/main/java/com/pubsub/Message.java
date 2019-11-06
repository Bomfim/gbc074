package com.pubsub;

public class Message {
	private String id;
	private String match;
	private String payload;

	public Message() {
	}

	public Message(String id, String match, String payload) {
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
