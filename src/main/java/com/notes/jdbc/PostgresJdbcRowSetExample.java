package com.notes.jdbc;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresJdbcRowSetExample {

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


    // CLEAN UP
    public static void deleteAllUsers() {
    	
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            // Select all rows
            rowSet.setCommand("SELECT * FROM users");
            rowSet.execute();

            // Iterate and delete each row
            int count = 0;
            while (rowSet.next()) {
                rowSet.deleteRow();
                count++;
            }

            System.out.println(count + " user(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    // CREATE
    public static void insertUser(int id, String name, String email) {
    	
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            // Select an empty result set but with correct metadata
            rowSet.setCommand("SELECT id, name, email FROM users WHERE 1=0");
            rowSet.execute();

            rowSet.moveToInsertRow();				// set column values for a new row.
            rowSet.updateInt("id", id);
            rowSet.updateString("name", name);
            rowSet.updateString("email", email);
            rowSet.insertRow();						// Equivalent to INSERT INTO users (id, name, email) VALUES (?, ?, ?) in plain JDBC.
            rowSet.moveToCurrentRow();				// move back to the RowSet’s normal cursor. Required to continue normal RowSet operations (like iterating rows if needed)

            System.out.println("1 user inserted with id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ by ID
    public static User getUserById(int id) {
    	
        User user = null;
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            rowSet.setCommand("SELECT * FROM users WHERE id = ?");
            rowSet.setInt(1, id);
            rowSet.execute();

            if (rowSet.next()) {
                user = new User(rowSet.getInt("id"), rowSet.getString("name"), rowSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // READ all
    public static List<User> getAllUsers() {
    	
        List<User> users = new ArrayList<>();
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            rowSet.setCommand("SELECT * FROM users");
            rowSet.execute();

            while (rowSet.next()) {
                users.add(new User(rowSet.getInt("id"), rowSet.getString("name"), rowSet.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // UPDATE
    public static void updateUserEmail(int id, String newEmail) {
    	
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            rowSet.setCommand("SELECT * FROM users WHERE id=?");
            rowSet.setInt(1, id);
            rowSet.execute();

            if (rowSet.next()) {
                rowSet.updateString("email", newEmail);
                rowSet.updateRow();
                System.out.println("1 user updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteUser(int id) {
    	
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);

            rowSet.setCommand("SELECT * FROM users WHERE id=?");
            rowSet.setInt(1, id);
            rowSet.execute();

            if (rowSet.next()) {
                rowSet.deleteRow();
                System.out.println("1 user deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/**
CREATE TABLE users (
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

*/

/* OUTPUT:
a. Deleting all users
1 user(s) deleted.

b. Creating two users
1 user inserted with id=1
1 user inserted with id=2

c. Reading user with id=1
Fetched by ID: User{id=1, name='Mohan', email='mohan@example.com'}

d. Reading all users
User{id=1, name='Mohan', email='mohan@example.com'}
User{id=2, name='Ravi', email='ravi@example.com'}

e. Updating user with id=1
1 user updated.

f. Deleting user withid=2
1 user deleted.

g. Reading all users again
Users after update and delete:
User{id=1, name='Mohan', email='mohan123@example.com'}


*/

/*
JdbcRowSet
 - Connected: ✅ Must keep DB connection open
 - Disconnected: ❌
 - Features: JavaBeans-compliant, scrollable, updatable, GUI-friendly
 - Usage: Connected applications, bind to Swing components
 - Analogy: Live call with DB + nicer interface + GUI-ready
*/