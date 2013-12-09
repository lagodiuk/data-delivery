package com.city.datadelivery.base.producer;

import com.city.datadelivery.base.Message;


public interface MessageProducer {

	boolean hasMoreMessages();

	Message nextMessage();
}
