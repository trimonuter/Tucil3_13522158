
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GBFSWithBacktrack {
    public static Result SearchGBFSWithBacktrack(String start, String end, Map<String, List<String>> wordMap) {
        // Return true if the start and end words are the same
        if (start.equals(end)) {
            return new Result(true, 0, 0, 0, new Node(start, start, 0, end, "GBFSWithBacktrack"));
        }
        
        // Track starting time
        long startTime = System.nanoTime();

        // Create a priority queue
        PriorityQueue<Node> queue = new PriorityQueue<>();
        
        // Create a seen map
        Map<String, Boolean> seen = new HashMap<>();
        
        // Create node for starting word
        Node startNode = new Node(start, start, 0, end, "GBFSWithBacktrack");
        
        // Add the start node to the queue
        queue.add(startNode);
    
        // Loop until queue is empty
        int count = 0;
        while (!queue.isEmpty()) {
            // Get the node with the lowest cost
            Node current = queue.poll();

            // If current node is seen, return with not found to prevent an infinite loop
            // if (seen.containsKey(current.word)) {
            //     return new Result(false, System.nanoTime() - startTime, 0, count, null);
            // }

            // Get the neighbors of the current node
            List<String> neighbors = wordMap.get(current.word);
            
            // For each neighbor
            if (neighbors == null) {
                continue;
            }

            for (String neighbor : neighbors) {
                // Skip if node has already been seen
                if (seen.containsKey(neighbor)) {
                    continue;
                }
                
                // Increment the count
                count++;

                // Create a new node for the neighbor
                Node neighborNode = new Node(neighbor, current.path + " -> " + neighbor, current.cost + 1, end, "GBFSWithBacktrack");

                // If the neighbor is the end word
                if (neighbor.equals(end)) {
                    // Track the ending time
                    long endTime = System.nanoTime();
                    
                    // Calculate the time taken
                    long time = endTime - startTime;
                    
                    // Return the result
                    return new Result(true, time, 0, count, neighborNode);
                }

                // Mark the current node as seen
                seen.put(current.word, true);

                // Add the neighbor node to the queue
                queue.add(neighborNode);
            }
        }

        // Return false if the path is not found
        return new Result(false, System.nanoTime() - startTime, 0, count, null);
    }
}
