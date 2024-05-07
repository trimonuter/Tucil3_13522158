
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class UCS {
    public static Result SearchUCS(String start, String end, Map<String, List<String>> wordMap) {
        // Return true if the start and end words are the same
        if (start.equals(end)) {
            return new Result(true, 0, 0, 0, new Node(start, start, 0, end, "UCS"));
        }
        
        // Track starting time
        long startTime = System.nanoTime();

        // Create a priority queue
        PriorityQueue<Node> queue = new PriorityQueue<>();
        
        // Create a minimum cost map
        Map<String, Integer> minCost = new HashMap<>();
        
        // Create node for starting word
        Node startNode = new Node(start, start, 0, end, "UCS");
        
        // Add the start node to the queue
        queue.add(startNode);
    
        // Loop until queue is empty
        int count = 0;
        while (!queue.isEmpty()) {
            // Get the node with the lowest cost
            Node current = queue.poll();

            // Get the neighbors of the current node
            List<String> neighbors = wordMap.get(current.word);
            
            // For each neighbor
            if (neighbors == null) {
                continue;
            }

            for (String neighbor : neighbors) {
                // Skip if the neighbor has already been visited with a lower cost
                if (minCost.containsKey(neighbor)) {
                    if (current.cost >= minCost.get(neighbor)) {
                        continue;
                    }
                }

                // Increment the count
                count++;

                // If the neighbor is the end word
                if (neighbor.equals(end)) {
                    // Track the ending time
                    long endTime = System.nanoTime();
                    
                    // Calculate the time taken
                    long time = endTime - startTime;

                    // Create a new node
                    Node newNode = new Node(neighbor, current.path + " -> " + neighbor, current.cost + 1, end, "UCS");
                    
                    // Return the result
                    return new Result(true, time, 0, count, newNode);
                }
                
                // Track the minimum cost
                minCost.put(neighbor, current.cost);

                // Create a new node
                Node newNode = new Node(neighbor, current.path + " -> " + neighbor, current.cost + 1, end, "UCS");
                
                // Add the new node to the queue
                queue.add(newNode);
            }
        }

        // Calculate the time taken
        long time = System.nanoTime() - startTime;
        return new Result(false, time, 0, count, null);
    }
}
