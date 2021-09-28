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
    private static ArrayList<String> rankDown = new ArrayList<>();
    private static StringBuilder stringHasil = new StringBuilder();
    private static Deque<String> dontAddThis = new LinkedList<>();


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
        Object[] agentString = agentRank.toArray();
        for (int i = 0; i < q; i++) {
            String currentAgentCode = agentOrigin.get((String) agentString[i]);
            if (currentAgentCode.equals("B")){
                countB++;
            } else {
                countS++;
            }
            agentOrigin.remove((String) agentString[i]);
        }
        currentOriginHolder.addFirst(countB);
        currentOriginHolder.addLast(countS);
    }

    static private void handleKompetitif(int n) {
        Object[] stringAgent = agentRank.toArray();
        currentAgent = (String) stringAgent[0];
        current = agentChoosen.get(currentAgent);

        for (int i = 0; i < agentRank.size() - 1; i++) {
            if (current < agentChoosen.get((String) stringAgent[i + 1])) {
                currentAgent = (String) stringAgent[i + 1];
                current = agentChoosen.get((String) stringAgent[i + 1]);
            }
        }
    }

    static private void handleEvaluasi(ArrayList<String> rankYesterday,ArrayList<String> rankToday, int n) {
        for (int i = 0; i < n; i++) {
            String current = rankYesterday.get(i);
            if (rankToday.indexOf(current) < i) {
                dontAddThis.add(current);
            }
        }
    }

    static private void printEvaluasi(){
        if (dontAddThis.isEmpty()){
            rankDown.add("TIDAK ADA");
        }
        else {
            for (Object agent : agentRank.toArray()) {
                if (!Objects.equals(dontAddThis.poll(), agent)) {
                    rankDown.add((String) agent);
                }
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
            }
            int e = in.nextInt();               //banyak hari latian

            for (int i = 0; i < e; i++) {
                int p = in.nextInt();           //banyak update rangking
                ArrayList<String> rankYesterday = new ArrayList<String>(agentRank);
                for (int j = 0; j < p; j++) {
                    String agentName = in.next();
                    int rankingCode = in.nextInt();
                    rankUpdate(agentName, rankingCode);
                }
                ArrayList<String> rankToday = new ArrayList<String>(agentRank);
                handleEvaluasi(rankYesterday, rankToday, n);

                Object[] stringRank = agentRank.toArray();
                for (int j = 0; j < n; j++) {
                    stringHasil.append(stringRank[j]);
                    stringHasil.append(" ");
                    if ((j + 1) % n == 0){
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
                    out.print("\n");
                    break;
                }
                case "KOMPETITIF": {
                    handleKompetitif(n);
                    stringHasil.append(currentAgent);
                    stringHasil.append(" ");
                    stringHasil.append(current);
                    out.print("\n");
                    current = 0;
                    currentAgent = "";
                    break;
                }
                case "EVALUASI":
                    printEvaluasi();
                    for (int i = 0; i < rankDown.size(); i++) {
                        Object[] stringRankDown = rankDown.toArray();
                        stringHasil.append(stringRankDown[i]);
                        stringHasil.append(" ");
                    }
                    out.print("\n");
                    rankDown.clear();
                    break;
            }
            out.print(stringHasil);
            stringHasil.setLength(0);
            agentRank.clear();
            agentChoosen.clear();
            rankDown.clear();
            dontAddThis.clear();
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