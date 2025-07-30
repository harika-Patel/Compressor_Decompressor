# Huffman File Compressor and Decompressor

A simple Java-based implementation of **Huffman Coding**, a lossless data compression algorithm. This project enables you to compress and decompress files using both a **command-line interface** and a **user-friendly GUI built with Swing**.

---

## 📦 Features

-  Compress any file using Huffman encoding
-  Decompress files encoded using Huffman tree
-  Lightweight and independent of file type
-  Java Swing-based **Graphical User Interface** (GUI)
-  Option to select files via dialog or enter file paths manually
-  Stores compressed data and encoding tree for accurate restoration

---

## How Huffman Coding Works

Huffman coding builds a binary tree based on the frequency of each byte in a file:
- Frequent bytes get shorter binary codes
- Infrequent bytes get longer codes
This minimizes the total size of the encoded data.

---

##  Project Structure
HuffmanCompressor.java      // Compression logic using Huffman encoding  
HuffmanDecompressor.java    // Decompression logic using stored Huffman tree  
HuffmanNode.java            // Serializable tree node used in encoding/decoding  
HuffmanUI.java           // Swing-based GUI for file selection and actions  


##  Run Options

### Option 1: ✅ GUI Application (Swing-based)

#### Compile and Run

```bash
javac HuffmanNode.java HuffmanCompressor.java HuffmanDecompressor.java HuffmanAppUI.java
java HuffmanAppUI

