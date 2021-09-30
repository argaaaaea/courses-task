import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.StringTokenizer;

class TP1v2{
    private static InputReader in;
    private static PrintWriter out;
    private static String currentAgent;
    private static int current;
    private static Queue<String> dontAdd = new LinkedList<>();
    private static Deque<String> agentRank = new LinkedList<>();
    private static Queue<String> basoGeming = new LinkedList<>();
    private static Queue<String> somayGeming = new LinkedList<>();
    private static Deque<Integer> currentOriginHolder = new LinkedList<>();
    private static Stack<Integer> angkaGeming = new Stack<>();
    private static Stack<String> stringGeming = new Stack<>();
    private static Map<String, Boolean> udahPernahGeming = new HashMap<>();
    private static Map<String, String> agentOrigin = new HashMap<>();
    private static Map<String, Integer> agentChoosen = new HashMap<>();
    private static Map<String, Integer> rank = new HashMap<>();

    static private void rankUpdate(Stack<Integer> integer, Stack<String> string) {
        while (!(integer.size() == 0)) {
            String agent = string.pop();
            Integer rankCode = integer.pop();
            if (!udahPernahGeming.get(agent)) {
                agentRank.remove(agent);
                if (rankCode == 0) {
                    agentRank.addFirst(agent);
                    udahPernahGeming.put(agent, true);
                } else {
                    agentRank.addLast(agent);
                    udahPernahGeming.put(agent, true);
                }
            }
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

                    stringGeming.add(agentName);
                    angkaGeming.add(rankingCode);
                    udahPernahGeming.put(agentName, false);
                }
                rankUpdate(angkaGeming, stringGeming);

                for (int j = 0; j < n; j++) {
                    String tempAgent = agentRank.poll();
                    if (j + 1 < rank.get(tempAgent)){
                        dontAdd.add(tempAgent);
                    }
                    rank.put(tempAgent, j + 1);
                    out.print(tempAgent);
                    out.print(" ");
                    agentRank.addLast(tempAgent);
                }
                out.println();
            }
            String command = in.next();

            switch (command) {
                case "PANUTAN": {
                    int q = in.nextInt(); //batas ranking teratas
                    handlePanutan(q);
                    for (int i = 0; i < 2; i++) {
                        Integer integer = currentOriginHolder.poll();
                        out.print(integer);
                        out.print(" ");
                    }
                    out.println();
                    break;
                }
                case "KOMPETITIF": {
                    handleKompetitif(n);
                    out.print(currentAgent);
                    out.print(" ");
                    out.print(current);
                    out.println();
                    current = 0;
                    currentAgent = "";
                    break;
                }
                case "EVALUASI": {
                    agentRank.removeAll(dontAdd);
                    int size = agentRank.size();
                    if (size == 0) {
                        out.print("TIDAK ADA");
                    }
                    else {
                        for (int i = 0; i < size; i++) {
                            String currAgent = agentRank.poll();
                            out.print(currAgent);
                            out.print(" ");
                        }
                    }
                    out.println();
                    break;
                }
                case "DUO": {
                    int size = agentRank.size();
                    for (int i = 0; i < size; i++) {
                        String agent = agentRank.poll();
                        if (agentOrigin.get(agent).equals("B")) {
                            basoGeming.add(agent);
                        } else {
                            somayGeming.add(agent);
                        }
                    }
                    int sizeGeming = Math.min(basoGeming.size(), somayGeming.size());
                    for (int i = 0; i < sizeGeming; i++) {
                        out.print(basoGeming.poll());
                        out.print(" ");
                        out.print(somayGeming.poll());
                        out.println();
                    }
                    if (basoGeming.size() == 0 && somayGeming.size() != 0) {
                        out.print("TIDAK DAPAT: ");
                        while (somayGeming.size() != 0) {
                            out.print(somayGeming.poll());
                            out.print(" ");
                        }
                        out.println();
                    } else if (somayGeming.size() == 0 && basoGeming.size() != 0) {
                        out.print("TIDAK DAPAT: ");
                        while (basoGeming.size() != 0) {
                            out.print(basoGeming.poll());
                            out.print(" ");
                        }
                        out.println();
                    }
                    break;
                }
                case "DEPLOY": {

                    break;
                }
            }
            stringGeming = new Stack<>();
            angkaGeming = new Stack<>();
            somayGeming = new LinkedList<>();
            basoGeming = new LinkedList<>();
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