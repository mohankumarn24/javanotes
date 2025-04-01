package com.notes.collections;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationObjects {

	public static class Player implements Serializable {

		private static final long serialVersionUID = 1L;

		int id;

		String firstName;

		String lastName;

		String address;

		public Player() {
			id = 1;
			firstName = "Ricky";
			lastName = "Ponting";
			address = "somewhere in Australia";
		}

		public Player(int id, String firstName, String lastName, String address) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
		}

		public String getPlayerData() {
			return String.format("\nId: %d \nFirst Name: %s \nLast Name: %s \nAddress: %s", 
					this.id, this.firstName, this.lastName, this.address);
		}
	}

	public static void main(String[] args) throws IOException {

		// serialize and deserialize objects
		Player player = new Player();
		serializeObjects(player);
		Player deserializedPlayer = deserializeObjects();

		// print object data
		System.out.println(String.format("Serialized object: %s\n", player.getPlayerData()));
		System.out.println(String.format("Deserialized object: %s\n", deserializedPlayer.getPlayerData()));
	}

	private static void serializeObjects(Player player) throws IOException {

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			// file created automatically at project root. Updated automatically as well
			fileOutputStream = new FileOutputStream("playerFile");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(player); // Write the Player object to the file
		} catch (Exception e) {

		} finally {
			fileOutputStream.close();
			objectOutputStream.close();
		}
	}

	private static Player deserializeObjects() throws IOException {

		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		Player player = null;

		try {
			fileInputStream = new FileInputStream("playerFile");
			objectInputStream = new ObjectInputStream(fileInputStream);
			// Read the Player object from the file and cast it to Player
			player = (Player) objectInputStream.readObject();
		} catch (Exception e) {

		} finally {
			objectInputStream.close();
			fileInputStream.close();
		}

		return player;
	}
}
