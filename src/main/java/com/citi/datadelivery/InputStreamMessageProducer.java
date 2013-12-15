package com.citi.datadelivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageConverter;
import com.citi.datadelivery.base.producer.MessageProducer;

public class InputStreamMessageProducer implements MessageProducer {

	private static final Logger LOGGER =
			Logger.getLogger(InputStreamMessageProducer.class.getName());

	private MessageConverter messageConverter;

	private BufferedReader reader;

	private String nextLine;

	public InputStreamMessageProducer(InputStream in) {
		this(in, new MessageConverterImpl());
	}

	public InputStreamMessageProducer(InputStream in, MessageConverter messageConverter) {
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.messageConverter = messageConverter;
	}

	@Override
	public boolean hasMoreMessages() {
		try {
			this.nextLine = this.reader.readLine();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Error while reading input data");
			return false;
		}
		return this.nextLine != null;
	}

	@Override
	public Message nextMessage() {
		Message message = this.messageConverter.stringToMessage(this.nextLine);
		return message;
	}
}
