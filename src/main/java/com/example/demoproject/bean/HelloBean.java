package com.example.demoproject.bean;

public class HelloBean {
	
	
	private String message;

	public HelloBean(String msg) {
		this.message  = msg;
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}

	public String toString() {
		return String.format("Mesasge: %s", message);
	}
}
