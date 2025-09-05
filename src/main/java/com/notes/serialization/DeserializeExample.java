package com.notes.serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializeExample {

	public static void main(String[] args) {

		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			fileInputStream = new FileInputStream("person.ser");
			objectInputStream = new ObjectInputStream(fileInputStream);

			Person person = (Person) objectInputStream.readObject();
			System.out.println("Object has been deserialized: " + person);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null)
					objectInputStream.close();
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * Output: 
 * ------
 * Object has been serialized: Person{name='Mohan', age=30, password='mySecretPassword'}
 * Object has been deserialized: Person{name='Mohan', age=30, password='null'}
 */

/*
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializeExample {

    public static void main(String[] args) {
    
        try (FileInputStream fileIn = new FileInputStream("person.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            Person person = (Person) in.readObject();
            System.out.println("Object has been deserialized: " + person);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
 */