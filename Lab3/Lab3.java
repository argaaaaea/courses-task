import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;

class Lab3try {

    private static InputReader in;
    private static PrintWriter out;
    private static long minDig = 0;

    // TODO
    static private Long findMaxBerlian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        int day = M.size();
        int dig = M.size();
        int xAxisLength = 3;
        long[][][] multiLongArray = new long[dig + 1][day + 1][xAxisLength];
        long maxDiamond = 0;
        B.add(0, 0);
        M.add(0, 0);
        S.add(0, 0);

        for (int i = 1; i <= dig; i++) {
            for (int j = 1; j <= day; j++) {
                if (i > j) {
                    continue;
                }
                for (int k = 0; k < xAxisLength; k++) {
                    if (k == 0) {
                        multiLongArray[i][j][k] = max(multiLongArray[i - 1][j - 1][1], multiLongArray[i - 1][j - 1][2])
                                + S.get(j) + B.get(i) - B.get(i - 1);
                        if (multiLongArray[i][j][k] > maxDiamond) {
                            maxDiamond = multiLongArray[i][j][k];
                            minDig = i;
                        }
                    } else if (k == 1) {
                        multiLongArray[i][j][k] = max(multiLongArray[i - 1][j - 1][0], multiLongArray[i - 1][j - 1][2])
                                + M.get(j) + B.get(i) - B.get(i - 1);
                        if (multiLongArray[i][j][k] > maxDiamond) {
                            maxDiamond = multiLongArray[i][j][k];
                            minDig = i;
                        }
                    } else {
                        multiLongArray[i][j][k] = max(multiLongArray[i][j - 1][0],
                                max(multiLongArray[i][j - 1][1], multiLongArray[i][j - 1][2]));
                        if (multiLongArray[i][j][k] > maxDiamond) {
                            maxDiamond = multiLongArray[i][j][k];
                            minDig = i;
                        }
                    }
                }
            }
        }
        return maxDiamond;
    }

    // TODO
    static private long findBanyakGalian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        return minDig;
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        ArrayList<Integer> S = new ArrayList<>();
        ArrayList<Integer> M = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();

        int N = in.nextInt();

        for (int i = 0; i < N; i++) {
            int tmp = in.nextInt();
            S.add(tmp);
        }

        for (int i = 0; i < N; i++) {
            int tmp = in.nextInt();
            M.add(tmp);
        }

        for (int i = 0; i < N; i++) {
            int tmp = in.nextInt();
            B.add(tmp);
        }

        long jawabanBerlian = findMaxBerlian(S, M, B);
        long jawabanGalian = findBanyakGalian(S, M, B);

        out.print(jawabanBerlian + " " + jawabanGalian);

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