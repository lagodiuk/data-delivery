package com.citi.datadelivery.cucumber;

import gherkin.formatter.model.DataTableRow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

import com.citi.datadelivery.AverageAgeConsumer;
import com.citi.datadelivery.CityMatchingConsumer;
import com.citi.datadelivery.GreaterAgeConsumer;
import com.citi.datadelivery.InputStreamMessageProducer;
import com.citi.datadelivery.base.DeliveryManager;
import com.citi.datadelivery.base.consumer.MessageConsumer;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MessagesIntegrationTest {

	private static final int PRODUCER_THREADS_NUM = 10;

	private static final int CONSUMER_THREADS_NUM = 10;

	private static final int MESSAGE_QUEUE_CAPACITY = 30;

	private static final String NEW_LINE = String.format("%n");

	private static final String DELIMITER = String.format("|");

	private String inputData;

	private String actualOutputData;

	private AverageAgeConsumer.Result actualAverageAge;

	@Given("^following input rows$")
	public void following_rows(DataTable table) throws Throwable {
		this.inputData = this.tableToString(table);
	}

	@When("^filtering messages by age of (\\d+)$")
	public void filtering_messages_by_age_of(int age) throws Throwable {

		ByteArrayOutputStream consumerOutputStream = new ByteArrayOutputStream();

		GreaterAgeConsumer greaterAgeConsumer = new GreaterAgeConsumer(50, consumerOutputStream);

		this.launchForConsumer(greaterAgeConsumer);

		this.actualOutputData = new String(consumerOutputStream.toByteArray());
	}

	@When("^filtering messages by city of \"([^\"]*)\"$")
	public void filtering_messages_by_city_of(String arg1) throws Throwable {

		ByteArrayOutputStream consumerOutputStream = new ByteArrayOutputStream();

		CityMatchingConsumer cityMatchingConsumer = new CityMatchingConsumer("Coil", consumerOutputStream);

		this.launchForConsumer(cityMatchingConsumer);

		this.actualOutputData = new String(consumerOutputStream.toByteArray());
	}

	@When("^calculating average age$")
	public void calculating_average_age() throws Throwable {

		AverageAgeConsumer averageAgeConsumer = new AverageAgeConsumer();

		this.launchForConsumer(averageAgeConsumer);

		this.actualAverageAge = averageAgeConsumer.getResult();
	}

	@Then("^following filtered rows are expected$")
	public void following_filtered_rows_are_expected(DataTable table) throws Throwable {

		Set<String> expectedRows = this.splitByLines(this.tableToString(table));

		Set<String> actualRows = this.splitByLines(this.actualOutputData);

		Assert.assertEquals(expectedRows, actualRows);
	}

	@Then("^average age must be (\\d+\\.\\d+) and number of processed messages must be (\\d+)$")
	public void average_age_must_be_and_number_of_processed_messages_must_be(double expectedAverageAge, int expectedCount) throws Throwable {
		Assert.assertEquals(expectedAverageAge, this.actualAverageAge.averageAge, 1e-5);
		Assert.assertEquals(expectedCount, this.actualAverageAge.processedMessagesCount);
	}

	private void launchForConsumer(MessageConsumer consumer) throws InterruptedException {
		DeliveryManager deliveryManager =
				new DeliveryManager(
						PRODUCER_THREADS_NUM,
						CONSUMER_THREADS_NUM,
						MESSAGE_QUEUE_CAPACITY);

		deliveryManager.addConsumers(consumer);

		deliveryManager.forkDeliveryThread();

		deliveryManager.forkProducers(
				new InputStreamMessageProducer(
						new ByteArrayInputStream(
								this.inputData.getBytes())));

		deliveryManager.waitUntillAllProducersAreStopped();
		deliveryManager.waitUntilQueueIsEmpty();
		deliveryManager.waitUntilAllConsumersAreStopped();
	}

	private Set<String> splitByLines(String expectedOutput) {
		Set<String> expectedRows = new HashSet<String>();
		for (String row : expectedOutput.split(NEW_LINE)) {
			expectedRows.add(row);
		}
		return expectedRows;
	}

	private String tableToString(DataTable table) {
		StringBuilder sb = new StringBuilder();

		for (DataTableRow row : table.getGherkinRows()) {
			List<String> cells = row.getCells();

			for (String cell : cells) {
				sb.append(cell).append(DELIMITER);
			}
			sb.setLength(sb.length() - 1);

			sb.append(NEW_LINE);
		}

		String str = sb.toString();
		return str;
	}
}
