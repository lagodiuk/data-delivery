package com.citi.datadelivery.base;

public interface MessageConverter {

	Message stringToMessage(String s);

	String messageToString(Message m);

}
