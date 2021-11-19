import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

class Lab22 {

    private static InputReader in;
    private static PrintWriter out;
    public static Queue<Penguin> pengQueue = new LinkedList<>();
    public static ArrayList<Penguin> satisfiedPenguin = new ArrayList<>();
    public static int penguinCome = 0;

    static class Penguin {
        String type;
        int howMany;
        int satisfied;

        public Penguin(String type, int howMany) {
            this.type = type;
            this.howMany = howMany;
            this.satisfied = 0;
        }
    }

    // TODO
    static private int handleDatang(String Gi, int Xi) {
        Penguin newPenguin = new Penguin(Gi, Xi);
        pengQueue.add(newPenguin);
        penguinCome += Xi;
        return penguinCome;
    }

    // TODO
    static private String handleLayani(int Yi) {
        penguinCome -= Yi;
        while (Yi > 0) {
            int newYi = Yi;
            Yi = Yi - pengQueue.peek().howMany;

            if (Yi > 0) {
                pengQueue.peek().satisfied = pengQueue.peek().howMany;
                Penguin p = pengQueue.remove();
                satisfiedPenguin.add(p);

            } else if (Yi < 0) {
                pengQueue.peek().howMany -= newYi;
                pengQueue.peek().satisfied += newYi;

            } else {
                pengQueue.peek().satisfied += pengQueue.peek().howMany;
                Penguin p = pengQueue.remove();
                satisfiedPenguin.add(p);
                return p.type;
            }
        }
        return pengQueue.peek().type;
    }

    // TODO
    static private int handleTotal(String Gi) {
        int handled = 0;
        for (Penguin penguin : satisfiedPenguin) {
            if (penguin.type.equals(Gi)) {
                handled += penguin.satisfied;
            }
        }
        if (pengQueue.peek() == null) {
            return handled;
        } else if (pengQueue.peek().type.equals(Gi)) {
            handled += pengQueue.peek().satisfied;
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