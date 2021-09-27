import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

class TP1{
    private static InputReader in;
    private static PrintWriter out;
    private static Deque<String> agentRank = new LinkedList<>();
    private static Map<String, String> agentOrigin = new HashMap<>();
    private static ArrayList<Integer> currentOriginHolder = new ArrayList<>();

    static private void rankUpdate(String agent, int rankCode) {
        agentRank.remove(agent);
        if (rankCode == 0) {
            agentRank.addFirst(agent);
        } else {
            agentRank.addLast(agent);
        }
    }

    static private void handlePanutan(int q) {
        int countB = 0;
        int countS = 0;
        Object[] agentString = agentRank.toArray();
        for (int i = 0; i < q; i++) {
            String currentAgentCode = agentOrigin.get((String) agentString[i]);
            if (currentAgentCode.equals("B")){
                countB++;
            } else {
                countS++;
            }
        }
        currentOriginHolder.add(countB);
        currentOriginHolder.add(countS);
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        //inisiasi input yang diperlukan pada sistem
        int c = in.nextInt();               //banyak batch

        for (int h = 0; h < c; h++) {
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
            String command = in.next();
            if (command.equals("PANUTAN")) {
                int q = in.nextInt(); //batas ranking teratas

                handlePanutan(q);
                for (Integer integer : currentOriginHolder) {
                    out.print(integer + " ");
                }
                out.println();
            }
            else if (command.equals("KOMPETITIF")){

            }
            else {

            }
            agentRank.clear();
            agentOrigin.clear();
            currentOriginHolder.clear();
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