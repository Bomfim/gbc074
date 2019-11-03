package com.pubsub;

public class Message {
	private int id;
	private String match;
	private String payload;

	public Message() {
	}

	public Message(int id, String match, String payload) {
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

	public int getId() {
		return id;
	}

	public void setPayload(int id) {
		this.id = id;
	}

}
