
public class Node implements Comparable<Node> {
    public String word;
    public String path;
    public int cost;
    public int heuristic;
    public boolean useCost;

    public int GetHammingDistance(String target) {
        int distance = 0;
        for (int i = 0; i < target.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    public Node(String word, String path, int cost, String GoalWord, String algorithm) {
        this.word = word;
        this.path = path;

        if (algorithm.equals("UCS")) {
            this.cost = cost;
            this.heuristic = 0;
            this.useCost = true;
        } else if (algorithm.equals("GBFS") || algorithm.equals("GBFSWithBacktrack")) {
            this.cost = cost;
            this.heuristic = GetHammingDistance(GoalWord);
            this.useCost = false;
        } else if (algorithm.equals("A*")) {
            this.cost = cost;
            this.heuristic = GetHammingDistance(GoalWord);
            this.useCost = true;
        }
    }

    public int GetPriority() {
        return this.cost + this.heuristic;
    }

    @Override
    public int compareTo(Node other) {
        int ThisPriority, OtherPriority;
        if (this.useCost) {
            ThisPriority = this.cost + this.heuristic;
            OtherPriority = other.cost + other.heuristic;
        } else {
            ThisPriority = this.heuristic;
            OtherPriority = other.heuristic;
        }

        return Integer.compare(ThisPriority, OtherPriority);
    }
}
