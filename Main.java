import java.io.*;
import java.util.*;

class Huffman {

    static class Node {
        char c;
        int freq;
        Node left, right;

        Node(char c, int freq) {
            this.c = c;
            this.freq = freq;
        }
    }

    static class FreqComparator implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.freq - b.freq;
        }
    }

    static void printCodes(Node root, String code, Map<Character, String> codes) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            codes.put(root.c, code);
            return;
        }

        if (root.left != null) {
            printCodes(root.left, code + "0", codes);
        }

        if (root.right != null) {
            printCodes(root.right, code + "1", codes);
        }
    }

    static Node buildTree(char[] charSet, int[] freq) {
        int n = charSet.length;

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(charSet[i], freq[i]);
        }

        Arrays.sort(nodes, new FreqComparator());

        while (nodes.length > 1) {
            Node x = nodes[0];
            Node y = nodes[1];

            Node f = new Node('\0', x.freq + y.freq);
            f.left = x;
            f.right = y;

            nodes = Arrays.copyOfRange(nodes, 2, nodes.length);

            int i = 0;
            for (; i < nodes.length; i++) {
                if (nodes[i].freq > f.freq)
                    break;
            }
            nodes = insert(nodes, i, f);
        }

        return nodes[0];
    }

    static Node[] insert(Node[] nodes, int i, Node x) {
        int n = nodes.length;
        Node[] temp = new Node[n + 1];

        for (int j = 0; j < n + 1; j++) {
            if (j < i)
                temp[j] = nodes[j];
            else if (j == i)
                temp[j] = x;
            else
                temp[j] = nodes[j - 1];
        }

        return temp;
    }

    static String getCode(char c, Map<Character, String> codes) {
        return codes.get(c);
    }

    static void decodeFile(String encodedText, Node root) {
        StringBuilder decoded = new StringBuilder();
        int index = 0;
        while (index < encodedText.length()) {
            Node current = root;
            while (current.left != null && current.right != null) {
                if (encodedText.charAt(index) == '0') {
                    current = current.left;
                } else {
                    current = current.right;
                }
                index++;
            }
            decoded.append(current.c);
        }
        System.out.println("Decoded text: " + decoded.toString());
    }

    static void displayHuffmanTree(Node root, int level) {
        if (root != null) {
            displayHuffmanTree(root.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("   ");
            }
            System.out.println(root.freq + " " + root.c);
            displayHuffmanTree(root.left, level + 1);
        }
    }

    public static void main(String[] args) {
        char[] charSet = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        int[] freq = {186, 64, 13, 22, 32, 103, 21, 15, 47, 57, 1, 5, 32, 20,
                57, 63, 15, 1, 48, 51, 80, 23, 8, 18, 1, 16, 1};

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the character set size n:");
        int n = scanner.nextInt();

        char[] customCharSet = new char[n];
        int[] customFreq = new int[n];

        System.out.println("Enter " + n + " characters:");
        for (int i = 0; i < n; i++) {
            customCharSet[i] = scanner.next().charAt(0);
        }

        System.out.println("Enter " + n + " weights:");
        for (int i = 0; i < n; i++) {
            customFreq[i] = scanner.nextInt();
        }

        Node root = buildTree(customCharSet, customFreq);

        System.out.println("Huffman Tree:");
        displayHuffmanTree(root, 0);

        Map<Character, String> codes = new HashMap<>();
        printCodes(root, "", codes);

        System.out.println("\nHuffman Codes:");
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nEnter text to encode (type 'quit' to exit):");
        String inputText = scanner.next();
        while (!inputText.equals("quit")) {
            StringBuilder encodedText = new StringBuilder();
            for (char c : inputText.toCharArray()) {
                encodedText.append(getCode(c, codes));
            }
            System.out.println("Encoded text: " + encodedText);

            System.out.println("Decoding...");
            decodeFile(encodedText.toString(), root);

            System.out.println("\nEnter text to encode (type 'quit' to exit):");
            inputText = scanner.next();
        }

        scanner.close();
    }
}
