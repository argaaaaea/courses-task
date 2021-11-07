import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class TP2 {
    private static InputReader in;
    private static PrintWriter out;
    private static Map<String, Pulau> mapPulau;

    static class NodeDaratan {
        public int tinggi;
        public NodeDaratan next;
        public NodeDaratan prev;
        public String kuil;

        public NodeDaratan(int tinggi) {
            this.tinggi = tinggi;
            this.next = null;
            this.prev = null;
            this.kuil = null;
        }

        public void addKuil(String input) {
            this.kuil = input;
        }
    }

    static class Pulau {
        public String nama;
        public NodeDaratan current;
        public NodeDaratan head;
        public int len;

        public Pulau(String nama) {
            this.nama = nama;
        }

        public void buatDaratan(int tinggi) {
            len++;
            if (len == 1) {
                current = new NodeDaratan(tinggi);
                head = current;
            } else if (len > 1) {
                NodeDaratan newNode = new NodeDaratan(tinggi);
                newNode.prev = current;
                current.next = newNode;
                current = newNode;
            }
        }

        public void hapusDaratan() {
            len--;
            if (len == 0) {
                current = null;
            }
            else if (len > 0) {
                current = current.prev;
                current.next = null;
            }
        }

        public void addKuil(String input) {
            current.addKuil(input);
        }
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Write here
        int jumlahPulau = in.nextInt();

        for (int i = 0; i < jumlahPulau; i++) {
            String namaPulau = in.next();
            Pulau pulauBaru = new Pulau(namaPulau);
            mapPulau.put(namaPulau, pulauBaru);
            int jumlahDaratan = in.nextInt();

            for (int j = 0; j < jumlahDaratan; j++) {
                int tinggi = in.nextInt();
                pulauBaru.buatDaratan(tinggi);
            }
            String kuil = "kuil ".concat(namaPulau);
            pulauBaru.addKuil(kuil);
            pulauBaru.current = pulauBaru.head;
        }

        String pulauRaiden = in.next();
        int daratanRaiden = in.nextInt();
        Pulau tempatRaiden = mapPulau.get(pulauRaiden);

        for (int i = 0; i < tempatRaiden.len; i++) {
            if (tempatRaiden.current.tinggi != daratanRaiden) {
                tempatRaiden.current = tempatRaiden.current.next;
            }
        }

        int jumlahMasukkan = in.nextInt();
        for (int i = 0; i < jumlahMasukkan; i++) {
            String perintah = in.next();
            switch(perintah) {
                case "UNIFIKASI": {

                }
                case "PISAH": {

                }
                case "GERAK": {

                }
                case "TEBAS": {

                }
                case "TELEPORTASI": {

                }
                case "RISE": {

                }
                case "QUAKE": {

                }
                case "CRUMBLE": {

                }
                case "STABILIZE": {

                }
                case "SWEEPING": {

                }
            }
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

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}