package com.notes.datetime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.time.Instant;
import java.util.Date;
import java.time.format.DateTimeFormatter;

public class Iso8601Example {

	public static void main(String[] args) {

		// Current Date
		LocalDate localDate = LocalDate.now();
		System.out.println("LocalDate	: " + localDate);																// LocalDate		: 2025-09-18
		
		// Current Time
		LocalTime localTime = LocalTime.now();
		System.out.println("LocalTime	: " + localTime);																// LocalTime		: 17:51:01.397524200
		
		// Current date and time in local time zone
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("\nLocalDateTime	: " + localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));				// LocalDateTime	: 2025-09-18T17:49:15.0294723
		System.out.println("LocalDateTime	: " + localDateTime);														// LocalDateTime	: 2025-09-18T17:49:15.029472300

		// Current date and time with zone info
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		System.out.println("\nZonedDateTime	: " + zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME));				// ZonedDateTime	: 2025-09-18T17:51:01.4015235+05:30[Asia/Calcutta]
		System.out.println("ZonedDateTime	: " + zonedDateTime);														// ZonedDateTime	: 2025-09-18T17:51:01.401523500+05:30[Asia/Calcutta]

		// Current time with offset (e.g. +05:30)
		OffsetDateTime offsetDateTime = OffsetDateTime.now();
		System.out.println("\nOffsetDateTime	: " + offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));	// OffsetDateTime	: 2025-09-18T17:51:01.4015235+05:30
		System.out.println("OffsetDateTime	: " + offsetDateTime);														// OffsetDateTime	: 2025-09-18T17:51:01.401523500+05:30

		// Instant (always in UTC, ends with 'Z')
		Instant instant = Instant.now();
		System.out.println("\nInstant		: " + instant.toString());													// Instant			: 2025-09-18T12:21:01.402523Z

		Date now = new Date();
		System.out.println("\njava.util.Date	: " + now);																// java.util.Date	: Thu Sep 18 17:51:01 IST 2025
	}
}

/*
LocalDate		: 2025-09-18
LocalTime		: 17:51:01.397524200

LocalDateTime	: 2025-09-18T17:51:01.3975242
LocalDateTime	: 2025-09-18T17:51:01.397524200

ZonedDateTime	: 2025-09-18T17:51:01.4015235+05:30[Asia/Calcutta]
ZonedDateTime	: 2025-09-18T17:51:01.401523500+05:30[Asia/Calcutta]

OffsetDateTime	: 2025-09-18T17:51:01.4015235+05:30
OffsetDateTime	: 2025-09-18T17:51:01.401523500+05:30

Instant			: 2025-09-18T12:21:01.402523Z

java.util.Date	: Thu Sep 18 17:51:01 IST 2025

*/