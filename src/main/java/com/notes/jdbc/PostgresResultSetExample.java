package com.notes.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// https://chatgpt.com/share/68bbda1f-601c-8004-8dca-b8a3134cfbc4
public class PostgresResultSetExample {

	private static final String URL = "jdbc:postgresql://localhost:5432/mydb?currentSchema=jdbc";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Password1";

	public static void main(String[] args) {

    	// delete all users
    	System.out.println("a. Deleting all users");
    	deleteAllUsers();
    	
        // Insert Users
    	System.out.println("\nb. Creating two users");
        insertUser(1, "Mohan", "mohan@example.com");
        insertUser(2, "Ravi", "ravi@example.com");

        // Fetch single user
        System.out.println("\nc. Reading user with id=1");
        User user = getUserById(1);
        if (user != null) {
            System.out.println("Fetched by ID: " + user);
        } else {
            System.out.println("User not found.");
        }

        // Read Users
        System.out.println("\nd. Reading all users");
        getAllUsers().forEach(System.out::println);

        // Update User Email
        System.out.println("\ne. Updating user with id=1");
        updateUserEmail(1, "mohan123@example.com");

        // Delete User
        System.out.println("\nf. Deleting user withid=2");
        deleteUser(2);

        // Read Users again
        System.out.println("\ng. Reading all users again");
        System.out.println("Users after update and delete:");
        getAllUsers().forEach(System.out::println);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

    // DELETE
    public static void deleteAllUsers() {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM users";
            stmt = conn.prepareStatement(sql);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " user(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    // CREATE
    public static void insertUser(int id, String name, String email) {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO users(id, name, email) VALUES(?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " user(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // READ by ID
    public static User getUserById(int id) {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return user;
    }

    // READ
    public static List<User> getAllUsers() {
    	
        List<User> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM users";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return users;
    }
    
    // UPDATE
    public static void updateUserEmail(int id, String newEmail) {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "UPDATE users SET email=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " user(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // DELETE
    public static void deleteUser(int id) {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM users WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " user(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}

/* OUTPUT:
1 user(s) inserted.
1 user(s) inserted.

Fetched by ID: User{id=1, name='Mohan', email='mohan@example.com'}

All users:
User{id=1, name='Mohan', email='mohan@example.com'}
User{id=2, name='Ravi', email='ravi@example.com'}
1 user(s) updated.
1 user(s) deleted.

Users after update and delete:
User{id=1, name='Mohan', email='mohan123@example.com'}
*/

/*
 * How it works:
 * - Connection: Uses DriverManager.getConnection() to connect to PostgreSQL.
 * - CRUD operations:
 * 		INSERT → PreparedStatement
 * 		SELECT → Statement + ResultSet
 * 		UPDATE → PreparedStatement
 * 		DELETE → PreparedStatement
 * - Try-with-resources ensures connections/statements are closed automatically.
*/

/*
ResultSet:
 - Connected: ✅ Must keep DB connection open
 - Disconnected: ❌ Cannot work without connection
 - Features: Simple, fast, can scroll/update (if created scrollable)
 - Usage: Quick DB reads for small/medium datasets
 - Analogy: Live phone call with DB
*/

/*
 * STEPS:
 * - Create db: myjdbcdb
 * - Create schema: my_schema
 * - Create table users
 * - Download postgresql-42.7.7.jar jar file
 * - Right click Project > Java Build Path > Libraries > Classpath > Add External Jar > Add postgresql-42.7.7.jar 
 * /


/* SQL Script
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);
*/

/* IGNORE
import java.sql.*;

public class JdbcCrudExample {

    private static final String URL = "jdbc:postgresql://localhost:5432/testdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to PostgreSQL");

            // CREATE
            createUser(conn, "Mohan", "mohan@example.com");

            // READ
            readUsers(conn);

            // UPDATE
            updateUserEmail(conn, 1, "mohan123@example.com");

            // DELETE
            deleteUser(conn, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create
    private static void createUser(Connection conn, String name, String email) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            System.out.println("Inserted " + rows + " user(s)");
        }
    }

    // Read
    private static void readUsers(Connection conn) throws SQLException {
        String sql = "SELECT id, name, email FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Email: " + rs.getString("email")
                );
            }
        }
    }

    // Update
    private static void updateUserEmail(Connection conn, int id, String newEmail) throws SQLException {
        String sql = "UPDATE users SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            System.out.println("Updated " + rows + " user(s)");
        }
    }

    // Delete
    private static void deleteUser(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println("Deleted " + rows + " user(s)");
        }
    }
}
*/