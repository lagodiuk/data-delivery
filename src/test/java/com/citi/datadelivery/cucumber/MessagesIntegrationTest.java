package com.citi.datadelivery.cucumber;

import gherkin.formatter.model.DataTableRow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import org.junit.Assert;

import com.citi.datadelivery.GreaterAgeConsumer;
import com.citi.datadelivery.InputStreamMessageProducer;
import com.citi.datadelivery.base.DeliveryManager;
import com.citi.datadelivery.base.MessageQueue;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MessagesIntegrationTest {

	private static final String NEW_LINE = String.format("%n");

	private static final String DELIMITER = String.format("|");

	private String inputData;

	private String actualOutputData;

	@Given("^following input rows$")
	public void following_rows(DataTable table) throws Throwable {
		this.inputData = this.tableToString(table);
	}

	@When("^filtering messages by age of (\\d+)$")
	public void filtering_messages_by_age_of(int age) throws Throwable {
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager =
				new DeliveryManager(
						messageQueue,
						Executors.newFixedThreadPool(5),
						Executors.newFixedThreadPool(5));

		ByteArrayOutputStream consumerOutputStream = new ByteArrayOutputStream();
		deliveryManager.addConsumers(
				new GreaterAgeConsumer(50, consumerOutputStream));

		deliveryManager.forkProducers(
				new InputStreamMessageProducer(
						new ByteArrayInputStream(
								this.inputData.getBytes())));

		deliveryManager.forkDeliveryThread();

		deliveryManager.waitUntillAllProducersAreStopped();
		deliveryManager.waitUntilQueueIsEmpty();
		deliveryManager.waitUntilAllConsumersAreStopped();

		this.actualOutputData = new String(consumerOutputStream.toByteArray());
	}

	@Then("^following filtered rows are expected$")
	public void following_filtered_rows_are_expected(DataTable table) throws Throwable {
		String expectedOutputData = this.tableToString(table);
		Set<String> expectedRows = this.splitByLines(expectedOutputData);

		Set<String> actualRows = this.splitByLines(this.actualOutputData);

		Assert.assertEquals(expectedRows, actualRows);
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
