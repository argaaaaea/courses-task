import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.StringTokenizer;

class TP1{
    private static InputReader in;
    private static PrintWriter out;
    private static String currentAgent;
    private static int current;
    private static Deque<String> agentRank = new LinkedList<>();
    private static Map<String, String> agentOrigin = new HashMap<>();
    private static Deque<Integer> currentOriginHolder = new LinkedList<>();
    private static Map<String, Integer> agentChoosen = new HashMap<>();
    private static Queue<String> dontAdd = new LinkedList<>();
    private static Map<String, Integer> rank = new HashMap<>();

    static private void rankUpdate(String agent, int rankCode) {
        agentRank.remove(agent);
        if (rankCode == 0) {
            agentRank.addFirst(agent);
            agentChoosen.put(agent, agentChoosen.get(agent) + 1);
        } else {
            agentRank.addLast(agent);
            agentChoosen.put(agent, agentChoosen.get(agent) + 1);
        }
    }

    static private void handlePanutan(int q) {
        int countB = 0;
        int countS = 0;
        for (int i = 0; i < q; i++) {
            String currentAgentCode = agentOrigin.get(agentRank.poll());
            if (currentAgentCode.equals("B")){
                countB++;
            } else {
                countS++;
            }
        }
        currentOriginHolder.addFirst(countB);
        currentOriginHolder.addLast(countS);
    }

    static private void handleKompetitif(int n) {
        currentAgent = agentRank.poll();
        current = agentChoosen.get(currentAgent);
        int size = agentRank.size();
        for (int i = 0; i < size ; i++) {
            String tempAgent = agentRank.poll();
            if (current < agentChoosen.get(tempAgent)) {
                currentAgent = tempAgent;
                current = agentChoosen.get(tempAgent);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

            //inisiasi input yang diperlukan pada sistem
        int c = in.nextInt();                   //banyak batch
        for (int h = 0; h < c; h++) {
            int n = in.nextInt();               //banyak murid

            for (int i = 0; i < n; i++) {
                String agentName = in.next();   //nama murid
                String agentCode = in.next();   //code

                agentRank.add(agentName);
                agentOrigin.put(agentName, agentCode);
                agentChoosen.put(agentName, 0);
                rank.put(agentName, i+1);
            }
            int e = in.nextInt();               //banyak hari latian
            StringBuilder stringHasil = new StringBuilder();

            for (int i = 0; i < e; i++) {
                int p = in.nextInt();           //banyak update rangking
                for (int j = 0; j < p; j++) {
                    String agentName = in.next();
                    int rankingCode = in.nextInt();

                    rankUpdate(agentName, rankingCode);
                    }

                for (int j = 0; j < n; j++) {
                    String tempAgent = agentRank.poll();
                    if (j + 1 < rank.get(tempAgent) && !(dontAdd.contains(tempAgent))){
                        dontAdd.add(tempAgent);
                    }
                    rank.put(tempAgent, j + 1);
                    stringHasil.append(tempAgent);
                    stringHasil.append(" ");
                    agentRank.addLast(tempAgent);
                    if ((j + 1) % n == 0) {
                        stringHasil.append("\n");
                    }
                }
            }
            String command = in.next();

            switch (command) {
                case "PANUTAN": {
                    int q = in.nextInt(); //batas ranking teratas
                    handlePanutan(q);
                    for (int i = 0; i < 2; i++) {
                        Integer integer = currentOriginHolder.poll();
                        stringHasil.append(integer);
                        stringHasil.append(" ");
                    }
                    break;
                }
                case "KOMPETITIF": {
                    handleKompetitif(n);
                    stringHasil.append(currentAgent);
                    stringHasil.append(" ");
                    stringHasil.append(current);
                    current = 0;
                    currentAgent = "";
                    break;
                }
                case "EVALUASI": {
                    if (dontAdd.size() == agentRank.size()) {
                        stringHasil.append("TIDAK ADA");
                    }
                    else {
                        agentRank.removeAll(dontAdd);
                        int size = agentRank.size();
                        for (int i = 0; i < size; i++) {
                            String currAgent = agentRank.poll();
                            System.out.println(currAgent);
                            stringHasil.append(currAgent);
                            stringHasil.append(" ");
                        }
                    }
                    break;
                }
                case "DUO": {

                    break;
                }
                case "DEPLOY": {

                    break;
                }
            }
            out.println(stringHasil);
            stringHasil.setLength(0);
            dontAdd = new LinkedList<>();
            rank = new HashMap<>();
            agentRank = new LinkedList<>();
            agentOrigin = new HashMap<>();
            agentChoosen = new HashMap<>();
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