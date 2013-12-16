package com.citi.datadelivery;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.citi.datadelivery.base.MessageBuilder;

public class CityMatchingConsumerTest {

	private static final String TEST_CITY = "Coil";

	private CityMatchingConsumer consumer;

	private ByteArrayOutputStream producedStream;

	private MessageConverterImpl messageConverter;

	@Before
	public void init() {
		this.producedStream = new ByteArrayOutputStream();
		this.consumer = new CityMatchingConsumer(TEST_CITY, this.producedStream);
		this.messageConverter = new MessageConverterImpl();
	}

	@Test
	public void transformingNamesTest() {

		Assert.assertNull(this.consumer.transformName(null));

		Assert.assertEquals("Firstname Lastname", this.consumer.transformName("Lastname, Firstname"));

		// Without comma
		Assert.assertEquals("Firstname Lastname", this.consumer.transformName("Firstname Lastname"));

		// Multiple commas
		Assert.assertEquals("A, B, C, D", this.consumer.transformName("A, B, C, D"));
	}

	@Test
	public void testFilteringMessagesByCity() {
		this.consumer.processMessage(new MessageBuilder("1").withCity(TEST_CITY).createMessage());
		this.consumer.processMessage(new MessageBuilder("2").withCity("NY").createMessage());
		this.consumer.processMessage(new MessageBuilder("3").withCity(TEST_CITY).createMessage());
		this.consumer.processMessage(new MessageBuilder("4").withCity("Palo Alto").createMessage());
		this.consumer.processMessage(new MessageBuilder("5").withCity(TEST_CITY).createMessage());

		String filteredMessagesString = this.producedStream.toString();

		String expectedMessagesString = String.format("%s%n%s%n%s%n",
				this.messageConverter.messageToString(new MessageBuilder("1").withCity(TEST_CITY).createMessage()),
				this.messageConverter.messageToString(new MessageBuilder("3").withCity(TEST_CITY).createMessage()),
				this.messageConverter.messageToString(new MessageBuilder("5").withCity(TEST_CITY).createMessage()));

		Assert.assertEquals(expectedMessagesString, filteredMessagesString);
	}

}
