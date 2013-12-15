package com.citi.datadelivery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;
import com.citi.datadelivery.base.producer.MessageProducer;

public class ReadingFileProducer implements MessageProducer {

	private static final Logger LOGGER =
			Logger.getLogger(ReadingFileProducer.class.getName());

	private static final String DELIMITER_REGEXP = "\\|";

	private BufferedReader reader;

	private String nextLine;

	public ReadingFileProducer(InputStream in) throws FileNotFoundException {
		this.reader = new BufferedReader(new InputStreamReader(in));
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
		Message message = this.parse(this.nextLine);
		return message;
	}

	private Message parse(String s) {
		String[] parts = s.split(DELIMITER_REGEXP);

		MessageBuilder messageBuilder =
				new MessageBuilder(parts[0])
						.withName(parts[1])
						.withAge(parts[2])
						.withAddress(parts[3])
						.withCity(parts[4])
						.withPostalCode(parts[5]);

		return messageBuilder.createMessage();
	}
}
