package com.citi.datadelivery;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;

public class InputStreamMessageProducerTest {

	@Test
	public void testMessageProducing() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(this.testMessages.getBytes());
		InputStreamMessageProducer producer = new InputStreamMessageProducer(inputStream);

		Set<Message> producedMessages = new HashSet<Message>();

		while (producer.hasMoreMessages()) {
			Message m = producer.nextMessage();
			producedMessages.add(m);
		}

		Assert.assertEquals("Number of produced messages", 3, producedMessages.size());

		Assert.assertTrue("Produced messages contains expected messages",
				producedMessages.contains(this.expectedMessage1));

		Assert.assertTrue("Produced messages contains expected messages",
				producedMessages.contains(this.expectedMessage2));

		Assert.assertTrue("Produced messages contains expected messages",
				producedMessages.contains(this.expectedMessage3));
	}

	private String testMessages =
			String.format("1|Lastname_1, Firstname_1|10|Sample address 1|Sample city 1|111-111%n" +
					"2|Lastname_2, Firstname_2|20|Sample address 2|Sample city 2|222-222%n" +
					"3|Lastname_3, Firstname_3|30|Sample address 3|Sample city 3|333-333%n");

	private Message expectedMessage1 =
			new MessageBuilder("1")
					.withName("Lastname_1, Firstname_1")
					.withAge(10)
					.withAddress("Sample address 1")
					.withCity("Sample city 1")
					.withPostalCode("111-111")
					.createMessage();

	private Message expectedMessage2 =
			new MessageBuilder("2")
					.withName("Lastname_2, Firstname_2")
					.withAge(20)
					.withAddress("Sample address 2")
					.withCity("Sample city 2")
					.withPostalCode("222-222")
					.createMessage();

	private Message expectedMessage3 =
			new MessageBuilder("3")
					.withName("Lastname_3, Firstname_3")
					.withAge(30)
					.withAddress("Sample address 3")
					.withCity("Sample city 3")
					.withPostalCode("333-333")
					.createMessage();
}
