package com.conference.business;

@SuppressWarnings("serial")
public class TimeNotAllowedException extends Exception {

	public TimeNotAllowedException(String message) {
		super(message);
	}

}
