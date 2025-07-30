import java.io.*;
import java.nio.file.Files;

public class HuffmanDecompressor {

    public void decompress(String inputFile, String outputFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFile))) {
            HuffmanNode root = (HuffmanNode) ois.readObject();
            int originalLength = ois.readInt();
            int compressedLength = ois.readInt();

            byte[] compressedBytes = new byte[compressedLength];
            ois.readFully(compressedBytes);

            StringBuilder bitString = new StringBuilder();
            for (int i = 0; i < compressedLength; i++) {
                byte b = compressedBytes[i];
                for (int j = 7; j >= 0; j--) {
                    bitString.append(((b >> j) & 1) == 1 ? '1' : '0');
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HuffmanNode current = root;
            int count = 0;

            for (int i = 0; i < bitString.length() && count < originalLength; i++) {
                current = bitString.charAt(i) == '0' ? current.left : current.right;
                if (current.isLeaf()) {
                    baos.write(current.data);
                    current = root;
                    count++;
                }
            }

            Files.write(new File(outputFile).toPath(), baos.toByteArray());
        }
    }
}
