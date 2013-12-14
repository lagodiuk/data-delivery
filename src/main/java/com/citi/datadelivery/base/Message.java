package com.citi.datadelivery.base;

public final class Message {

	private final String id;

	private final String name;

	private final Integer age;

	private final String address;

	private final String city;

	private final String postalCode;

	public Message(String id, String name, Integer age, String address, String city, String postalCode) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getAge() {
		return this.age;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	@Override
	public String toString() {
		return "Message [id=" + this.id + ", name=" + this.name + ", age=" + this.age + ", address=" + this.address + ", city=" + this.city + ", postalCode=" + this.postalCode
				+ "]";
	}
}
