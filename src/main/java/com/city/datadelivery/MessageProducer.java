package com.city.datadelivery;

public interface MessageProducer {

	boolean hasMoreMessages();

	Message nextMessage();
}
