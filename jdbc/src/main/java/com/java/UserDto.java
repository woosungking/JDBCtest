package com.java;

import java.sql.Timestamp;
import java.util.Date;

public class UserDto {
	private final Long id;
	private final String name;
	private final Timestamp timestamp;

	public UserDto(Long id, String name, Timestamp timestamp) {
		this.id = id;
		this.name = name;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getDateTime() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "UserDto{" +
			"id=" + id +
			", name='" + name + '\'' +
			", timestamp=" + timestamp +
			'}';
	}
}
