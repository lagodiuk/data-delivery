package com.citi.datadelivery.base.producer;

import com.citi.datadelivery.base.Message;

public interface MessageProducer {

	boolean hasMoreMessages();

	Message nextMessage();

	void cleanUp();
}
