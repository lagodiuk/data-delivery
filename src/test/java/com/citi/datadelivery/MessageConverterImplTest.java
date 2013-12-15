package com.citi.datadelivery;

import org.junit.Assert;
import org.junit.Test;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;

public class MessageConverterImplTest {

	@Test
	public void testMessageConverting() {
		Message parsedMessage = this.converter.stringToMessage(this.exampleMessageString);
		Assert.assertEquals(this.exampleMessage, parsedMessage);

		String createdString = this.converter.messageToString(this.exampleMessage);
		Assert.assertEquals(this.exampleMessageString, createdString);
	}

	private MessageConverterImpl converter =
			new MessageConverterImpl();

	private Message exampleMessage =
			new MessageBuilder("1")
					.withName("Test Name")
					.withAge(1)
					.withCity("Test city")
					.withAddress("Test address")
					.withPostalCode("111-111")
					.createMessage();

	private String exampleMessageString =
			"1|Test Name|1|Test address|Test city|111-111";
}
