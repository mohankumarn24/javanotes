package com.notes.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

// https://chatgpt.com/share/68bbda1f-601c-8004-8dca-b8a3134cfbc4
public class PostgresCachedRowSetExample {

    private static final String URL = "jdbc:postgresql://localhost:5432/mydb?currentSchema=jdbc";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Password1";

    public static void main(String[] args) {

        // Insert Users
        insertUser("Mohan", "mohan@example.com");
        insertUser("Ravi", "ravi@example.com");

        // Fetch single user
        System.out.println();
        User user = getUserById(1);
        if (user != null) {
            System.out.println("Fetched by ID: " + user);
        } else {
            System.out.println("User not found.");
        }

        // Read Users
        System.out.println();
        System.out.println("All users:");
        getAllUsers().forEach(System.out::println);

        // Update User Email (via CachedRowSet)
        updateUserEmail(1, "mohan123@example.com");

        // Delete User (still using PreparedStatement for simplicity)
        deleteUser(2);

        // Read Users again
        System.out.println();
        System.out.println("Users after update and delete:");
        getAllUsers().forEach(System.out::println);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE
    public static void insertUser(String name, String email) {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO users(name, email) VALUES(?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " user(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }}
            if (conn != null) {try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }}
        }
    }

    // READ by ID (CachedRowSet)
    public static User getUserById(int id) {
    	
        Connection conn = null;
        CachedRowSet rowSet = null; // public interface CachedRowSet extends RowSet, Joinable {...}
        User user = null;
        try {
            conn = getConnection();
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.setCommand("SELECT * FROM users WHERE id = ?");
            rowSet.setInt(1, id);
            rowSet.execute(conn);

            if (rowSet.next()) {
                user = new User(rowSet.getInt("id"), rowSet.getString("name"), rowSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowSet != null) {try { rowSet.close(); } catch (SQLException e) { e.printStackTrace(); }}
            if (conn != null) {try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return user;
    }

    // READ all (CachedRowSet)
    public static List<User> getAllUsers() {
    	
        Connection conn = null;
        CachedRowSet rowSet = null; // public interface CachedRowSet extends RowSet, Joinable {...}
        List<User> users = new ArrayList<>();
        try {
            conn = getConnection();
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.setCommand("SELECT * FROM users");
            rowSet.execute(conn); // now data is in memory

            while (rowSet.next()) {
                users.add(new User(rowSet.getInt("id"), rowSet.getString("name"), rowSet.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowSet != null) {try { rowSet.close(); } catch (SQLException e) { e.printStackTrace(); }}
            if (conn != null) {try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }}
        }
        return users;
    }

    // UPDATE (CachedRowSet)
    public static void updateUserEmail(int id, String newEmail) {
    	
        Connection conn = null;
        CachedRowSet rowSet = null; // public interface CachedRowSet extends RowSet, Joinable {...}
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // ✅ Required for acceptChanges(). otherwise gives "org.postgresql.util.PSQLException: Cannot commit when autoCommit is enabled." error
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.setCommand("SELECT * FROM users WHERE id = ?");
            rowSet.setInt(1, id);
            rowSet.execute(conn);

            if (rowSet.next()) {
                rowSet.updateString("email", newEmail);
                rowSet.updateRow();
                rowSet.acceptChanges(conn);
                System.out.println("User email updated via CachedRowSet.");
            } else {
                System.out.println("User not found for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rowSet != null) {try { rowSet.close(); } catch (SQLException e) { e.printStackTrace(); }}
            if (conn != null) {try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // DELETE
    public static void deleteUser(int id) {
    	
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("DELETE FROM users WHERE id=?");
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " user(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }}
            if (conn != null) {try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }}
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
User email updated via CachedRowSet.
1 user(s) deleted.

Users after update and delete:
User{id=1, name='Mohan', email='mohan123@example.com'}

*/

/*
CachedRowSet:
 - Connected: ❌ Works disconnected after fetching data
 - Disconnected: ✅ Can work offline
 - Features: Serializable, scrollable, updatable, cache in memory, sync back to DB later
 - Usage: Web apps, offline apps, network transfer, caching, reduce DB load
 - Analogy: Download a copy of data → use anywhere → sync later if needed
*/
