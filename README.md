# Huffman File Compressor and Decompressor

A simple Java-based implementation of **Huffman Coding**, a lossless data compression algorithm. This project enables you to compress and decompress files using both a **command-line interface** and a **user-friendly GUI built with Swing**.

---

## Features

-  Compress any file using Huffman encoding
-  Decompress files encoded using Huffman tree
-  Lightweight and independent of file type
-  Java Swing-based **Graphical User Interface** (GUI)
-  Option to select files via dialog or enter file paths manually
-  Stores compressed data and encoding tree for accurate restoration

---

## User Interface
<img width="762" height="570" alt="Screenshot 2025-07-30 233757" src="https://github.com/user-attachments/assets/4e43e281-669a-492d-988d-8e1f506a48bc" />
<img width="955" height="666" alt="Screenshot 2025-07-30 233945" src="https://github.com/user-attachments/assets/6968c650-cebf-4ce9-ae1a-bebf7cde2326" />
<img width="473" height="365" alt="Screenshot 2025-07-30 234206" src="https://github.com/user-attachments/assets/ab3310f9-ba69-469d-87af-181b6d70daaf" />
<img width="760" height="578" alt="Screenshot 2025-07-30 234451" src="https://github.com/user-attachments/assets/c6637210-9779-408d-a0e9-1815d6e51219" />

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

