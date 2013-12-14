package com.citi.datadelivery.base.consumer;

import com.citi.datadelivery.base.Message;


public interface MessageConsumer {

	void processMessage(Message message);
}
