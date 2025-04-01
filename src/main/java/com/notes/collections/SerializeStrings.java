package com.notes.collections;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializeStrings {
	
	public static void main(String[] args) throws IOException {

		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("Sachin");
		arrayList.add("Lara");
		arrayList.add("Sangakkara");

		// serialize and deserialize arraylist
		serializeArraylist(arrayList);
		ArrayList<String> deserializedArrayList = deserializeArraylist();

		// print arraylist data
		System.out.println(String.format("Serialized arraylist: %s", arrayList));
		System.out.println(String.format("Deserialized arraylist: %s", deserializedArrayList));
	}

	private static void serializeArraylist(ArrayList<String> arrayList) throws IOException {

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			// file created automatically at project root. Updated automatically as well
			fileOutputStream = new FileOutputStream("file");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(arrayList); // Write the ArrayList object to the file
		} catch (Exception e) {

		} finally {
			fileOutputStream.close();
			objectOutputStream.close();
		}
	}

	private static ArrayList<String> deserializeArraylist() throws IOException {

		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		ArrayList<String> arrayList = null;

		try {
			fileInputStream = new FileInputStream("file");
			objectInputStream = new ObjectInputStream(fileInputStream);
			// Read the ArrayList object from the file and cast it to ArrayList<String>
			arrayList = (ArrayList<String>) objectInputStream.readObject();
		} catch (Exception e) {

		} finally {
			objectInputStream.close();
			fileInputStream.close();
		}

		return arrayList;
	}
}
