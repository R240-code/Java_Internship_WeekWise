package Assignments_Week3;
//Assignments_Week3/IO_Example.java

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class IO_Example {

    public static void main(String[] args) {

        String inputFile = "input.text";
        String outputFile = "output.text";

        Map<String, Integer> wordCount = new LinkedHashMap<>();

        // Read from input file (try working dir, then src/, then classpath resource)
        BufferedReader br = null;
        try {
            Path inputPath = Paths.get(inputFile);
            if (Files.exists(inputPath)) {
                br = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8);
            } else {
                Path alt = Paths.get("src", inputFile);
                if (Files.exists(alt)) {
                    br = Files.newBufferedReader(alt, StandardCharsets.UTF_8);
                } else {
                    InputStream is = IO_Example.class.getClassLoader().getResourceAsStream(inputFile);
                    if (is != null) {
                        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    } else {
                        System.out.println("Error: input file not found. Looked at: " + inputPath.toAbsolutePath() + " and " + alt.toAbsolutePath() + " and classpath.");
                        return;
                    }
                }
            }

            String line;
            while ((line = br.readLine()) != null) {
                // Convert to lowercase and remove punctuation
                line = line.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");

                String[] words = line.split("\\s+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
            return;
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException ignored) {}
            }
        }

        // Write result to output file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {

                bw.write(entry.getKey() + " : " + entry.getValue());
                bw.newLine();
            }

            System.out.println("Word frequency written successfully.");

        } catch (IOException e) {
            System.out.println("Error while writing file: " + e.getMessage());
        }
    }
}