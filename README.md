# Huffman Coding Implementation

## Introduction

This program demonstrates Huffman coding, a compression algorithm that assigns variable-length codes to input characters based on their frequencies. The generated Huffman tree is used to encode and decode text efficiently.

## Usage

### Couldn't find data.txt, using sample data

In case the program couldn't find the specified `data.txt` file, it will use the provided sample data for demonstration purposes.

### Building Huffman tree...

The program reads the input data, constructs a Huffman tree, and displays the codes assigned to each character in the tree.

### Huffman codes:

The following is an example of the Huffman codes generated for the characters in the input data:

- A: 0
- B: 101
- C: 100
- D: 11

### Encoding sample text: BABAABA

A sample text ("BABAABA") is encoded using the generated Huffman tree. The resulting encoded text is displayed.

Encoded text: 101101100

### Decoding encoded text:

The program decodes the previously encoded text using the Huffman tree and displays the decoded result.

Decoded text: BABAABA

## How to Run

1. Make sure you have the necessary dependencies for compiling and running the program.
2. Compile the source code using a C compiler (e.g., gcc).
3. Run the executable, providing the required input file (data.txt) or let the program use the sample data.

```bash
gcc huffman.c -o huffman
./huffman
