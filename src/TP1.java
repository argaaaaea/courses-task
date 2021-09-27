import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

class TP1{
    private static InputReader in;
    private static PrintWriter out;
    private static Deque<String> agentRank = new LinkedList<>();
    private static Map<String, String> agentOrigin = new HashMap<>();

    static private void rankUpdate(String agent, int rankCode) {
        agentRank.remove(agent);
        if (rankCode == 0) {
            agentRank.addFirst(agent);
        } else {
            agentRank.addLast(agent);
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        //inisiasi input yang diperlukan pada sistem
        int c = in.nextInt();               //banyak batch
        int n = in.nextInt();               //banyak murid

        for (int i = 0; i < n; i++) {
            String agentName = in.next();   //nama murid
            String agentCode = in.next();   //code

            agentRank.add(agentName);
            agentOrigin.put(agentName, agentCode);
        }

        int e = in.nextInt();               //banyak hari latian

        for (int i = 0; i < e; i++) {
            int p = in.nextInt();           //banyak update rangking
            for (int j = 0; j < p; j++) {
                String agentName = in.next();
                int rankingCode = in.nextInt();
                rankUpdate(agentName, rankingCode);
            }
            Object[] stringRank = agentRank.toArray();
            for (int j = 0; j < n; j++) {
                out.print(stringRank[j] + " ");
            }
            out.println();
        }
        if (in.next().equals("PANUTAN")) {
            int q = in.nextInt(); //batas ranking teratas

        }
        else if (in.next().equals("KOMPETITIF")){

        }
        else {

        }
        out.flush();
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