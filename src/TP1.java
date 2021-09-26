import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

class TP1{
    private static InputReader in;
    private static PrintWriter out;
    private static Deque<Agent> agentRank = new LinkedList<>();

    static private String rankUpdate() {
        return null;
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        //inisiasi input yang diperlukan pada sistem
        int c = in.nextInt();   // banyak batch
        String space = in.next(); // batas dengan spasi
        int n = in.nextInt();   // banyak murid

        for (int i = 0; i < n; i++) {
            String agentName = in.next(); //nama murid
            String agentCode = in.next(); //code

            Agent agent = new Agent(agentName, agentCode);
            agentRank.addFirst(agent);
        }

        int e = in.nextInt(); //banyak hari latian

        for (int i = 0; i < e; i++) {
            int p = in.nextInt(); // banyak update rangking
            for (int j = 0; j < p; j++) {
                String studentName = in.next();
                int rankingCode = in.nextInt();
            }
        }
        if (in.next().equals("PANUTAN")) {
            int q = in.nextInt(); //batas ranking teratas

        }
        else if (in.next().equals("KOMPETITIF")){

        }
        else {

        }
    }

    static class Agent {
        String agentName;
        String agentCode;

        public Agent(String name, String code) {
            this.agentName = name;
            this.agentCode = code;
        }
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

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}