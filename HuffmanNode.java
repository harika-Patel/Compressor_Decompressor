import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
    int frequency;
    byte data;
    HuffmanNode left, right;

    public HuffmanNode(byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
