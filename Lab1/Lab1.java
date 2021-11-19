import java.io.*;
import java.util.*;

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    /**
     * The main method that reads input, calls the function 
     * for each question's query, and output the results.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args) {
        
        int N = in.nextInt();   // banyak bintang
        int M = in.nextInt();   // panjang sequence
        
        List<String> sequence = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            String temp = in.next();
            sequence.add(temp);
        }

        int maxMoney = getMaxMoney(N, M, sequence);
        out.println(maxMoney);
        out.close();
    }

    public static int getMaxMoney(int N, int M, List<String> sequence) {
        int maxThisSequence = 0;
        int currentMax = Integer.MIN_VALUE;
        int current = 0;
        // TODO
        for (int i = 1; i < M; i++) {
            try {
                current = Integer.parseInt(sequence.get(i));
                maxThisSequence += current;
            } catch (NumberFormatException e) {
                if (currentMax < maxThisSequence) {
                    currentMax = maxThisSequence;
                }
                if (maxThisSequence < 0) {
                    maxThisSequence = 0;
                }
            }
        }
        
        return currentMax;
    }
    
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}