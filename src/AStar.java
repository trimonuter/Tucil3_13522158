
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar {
    public static Result SearchAStar(String start, String end, Map<String,List<String>> wordMap) {
        // Return true if the start and end words are the same
        if (start.equals(end)) {
            return new Result(true, 0, 0, 0, new Node(start, start, 0, end, "A*"));
        }
        
        // Track starting time
        long startTime = System.nanoTime();

        // Create a priority queue
        PriorityQueue<Node> queue = new PriorityQueue<>();

        // Create a minimum cost map
        Map<String, Integer> minCost = new HashMap<>();
        
        // Create node for starting word
        Node startNode = new Node(start, start, 0, end, "A*");
        
        // Add the start node to the queue
        queue.add(startNode);
    
        // Loop until queue is empty
        int count = 0;
        boolean found = false;
        Node result = null;
        while (!queue.isEmpty()) {
            // Get the node with the lowest cost
            Node current = queue.poll();

            // If found and current node cost is higher than result node cost
            if (found && (current.cost > result.cost)) {
                return new Result(true, System.nanoTime() - startTime, 0, count, result);
            }

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
                    // // If not found or found with lower cost
                    if (!found || (found && result.cost < current.cost)) {
                        // Create a new node
                        Node newNode = new Node(neighbor, current.path + " -> " + neighbor, current.cost + 1, end, "A*");
                        
                        // Set result node to new node
                        result = newNode;
                    }

                    // Set found to true
                    found = true;


                    // Track the ending time
                    // long endTime = System.nanoTime();
                    
                    // // Calculate the time taken
                    // long time = endTime - startTime;
                    
                    // // Calculate the memory used
                    // // int size = queue.size();
                    // // long memoryUsage = size * Node.BYTES;

                    // // Create a new node
                    // Node newNode = new Node(neighbor, current.path + " -> " + neighbor, current.cost + 1, end, "A*");
                    
                    // // Return the result
                    // return new Result(true, time, 0, count, newNode);
                }

                // Track the minimum cost
                minCost.put(neighbor, current.cost);

                // Create a new node
                Node newNode = new Node(neighbor, current.path + " -> " + neighbor, current.cost + 1, end, "A*");
                
                // Add the new node to the queue
                queue.add(newNode);
            }
        }

        // Calculate the time taken
        long time = System.nanoTime() - startTime;
        return new Result(found, time, 0, count, result);
    }
}