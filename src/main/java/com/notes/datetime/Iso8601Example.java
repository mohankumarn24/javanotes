package com.notes.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Iso8601Example {

	public static void main(String[] args) {

		// Current date and time in local time zone
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("LocalDateTime	: " + localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

		// Current date and time with zone info
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		System.out.println("ZonedDateTime	: " + zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

		// Current time with offset (e.g. +05:30)
		OffsetDateTime offsetDateTime = OffsetDateTime.now();
		System.out.println("OffsetDateTime	: " + offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

		// Instant (always in UTC, ends with 'Z')
		Instant instant = Instant.now();
		System.out.println("Instant			: " + instant.toString());

		Date now = new Date();
		System.out.println("java.util.Date	: " + now);
	}
}

/*
LocalDateTime	: 2025-09-07T11:43:04.1861596
ZonedDateTime	: 2025-09-07T11:43:04.1971602+05:30[Asia/Calcutta]
OffsetDateTime	: 2025-09-07T11:43:04.1971602+05:30
Instant			: 2025-09-07T06:13:04.197160200Z
java.util.Date	: Sun Sep 07 11:43:04 IST 2025
*/