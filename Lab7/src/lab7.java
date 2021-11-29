import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class lab7 {
    private static InputReader in;
    private static PrintWriter out;
    private static int[][] graph;
    private static int[][] graph2;

    // TODO: method to initialize graph
    public static void createGraph(int N) {
        graph = new int[N][N];
        graph2 = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = 1001;
                }
            }
        }
    }
    // TODO: method to create an edge with type==T that connects vertex U and vertex V in a graph
    public static void addEdge(int U, int V, int T) {
        graph[U-1][V-1] = T;
        graph[V-1][U-1] = T;
    }

    // TODO: Handle teman X Y K
    public static int canMudik(int X, int Y, int K) {
        if (graph2[X-1][Y-1] <= K) {
            return 1;
        }
        return 0;
    }
    // i get this code from https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/ with some changes
    public static void floydGFG(int V) {
        graph2 = new int[V][V];
        int i, j, k;

        /* Initialize the solution matrix
           same as input graph matrix.
           Or we can say the initial values
           of shortest distances
           are based on shortest paths
           considering no intermediate
           vertex. */
        for (i = 0; i < V; i++)
            for (j = 0; j < V; j++)
                graph2[i][j] = graph[i][j];

        /* Add all vertices one by one
           to the set of intermediate
           vertices.
          ---> Before start of an iteration,
               we have shortest
               distances between all pairs
               of vertices such that
               the shortest distances consider
               only the vertices in
               set {0, 1, 2, .. k-1} as
               intermediate vertices.
          ----> After the end of an iteration,
                vertex no. k is added
                to the set of intermediate
                vertices and the set
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < V; k++)
        {
            // Pick all vertices as source one by one
            for (i = 0; i < V; i++)
            {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < V; j++)
                {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (graph2[i][k] + graph2[k][j] < graph2[i][j])
                        graph2[i][j] = graph2[i][k] + graph2[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N = in.nextInt();
        int M = in.nextInt();
        int Q = in.nextInt();
        createGraph(N);

        for (int i=0;i < M;i++) {
            int U = in.nextInt();
            int V = in.nextInt();
            int T = in.nextInt();
            addEdge(U, V, T);
        }
        floydGFG(N);
        while(Q-- > 0) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int K = in.nextInt();
            out.println(canMudik(X, Y, K));
        }

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
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