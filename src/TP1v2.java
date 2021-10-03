import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class TP1v2{
    private static InputReader in;
    private static PrintWriter out;
    private static String currentAgent;
    private static int current;
    private static Queue<String> basoGeming = new LinkedList<>();
    private static Queue<String> somayGeming = new LinkedList<>();
    private static Deque<Integer> currentOriginHolder = new LinkedList<>();
    private static Queue<String> data1 = new LinkedList<>();
    private static Stack<String> data2 = new Stack<>();
    private static Map<String, Boolean> cek = new HashMap<>();
    private static int[][] saveDeploy;
    private static boolean[][] booleanDeploy;

    static private void siestaTukangNunjuk(Deque<String> agentRank, Map<String,Integer> agentChoosen, Map<String, Integer> lastCommand, String agent, Integer rankCode) {
        if (rankCode == 0) {
            agentRank.addFirst(agent);
            agentChoosen.put(agent, agentChoosen.get(agent) + 1);
        } else {
            agentRank.addLast(agent);
            agentChoosen.put(agent, agentChoosen.get(agent) + 1);
        }
        lastCommand.put(agent, rankCode);
    }

    static private void rankUpdate(Map<String, Integer> agentChoosen, Map<String, Integer> lastCommand, Deque<String> agentRank) {
        while (agentRank.size() != 0) {
            if (lastCommand.get(agentRank.peek()) == 0 || lastCommand.get(agentRank.peek()) == 2 || (cek.get(agentRank.peek()))) {
                String currAgent = agentRank.poll();
                if (!cek.get(currAgent)) {
                    data1.add(currAgent);
                    cek.put(currAgent, true);
                }
            } else {
                String currAgent = agentRank.pollLast();
                if (!cek.get(currAgent)) {
                    data2.add(currAgent);
                    cek.put(currAgent, true);
                }
            }
        }
        while (data1.size() != 0) {
            agentRank.add(data1.poll());
        }
        while (data2.size() != 0) {
            agentRank.add(data2.pop());
        }
    }

    static private void handlePanutan(Map<String, String> agentOrigin,Deque<String> agentRank ,int q, int b, int s) {
        int countB;
        int countS;
        if (q >= agentRank.size() / 2) {
            countB = b;
            countS = s;
            int size = agentRank.size();
            for (int i = 0; i < size - q; i++) {
                String agent = agentRank.pollLast();
                String currentAgentCode = agentOrigin.get(agent);
                if (currentAgentCode.equals("B")) {
                    countB--;
                } else {
                    countS--;
                }
            }
        } else {
            countB = 0;
            countS = 0;
            for (int i = 0; i < q; i++) {
                String agent = agentRank.poll();
                String currentAgentCode = agentOrigin.get(agent);
                if (currentAgentCode.equals("B")){
                    countB++;
                } else {
                    countS++;
                }
            }
        }
        currentOriginHolder.addFirst(countB);
        currentOriginHolder.addLast(countS);
    }

    static private void handleKompetitif(Map<String, Integer> agentChoosen,Deque<String> agentRank ,int n) {
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
    static private int recs(Object[] rank,Map<String, String> agentOrigin, int atasi) {
        int answer = 0;
        int counter = 0;
        saveDeploy = new int[rank.length + 1][rank.length + 1];
        booleanDeploy = new boolean[rank.length + 1][rank.length + 1];

        answer = recursive(counter, counter+1, atasi, rank, agentOrigin, answer);
        return answer % (1000000007);
    }
    static private int recursive(int awal, int akhir, int kelompok, Object[] rank, Map<String, String> agentOrigin, int ans) {
        System.out.println(awal + " " + akhir + " " + kelompok);
        int total = 0;
        if (booleanDeploy[awal][akhir]){
            System.out.println("here");
            return kelompok = kelompok-1;
        }
        if (kelompok == 1 && akhir == rank.length - 1 && agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir]))) {
            booleanDeploy[awal][akhir] = true;
            System.out.println("here1");
            return 1;
        } else if ((kelompok != 1 && akhir == (rank.length - 1))) {
            booleanDeploy[awal][akhir] = true;
            System.out.println("here0v1");
            return 0;
        } else if ((kelompok == 1 && akhir == (rank.length - 1)) && !(agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir])))) {
            booleanDeploy[awal][akhir] = true;
            System.out.println("here0v2");
            return 0;
        } else if (akhir != rank.length - 2 && agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir]))) {
           long recur = recursive(akhir + 1, akhir + 2, kelompok - 1, rank, agentOrigin, ans) + recursive(awal, akhir + 1, kelompok, rank, agentOrigin, ans);
           total += recur;
        } else if (akhir == rank.length - 1 && agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir]))){
            long recur = recursive(awal, akhir, kelompok - 1, rank, agentOrigin, ans);
            total += recur;
        } else {
            long recur = recursive(awal, akhir + 1, kelompok, rank, agentOrigin, ans);
            total += recur;
        }
        saveDeploy[awal][akhir] = total;
        return total;
    }

    //    static private int recursive(int awal, int akhir, int kelompok, Object[] rank, Map<String, String> agentOrigin) {
//        int ans = 0;
//        System.out.print(awal + " ");
//        System.out.print(akhir + " ");
//        System.out.println(kelompok);
//        if (kelompok == 1 && akhir == rank.length - 1 && agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir]))) {
//            return 1;
//        } else if ((kelompok != 1 && akhir == (rank.length - 1))) {
//            return 0;
//        }
//        else if ((kelompok == 1 && akhir == (rank.length - 1)) && !(agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir])))) {
//            return 0;
//        }
//        else if (akhir != rank.length - 2 && agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir]))) {
//            ans = recursive(akhir + 1, akhir + 2, kelompok - 1, rank, agentOrigin) + recursive(awal, akhir + 1, kelompok, rank, agentOrigin);
//        } else if (akhir == rank.length - 1 && agentOrigin.get((String) rank[awal]).equals(agentOrigin.get((String) rank[akhir]))){
//            ans = recursive(awal, akhir, kelompok - 1, rank, agentOrigin);
//        } else {
//            ans = recursive(awal, akhir + 1, kelompok, rank, agentOrigin);
//        }
//        return ans;
//    }
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        //inisiasi input yang diperlukan pada sistem
        int c = in.nextInt();                   //banyak batch
        for (int h = 0; h < c; h++) {
            int n = in.nextInt();               //banyak murid
            int b = 0;
            int s = 0;
            Set<String> dontAdd = new HashSet<>(n);
            Deque<String> agentRank = new LinkedList<>();
            Map<String, Integer> rank = new HashMap<>(n);
            Map<String, String> agentOrigin = new HashMap<>(n);
            Map<String, Integer> agentChoosen = new HashMap<>(n);
            Map<String, Integer> lastCommand = new HashMap<>();

            for (int i = 0; i < n; i++) {
                String agentName = in.next();   //nama murid
                String agentCode = in.next();   //code
                agentRank.add(agentName);
                agentOrigin.put(agentName, agentCode);
                agentChoosen.put(agentName, 0);
                rank.put(agentName, i + 1);
                lastCommand.put(agentName, 2);
                cek.put(agentName, false);
                if (agentCode.equals("B")) {
                    b++;
                } else {
                    s++;
                }
            }
            int e = in.nextInt();               //banyak hari latian
            for (int i = 0; i < e; i++) {

                int p = in.nextInt();           //banyak update rangking
                for (int j = 0; j < p; j++) {
                    String agentName = in.next();
                    int rankingCode = in.nextInt();
                    siestaTukangNunjuk(agentRank, agentChoosen, lastCommand, agentName, rankingCode);
                }
                rankUpdate(agentChoosen, lastCommand, agentRank);
                for (int j = 0; j < n; j++) {
                    String tempAgent = agentRank.poll();
                    if (j + 1 < rank.get(tempAgent)) {
                        dontAdd.add(tempAgent);
                    }
                    rank.put(tempAgent, j + 1);
                    out.print(tempAgent);
                    out.print(" ");
                    agentRank.addLast(tempAgent);
                    lastCommand.put(tempAgent, 2);
                    cek.put(tempAgent, false);
                }
                out.println();
            }
            String command = in.next();

            switch (command) {
                case "PANUTAN": {
                    int q = in.nextInt(); //batas ranking teratas
                    handlePanutan(agentOrigin, agentRank, q, b, s);
                    for (int i = 0; i < 2; i++) {
                        Integer integer = currentOriginHolder.poll();
                        out.print(integer);
                        out.print(" ");
                    }
                    out.println();
                    break;
                }
                case "KOMPETITIF": {
                    handleKompetitif(agentChoosen, agentRank, n);
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
                    int kelompok = in.nextInt();
                    int counter = 0;
                    Object[] stringRank = agentRank.toArray();
                    out.print(recs(stringRank, agentOrigin, kelompok));
                    out.println();

                    break;
                }
            }
            b = 0;
            s = 0;
            //somayGeming = new LinkedList<>();
            //basoGeming = new LinkedList<>();
            //dontAdd = new HashSet<>();
            //rank = new HashMap<>();
            //agentRank = new LinkedList<>();
            //agentOrigin = new HashMap<>();
            //agentChoosen = new HashMap<>();
            somayGeming.clear();
            basoGeming.clear();
            dontAdd.clear();
            rank.clear();
            agentRank.clear();
            agentOrigin.clear();
            agentChoosen.clear();
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