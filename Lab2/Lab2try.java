import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

class Lab2try {

    private static InputReader in;
    private static PrintWriter out;
    private static Queue<String> pengQueue = new LinkedList<>();
    private static ArrayList<String> totalHandled = new ArrayList<>();

    // TODO
    static private int handleDatang(String Gi, int Xi) {
        for (int i = 0; i < Xi; i++) {
            pengQueue.add(Gi);
        }
        return pengQueue.size();
    }

    // TODO
    static private String handleLayani(int Yi) {

        for (int i = 0; i < Yi; i++) {
            String current = pengQueue.peek();
            totalHandled.add(current);
            pengQueue.remove();
        }
        return pengQueue.peek();
    }

    // TODO
    static private int handleTotal(String Gi) {
        int handled = 0;
        for (int i = 0; i < totalHandled.size(); i++) {
            if (totalHandled.get(i).equals(Gi))
                handled++;
        }
        return handled;
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for (int tmp = 0; tmp < N; tmp++) {
            String event = in.next();

            if (event.equals("DATANG")) {
                String Gi = in.next();
                int Xi = in.nextInt();

                out.println(handleDatang(Gi, Xi));
            } else if (event.equals("LAYANI")) {
                int Yi = in.nextInt();

                out.println(handleLayani(Yi));
            } else {
                String Gi = in.next();

                out.println(handleTotal(Gi));
            }
        }

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the
    // usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit
    // Exceeded caused by slow input-output (IO)
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