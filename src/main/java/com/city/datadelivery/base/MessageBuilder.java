package com.city.datadelivery.base;

public class MessageBuilder {

	private String id;

	private String name;

	private Integer age;

	private String address;

	private String city;

	private String postalCode;

	public MessageBuilder(String id) {
		this.id = id;
	}

	public MessageBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public MessageBuilder withAge(Integer age) {
		this.age = age;
		return this;
	}

	public MessageBuilder withAge(String ageStr) {
		if ((ageStr != null) && (ageStr.matches("\\d+"))) {
			this.age = Integer.parseInt(ageStr);
		}
		return this;
	}

	public MessageBuilder withAddress(String address) {
		this.address = address;
		return this;
	}

	public MessageBuilder withCity(String city) {
		this.city = city;
		return this;
	}

	public MessageBuilder withPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public Message createMessage() {
		Message message = new Message(this.id, this.name, this.age, this.address, this.city, this.postalCode);
		return message;
	}
}
