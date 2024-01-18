import java.io.*;
import java.util.*;

class Huffman {

    // TreeNode class
    static class TreeNode implements Comparable<TreeNode> {
        char ch;
        int freq;
        TreeNode left, right;

        public TreeNode(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        public int compareTo(TreeNode node) {
            return freq - node.freq;
        }
    }

    // Store encodings in a map
    static Map<Character, String> encodings = new HashMap<>();

    // Encode text
    static String encode(String text, TreeNode root) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(encodings.get(c));
        }
        return sb.toString();
    }

    // Decode text
    static String decode(String encodedText, TreeNode root) {
        StringBuilder sb = new StringBuilder();
        TreeNode curr = root;
        for (char c : encodedText.toCharArray()) {
            curr = c == '0' ? curr.left : curr.right;
            if (curr.left == null && curr.right == null) {
                sb.append(curr.ch);
                curr = root;
            }
        }
        return sb.toString();
    }

    // Build Huffman tree
    static TreeNode buildTree(int[] freqs) {

        PriorityQueue<TreeNode> pq = new PriorityQueue<>();
        for (int i = 0; i < freqs.length; i++) {
            if (freqs[i] > 0) {
                pq.add(new TreeNode((char)i, freqs[i]));
            }
        }

        while (pq.size() > 1) {
            TreeNode left = pq.poll();
            TreeNode right = pq.poll();

            TreeNode parent = new TreeNode('\0', left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }

        storeCodes(pq.peek(), "");
        return pq.peek();

    }

    // Store codes in map
    static void storeCodes(TreeNode root, String code) {
        if (root == null) return;

        if (root.ch != '\0') {
            encodings.put(root.ch, code);
        }

        storeCodes(root.left, code + "0");
        storeCodes(root.right, code + "1");
    }

    public static void main(String[] args) {

        int[] freqs = new int[128];

        try {
            File file = new File("data.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (char c : line.toCharArray()) {
                    freqs[c]++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find data.txt, using sample data");
            // Sample data
            freqs['A'] = 5;
            freqs['B'] = 9;
            freqs['C'] = 12;
            freqs['D'] = 13;
        }

        TreeNode root = buildTree(freqs);

        String sample = "BABAABA";
        String encoded = encode(sample, root);
        System.out.println("Encoded text: " + encoded);

        String decoded = decode(encoded, root);
        System.out.println("Decoded text: " + decoded);

    }

}