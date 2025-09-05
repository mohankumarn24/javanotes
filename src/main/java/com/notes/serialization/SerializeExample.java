package com.notes.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeExample {

	public static void main(String[] args) {

		Person person = new Person("Mohan", 30, "mySecretPassword");

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream("person.ser");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(person);
			System.out.println("Object has been serialized: " + person);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectOutputStream != null)
					objectOutputStream.close();
				if (fileOutputStream != null)
					fileOutputStream.close();
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeExample {

    public static void main(String[] args) {
    
        Person person = new Person("Mohan", 30);

        try (FileOutputStream fileOut = new FileOutputStream("person.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(person);
            System.out.println("Object has been serialized: " + person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/