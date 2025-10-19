package com.notes.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// This code uses modern NIO (java.nio.file), not classic Java IO (java.io)
public class FileOperationsDemo1 {

	public static void main(String[] args) {
		Path filePath = Paths.get("example1.txt");			// file created in project root path

		// 1️. Create a file
		try {
			if (Files.notExists(filePath)) {
				Files.createFile(filePath);
				System.out.println("1. File created: " + filePath.getFileName());
			} else {
				System.out.println("1. File already exists: " + filePath.getFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Files.writeString() and Files.readString() are Java 11 features, much cleaner than using FileWriter/BufferedReader
		// 2️. Write to a file (overwrites if exists) (Write whole file)
		String content = "Hello, Java 11 File Operations!\n";
		try {
			Files.writeString(filePath, content, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE,					// creates the file if it doesn’t exist
					StandardOpenOption.TRUNCATE_EXISTING,		// clears the file before writing
					StandardOpenOption.WRITE);					// writes to the file

			System.out.println("2. File written successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3️. Append to a file
		String moreContent = "Appending a second line.\n";
		try {
			Files.writeString(filePath, moreContent, StandardCharsets.UTF_8, 
					StandardOpenOption.CREATE, 					// Create file if not exists
					StandardOpenOption.APPEND);					// StandardOpenOption.TRUNCATE_EXISTING -> overwrites existing content
			System.out.println("3. File appended successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 4️. Read from a file (Read whole file)
		try {
			String fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
			System.out.println("\n4. File content:\n" + fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 5️. Check if file exists
		if (Files.exists(filePath)) {
			System.out.println("5. File exists: " + filePath.getFileName());
		} else {
			System.out.println("5. File does not exist.");
		}

		// 6️. Delete the file
		try {
			Files.delete(filePath);
			System.out.println("6. File deleted successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*
| Feature       | `readString`                               | `readAllLines`                                   |
| ------------- | ------------------------------------------ | ------------------------------------------------ |
| Returns       | `String`                                   | `List<String>`                                   |
| Ideal for     | Whole file as one string                   | Line-by-line processing                          |
| Memory        | Entire file in memory                      | Entire file in memory                            |
| Introduced    | Java 11                                    | Java 7                                           |
| Example usage | `String content = Files.readString(path);` | `List<String> lines = Files.readAllLines(path);` |
*/


/*
Making It Thread-Safe:

 a) Synchronize access in Java:
    - If multiple threads are working within the same JVM, you can use synchronized:
		 private static final Object fileLock = new Object();
		 
		 synchronized(fileLock) {
			 // write, update, or delete file operations
		 }
 
	- Pros: Simple, ensures one thread at a time
	- Cons: Only works for threads within the same JVM, not processes
 
 b) File Locking using java.nio.channels.FileLock
	- You can lock the file before writing/updating:
		 try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.WRITE);
			  FileLock lock = channel.lock()) {
			 // safe write/update/delete here
		 }
 
	- Pros: Works across JVMs/processes
	- Cons: Locks the whole file; slow for large files if many threads wait
 
 c) Use database or message queue for concurrent updates
	- For very large files with heavy concurrency, consider:
		-- Splitting data into chunks
		-- Using a DB (Postgres, Redis, etc.) for thread-safe updates
		-- Using a queue system (Kafka) for ordered updates
*/
