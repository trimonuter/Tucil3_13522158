
public class Result {
    public boolean found;
    public long time;
    public long memory;
    public int count;
    public Node node;

    public Result(boolean found, long time, long memory, int count, Node node) {
        this.found = found;
        this.time = time;
        this.memory = memory;
        this.count = count;
        this.node = node;
    }
}