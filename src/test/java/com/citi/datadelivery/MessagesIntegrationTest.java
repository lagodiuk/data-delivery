package com.citi.datadelivery;

import java.util.List;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

public class MessagesIntegrationTest {

	@Given("^following rows$")
	public void following_rows(DataTable rows) throws Throwable {
		for (List<String> row : rows.raw()) {
			System.out.println(row);
		}
	}
}
