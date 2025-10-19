package com.notes.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

// This code uses modern NIO (java.nio.file), not classic Java IO (java.io)
public class FileOperationsDemo2 {

    public static void main(String[] args) {
    	
        Path filePath = Paths.get("example2.txt"); // file in project root

        // 1️. Create file
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

        // 2️. Write whole file (overwrites if exists)
        String content = "Line 1: Hello, Java 11 File Operations!\n" +
                         "Line 2: This is the second line.\n" +
                         "Line 3: Third line here.\n";
        try {
            Files.writeString(filePath, content, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE,					// creates the file if it doesn’t exist
					StandardOpenOption.TRUNCATE_EXISTING,		// clears the file before writing
					StandardOpenOption.WRITE);					// writes to the file
            System.out.println("2. File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3️. Append a line
        String appendContent = "Line 4: Appending a fourth line.\n";
        try {
            Files.writeString(filePath, appendContent, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, 					// Create file if not exists
					StandardOpenOption.APPEND);					// StandardOpenOption.TRUNCATE_EXISTING -> overwrites existing content
            System.out.println("3. Line appended successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4️. Read file line by line
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            System.out.println("\n4. Reading file line by line:");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 5️. Update a line (e.g., update Line 2)
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            List<String> updatedLines = lines.stream()
                    .map(line -> line.startsWith("Line 2") ? "Line 2: This line has been updated!" : line)
                    .collect(Collectors.toList());
            Files.write(filePath, updatedLines, StandardCharsets.UTF_8, 
            		StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("\n5. Line 2 updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 6️. Delete a line (e.g., remove Line 3)
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            List<String> filteredLines = lines.stream()
                    .filter(line -> !line.startsWith("Line 3"))
                    .collect(Collectors.toList());
            Files.write(filePath, filteredLines, StandardCharsets.UTF_8, 
            		StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("\n6. Line 3 deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 7️. Read final content
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            System.out.println("\n7. Final file content:");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 8️. Check if file exists
        if (Files.exists(filePath)) {
            System.out.println("\n8. File exists: " + filePath.getFileName());
        } else {
            System.out.println("\n8. File does not exist.");
        }

        // 9️. Delete file
        try {
            Files.delete(filePath);
            System.out.println("\n9. File deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
