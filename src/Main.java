
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
   public static void main(String[] args) {
        // Variables
        String filename = "oracle.txt";
        String start;
        String end;
        // String algorithm;

        // Get user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter starting word: ");
        start = scanner.nextLine().toLowerCase();

        System.out.print("Enter ending word: ");
        end = scanner.nextLine().toLowerCase();

        boolean correct = false;
        String algorithm = "";
        do {
            System.out.println();
            System.out.println("Enter which algorithm to use: ");
            System.out.println("1. UCS");
            System.out.println("2. GBFS");
            System.out.println("3. A*");
            System.out.println("4. GBFSWithBacktrack (Unoptimal)");

            System.out.println();
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Using UCS algorithm.");
                    algorithm = "UCS";
                    correct = true;
                    break;
                case "2":
                    System.out.println("Using GBFS algorithm.");
                    algorithm = "GBFS";
                    correct = true;
                    break;
                case "3":
                    System.out.println("Using A* algorithm.");
                    algorithm = "A*";
                    correct = true;
                    break;
                case "4":
                    System.out.println("Using GBFSWithBacktrack algorithm.");
                    algorithm = "GBFSWithBacktrack";
                    correct = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!correct);
        System.out.println();
        scanner.close();

        // Read file and store data to map
        Map<String, List<String>> wordMap = new HashMap<>();

        // Get map of word graph
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by spaces
                String[] parts = line.split(" ");

                // The first part is the key, and the rest are values
                String key = parts[0];
                List<String> values = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    values.add(parts[i]);
                }

                // Put key-value pair into the map
                wordMap.put(key, values);
            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }

        Result res;

        if (algorithm.equals("UCS")) {
            res = UCS.SearchUCS(start, end, wordMap);
        } else if (algorithm.equals("GBFS")) {
            res = GBFS.SearchGBFS(start, end, wordMap);
        } else if (algorithm.equals("GBFSWithBacktrack")) {
            res = GBFSWithBacktrack.SearchGBFSWithBacktrack(start, end, wordMap);
        } else  {
            res = AStar.SearchAStar(start, end, wordMap);
        }

        // Print the result
        System.out.println("Algorithm: " + algorithm);
        long time_ms = res.time / 1000000;
        if (res.found) {
            System.out.println("Time: " + time_ms + " ms");
            System.out.println("Path: " + res.node.path);
            System.out.println("Degrees: " + res.node.cost);
            System.out.println("Nodes visited: " + res.count);
        } else {
            System.out.println("Path not found.");
            System.out.println("Time: " + time_ms + " ms");
            System.out.println("Nodes visited: " + res.count);
        }
        System.out.println();
   } 
}
