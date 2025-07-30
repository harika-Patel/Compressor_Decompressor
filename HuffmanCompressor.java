import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class HuffmanCompressor {

    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private HuffmanNode root;

    public void compress(String inputFile, String outputFile) throws IOException {
        byte[] inputBytes = readFile(inputFile);
        Map<Byte, Integer> freqMap = buildFrequencyMap(inputBytes);
        root = buildHuffmanTree(freqMap);
        buildCodes(root, "");

        StringBuilder encodedData = new StringBuilder();
        for (byte b : inputBytes) {
            encodedData.append(huffmanCodes.get(b));
        }

        byte[] compressedData = getEncodedBytes(encodedData.toString());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            oos.writeObject(root);  // Save the Huffman tree
            oos.writeInt(inputBytes.length);  // Original file length
            oos.writeInt(compressedData.length);  // Compressed length
            oos.write(compressedData);
        }
    }

    private byte[] readFile(String filePath) throws IOException {
        return Files.readAllBytes(new File(filePath).toPath());
    }

    private Map<Byte, Integer> buildFrequencyMap(byte[] bytes) {
        Map<Byte, Integer> freqMap = new HashMap<>();
        for (byte b : bytes) {
            freqMap.put(b, freqMap.getOrDefault(b, 0) + 1);
        }
        return freqMap;
    }

    private HuffmanNode buildHuffmanTree(Map<Byte, Integer> freqMap) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for (Map.Entry<Byte, Integer> entry : freqMap.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode merged = new HuffmanNode((byte) 0, left.frequency + right.frequency);
            merged.left = left;
            merged.right = right;
            queue.add(merged);
        }

        return queue.poll();
    }

    private void buildCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.data, code);
        }
        buildCodes(node.left, code + '0');
        buildCodes(node.right, code + '1');
    }

    private byte[] getEncodedBytes(String bitString) {
        int len = (bitString.length() + 7) / 8;
        byte[] result = new byte[len];
        for (int i = 0; i < bitString.length(); i++) {
            int byteIndex = i / 8;
            result[byteIndex] <<= 1;
            if (bitString.charAt(i) == '1') {
                result[byteIndex] |= 1;
            }
        }
        int padding = 8 - (bitString.length() % 8);
        if (padding < 8) {
            result[len - 1] <<= padding;
        }
        return result;
    }
}
