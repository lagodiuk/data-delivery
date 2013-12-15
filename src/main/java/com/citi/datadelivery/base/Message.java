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

	// Generated with Eclipse source code generator
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.address == null) ? 0 : this.address.hashCode());
		result = (prime * result) + ((this.age == null) ? 0 : this.age.hashCode());
		result = (prime * result) + ((this.city == null) ? 0 : this.city.hashCode());
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		result = (prime * result) + ((this.postalCode == null) ? 0 : this.postalCode.hashCode());
		return result;
	}

	// Generated with Eclipse source code generator
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Message other = (Message) obj;
		if (this.address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!this.address.equals(other.address)) {
			return false;
		}
		if (this.age == null) {
			if (other.age != null) {
				return false;
			}
		} else if (!this.age.equals(other.age)) {
			return false;
		}
		if (this.city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!this.city.equals(other.city)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.postalCode == null) {
			if (other.postalCode != null) {
				return false;
			}
		} else if (!this.postalCode.equals(other.postalCode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Message [id=" + this.id + ", name=" + this.name + ", age=" + this.age + ", address=" + this.address + ", city=" + this.city + ", postalCode=" + this.postalCode
				+ "]";
	}
}
