package com.city.datadelivery.base.consumer;

import com.city.datadelivery.base.Message;


public interface MessageConsumer {

	void processMessage(Message message);
}
