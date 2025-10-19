package com.notes.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class FileOperationsLargeDemo {

    public static void main(String[] args) {

        // File path in project root
        Path filePath = Paths.get("example_large.txt");

        // 1️. Create file if it doesn't exist
        // Files.notExists() is safer than !Files.exists() for concurrency
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

        // 2️. Write large file (1 million lines)
        // Use BufferedWriter to write lines efficiently without loading everything in memory
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) { // Truncate ensures file is overwritten
            for (int i = 1; i <= 1_000_000; i++) {
                writer.write("Line " + i + ": This is a large file example.\n");
            }
            System.out.println("2. Large file written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3️. Append a line at the end (line 1000001)
        // APPEND mode ensures existing content is preserved
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write("Line 1000001: Appending a line at the end.\n");
            System.out.println("3. Line appended successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4️. Read first 10 lines lazily
        // Stream is memory-efficient: reads line by line, not the whole file
        System.out.println("\n4. Reading first 10 lines lazily:");
        try (Stream<String> linesStream = Files.lines(filePath, StandardCharsets.UTF_8)) {
            linesStream.limit(10).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 5️. Update and delete lines safely using line numbers
        // Map<LineNumber, NewContent> for updates
        Map<Integer, String> updates = new HashMap<>();
        updates.put(2, "Line 2: This line has been updated!"); // Update line 2
        updates.put(4, "Line 4: Another updated line!");       // Update line 4

        // Set of line numbers to delete
        Set<Integer> deletes = Set.of(3, 5); // Delete lines 3 and 5

        // Temporary file for safe write before atomic replace
        Path tempFile = filePath.resolveSibling("temp_safe.txt");

        try (Stream<String> linesStream = Files.lines(filePath, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            final int[] lineNumber = {1}; // Counter to track current line number

            linesStream.forEach(line -> {
                try {
                    int current = lineNumber[0];

                    // Delete lines first: skip writing these lines
                    if (deletes.contains(current)) {
                        // Comment: Line is deleted
                        lineNumber[0]++;
                        return;
                    }

                    // Update lines if current line is in updates map
                    if (updates.containsKey(current)) {
                        writer.write(updates.get(current) + "\n");
                        // Comment: Line has been updated
                    } else {
                        // Write original line
                        writer.write(line + "\n");
                    }

                    lineNumber[0]++; // Increment line number

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 6️. Atomically replace original file
        // Ensures the large file is replaced safely without corruption
        try {
            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            System.out.println("\n5. Multiple lines updated and deleted safely in large file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Expected final number of lines:
        // Original lines = 1,000,000
        // +1 appended line
        // -2 deleted lines (3 and 5)
        // => Total lines = 1,000,000 + 1 - 2 = 999,999
        System.out.println("\nExpected total lines after updates and deletes: 999,999");

        // 7️. Read first 10 lines of final file to verify changes
        System.out.println("\n6. Reading first 10 lines of final file:");
        try (Stream<String> linesStream = Files.lines(filePath, StandardCharsets.UTF_8)) {
            linesStream.limit(10).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 8️. Check if file exists and delete
        if (Files.exists(filePath)) {
            System.out.println("\n7. File exists: " + filePath.getFileName());
            try {
                Files.delete(filePath);
                System.out.println("8. File deleted successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}






/*
1. File APIs Overview:

| API                                             | Use Case                                                 | Notes                                                                                                  |
| ----------------------------------------------- | -------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| `Files.readString()` (Java 11)                  | Small to medium text files (<10 MB)                      | Reads entire file into a single String. Very simple, not memory-efficient for huge files.              |
| `Files.readAllLines()` (Java 7)                 | Small to medium text files (<50 MB)                      | Reads entire file into `List<String>`. Good for line-by-line processing in memory.                     |
| `Files.lines()` (Java 8+)                       | Medium to huge files (50 MB → multiple GBs)              | Returns lazy `Stream<String>`. Lines processed on-the-fly. Very memory-efficient.                      |
| `BufferedReader` / `BufferedWriter` (`java.io`) | Medium to large files                                    | Legacy API. Also supports line-by-line processing. Still widely used in older codebases.               |
| `RandomAccessFile` (`java.io`)                  | Special cases: updating part of file without reading all | Low-level, allows jumping to specific offsets. Rarely used in modern apps unless performance-critical. |

2. File Size Guidelines & Memory Considerations:

| File Size        | Recommended Approach                        | Reason / Notes                                                                                                                                           |
| ---------------- | ------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| < 1 MB           | `readString()` or `readAllLines()`          | Can load whole file into memory safely. Very simple API.                                                                                                 |
| 1 MB – 50 MB     | `readAllLines()` or `BufferedReader`        | Memory usage is manageable; line-by-line processing may be needed.                                                                                       |
| 50 MB – 500 MB   | `Files.lines()` (stream) + `BufferedWriter` | Don’t load all lines in memory; process lazily.                                                                                                          |
| 500 MB – few GBs | `Files.lines()` with filtering / temp file  | Update/delete requires **temporary file**, atomic replace. Avoid `readAllLines()`.                                                                       |
| > 1 GB           | Stream-based processing + chunked writes    | Only read/write line-by-line. Do **not** hold the whole file in memory. Consider memory-mapped files (`FileChannel`) if very high performance is needed. |

3. Operation Recommendations:

| Operation                      | Small File                                  | Large File                                         |
| ------------------------------ | ------------------------------------------- | -------------------------------------------------- |
| Read whole content             | `Files.readString()`                        | `Files.lines()`                                    |
| Read line by line              | `Files.readAllLines()`                      | `Files.lines()`                                    |
| Append                         | `Files.writeString(..., APPEND)`            | `BufferedWriter` with APPEND                       |
| Update / Delete specific lines | `readAllLines()` → modify list → write back | Stream lines → write to temp file → atomic replace |
| Very large updates (>GB)       | —                                           | **Process in chunks**, avoid reading entire file   |

4. Memory Estimation Rule:
	- Each character in Java String uses 2 bytes (UTF-16).
	- Approximate memory usage: memory (bytes) = numChars * 2
	- Example: 1 million lines × 100 chars/line → 100,000,000 chars → 200 MB in memory
	- Using readAllLines() would require at least ~200 MB heap, plus extra overhead.
	- For larger files, always use stream-based processing.
	
5. Practical Recommendations:	

| File Size     | Recommended API / Approach               | Notes                                                                                                            |
| ------------- | ---------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| < 10 MB       | `Files.readString()`                     | Simple, reads entire file as a single `String`. Ideal for small config files, templates.                         |
| 10 MB – 50 MB | `Files.readAllLines()`                   | Reads entire file into `List<String>`. Good for logs, CSVs, medium-sized files.                                  |
| > 50 MB       | `Files.lines()` + temporary file         | Stream lines lazily. Use temp file for update/delete operations. Good for big logs, datasets, millions of lines. |
| > 1 GB        | Stream + chunked writes or `FileChannel` | Avoid loading entire file in memory. Use lazy streaming and/or memory-mapped files for very large datasets.      |

*/