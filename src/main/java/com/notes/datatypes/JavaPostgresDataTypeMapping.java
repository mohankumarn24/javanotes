package com.notes.datatypes;

import java.math.BigInteger;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Java -> PostgreSQL Datatype Mapping with JPA Annotations
 */
@Entity
@Table(name = "datatype_mapping")
public class JavaPostgresDataTypeMapping {

    // ===============================
    // Primary Key
    // ===============================
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    // PostgreSQL: UUID

    // ===============================
    // Primitive Types
    // ===============================
    @Column(name = "active", nullable = false)
    private boolean active;
    // PostgreSQL: BOOLEAN

    @Column(name = "grade", length = 1)
    private char grade;
    // PostgreSQL: CHAR(1)

    @Column(name = "byte_value")
    private byte byteValue;
    // PostgreSQL: SMALLINT (no 1-byte int in PostgreSQL)

    @Column(name = "short_value")
    private short shortValue;
    // PostgreSQL: SMALLINT

    @Column(name = "age")
    private int age;
    // PostgreSQL: INTEGER

    @Column(name = "salary")
    private long salary;
    // PostgreSQL: BIGINT

    @Column(name = "rating")
    private float rating;
    // PostgreSQL: REAL

    @Column(name = "percentage")
    private double percentage;
    // PostgreSQL: DOUBLE PRECISION

    // ===============================
    // Big / Exact Precision Types
    // ===============================
    @Column(name = "large_transaction_id", precision = 38, scale = 0)
    private BigInteger largeTransactionId;
    // PostgreSQL: NUMERIC(38,0)

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;
    // PostgreSQL: NUMERIC(12,2)

    // ===============================
    // Common Object Types
    // ===============================
    @Column(name = "name", length = 255)
    private String name;
    // PostgreSQL: VARCHAR(255)

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    // PostgreSQL: DATE

    @Column(name = "login_time")
    private LocalTime loginTime;
    // PostgreSQL: TIME

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    // PostgreSQL: TIMESTAMP

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    // PostgreSQL: TIMESTAMP WITH TIME ZONE
}

/*
CREATE TABLE datatype_mapping (
    id UUID NOT NULL,                      -- Java: UUID 			-> 	PostgreSQL: UUID (Primary Key)

    active BOOLEAN NOT NULL,               -- Java: boolean 		-> 	BOOLEAN
    grade CHAR(1),                         -- Java: char 			-> 	CHAR(1)

    byte_value SMALLINT,                   -- Java: byte 			-> 	SMALLINT (no 1-byte integer in PG)
    short_value SMALLINT,                  -- Java: short 			-> 	SMALLINT
    age INTEGER,                           -- Java: int 			-> 	INTEGER
    salary BIGINT,                         -- Java: long 			-> 	BIGINT
    
    rating REAL,                           -- Java: float 			-> 	REAL (approximate precision)
    percentage DOUBLE PRECISION,           -- Java: double 			-> 	DOUBLE PRECISION (approximate)

    large_transaction_id NUMERIC(38, 0),   -- Java: BigInteger 		-> 	NUMERIC (arbitrary precision)
    amount NUMERIC(12, 2),                 -- Java: BigDecimal 		-> 	NUMERIC(p,s) (exact precision)

    name VARCHAR(255),                     -- Java: String 			-> 	VARCHAR(255)
    date_of_birth DATE,                    -- Java: LocalDate 		-> 	DATE
    login_time TIME,                       -- Java: LocalTime 		-> 	TIME
    created_at TIMESTAMP,                  -- Java: LocalDateTime 	-> 	TIMESTAMP (without time zone)
    updated_at TIMESTAMPTZ,                -- Java: OffsetDateTime 	-> 	TIMESTAMP WITH TIME ZONE

    CONSTRAINT pk_datatype_mapping PRIMARY KEY (id) -- Primary key constraint
);

*/