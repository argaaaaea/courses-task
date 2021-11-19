import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

class Lab2 {

    private static InputReader in;
    private static PrintWriter out;
    public static Queue<Penguin> pengQueue = new LinkedList<>();
    public static Map<String, Integer> satisfiedPenguin = new HashMap<>(10);
    public static int penguinCome = 0;

    static class Penguin {
        String type;
        Integer howMany;

        public Penguin(String type, int howMany) {
            this.type = type;
            this.howMany = howMany;
        }
    }

    // TODO
    static private int handleDatang(String Gi, int Xi) {
        Penguin newPenguin = new Penguin(Gi, Xi);
        pengQueue.add(newPenguin);
        if (!satisfiedPenguin.containsKey(Gi)) {
            satisfiedPenguin.put(Gi, 0);
        }
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
                Penguin p = pengQueue.remove();
                satisfiedPenguin.put(p.type, satisfiedPenguin.get(p.type) + p.howMany);

            } else if (Yi < 0) {
                pengQueue.peek().howMany -= newYi;
                satisfiedPenguin.put(pengQueue.peek().type, satisfiedPenguin.get(pengQueue.peek().type) + newYi);

            } else {
                pengQueue.peek().howMany -= newYi;
                Penguin p = pengQueue.remove();
                satisfiedPenguin.put(p.type, satisfiedPenguin.get(p.type) + newYi);
                return p.type;
            }
        }
        return pengQueue.peek().type;
    }

    // TODO
    static private int handleTotal(String Gi) {
        if (!satisfiedPenguin.containsKey(Gi)) {
            return 0;
        }
        return satisfiedPenguin.get(Gi);
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