
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GBFS {
    public static Result SearchGBFS(String start, String end, Map<String, List<String>> wordMap) {
        // Return true if the start and end words are the same
        if (start.equals(end)) {
            return new Result(true, 0, 0, 0, new Node(start, start, 0, end, "GBFS"));
        }
        
        // Track starting time
        long startTime = System.nanoTime();

        // Create a seen map
        Map<String, Boolean> seen = new HashMap<>();
    
        // Loop until end goal is found
        int count = 0;
        Node node = new Node(start, start, 0, end, "GBFS");
        
        while (true) {
            // Get the neighbors of the current node
            List<String> neighbors = wordMap.get(node.word);
            
            // If there are no neighbors
            if (neighbors == null) {
                return new Result(false, System.nanoTime() - startTime, 0, count, null);
            }

            // Find the neighbor with the lowest heuristic
            int minHeuristic = Integer.MAX_VALUE;
            Node nextNode = null;
            for (String neighbor : neighbors) {                
                Node neighborNode = new Node(neighbor, node.path + " -> " + neighbor, node.cost + 1, end, "GBFS");

                if (neighborNode.heuristic < minHeuristic) {
                    minHeuristic = neighborNode.heuristic;
                    nextNode = neighborNode;
                }
            }

            // If next node has already been seen
            if (seen.containsKey(nextNode.word)) {
                return new Result(false, System.nanoTime() - startTime, 0, count, null);
            }

            // Increment the count
            count++;
 
            // If the neighbor is the end word
            if (nextNode.word.equals(end)) {
                // Track the ending time
                long endTime = System.nanoTime();
                
                // Calculate the time taken
                long time = endTime - startTime;
                
                // Return the result
                return new Result(true, time, 0, count, nextNode);
            }
        
            // Mark the current node as seen
            seen.put(nextNode.word, true);
            
            // Move to the next node
            node = nextNode;
        }
    }
}
