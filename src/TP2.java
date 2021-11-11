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
    private static Map<String, Pulau> mapKuil;

    static class NodeDaratan {
        public int tinggi;
        public NodeDaratan next, prev, raiden;
        public String namaKuil;

        public NodeDaratan(int tinggi){
            this.tinggi = tinggi;
        }

        public void addKuil(String namaKuil) {
            this.namaKuil = namaKuil;
        }
    }

    static class Pulau {
        public String namaPulau;
        public NodeDaratan head, tail, current;
        public Pulau next, prev;
        public int length;

        public Pulau(String namaPulau) {
            this.namaPulau = namaPulau;
        }

        public void addDaratan(int tinggi) {
            NodeDaratan newDaratan = new NodeDaratan(tinggi);
            if (length == 0) {
                this.head = newDaratan;
                this.tail = newDaratan;
            } else if (length > 1) {
                this.tail.next = newDaratan;
                newDaratan.prev = this.tail;
                this.tail = newDaratan;
            }
            length++;
        }

        public void gabunginDaratan(Pulau pulau) {
            this.tail.next = pulau.head;
            pulau.head.prev = this.tail;
            this.length += pulau.length;
            this.tail = pulau.tail;
        }

        public void gabunginPulau(Pulau pulau) {
            while (this.next != null) {
                this.next = this.next.next;
                this.length += pulau.length;
            }
            this.next = pulau;
            pulau.prev = this;
        }

        public void separatismeDaratan() {
            NodeDaratan sebelumhead = this.head.prev;
            sebelumhead.next = null;
            this.head.prev = null;
        }

        public void separatismePulau() {
            Pulau pulausebelumnya = this.prev;
            pulausebelumnya.next = null;
            this.prev = null;
            while (pulausebelumnya != null) {
                pulausebelumnya.length -= this.length;
                pulausebelumnya = pulausebelumnya.prev;
            }
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
                pulauBaru.addDaratan(tinggi);
            }
            if (pulauBaru.length == 1) {
                pulauBaru.head.addKuil(namaPulau);
                mapKuil.put(namaPulau, pulauBaru);
            }
        }

        String pulauRaiden = in.next();
        int daratanKe = in.nextInt();
        Pulau tempatRaiden = mapPulau.get(pulauRaiden);

        NodeDaratan raidendisinibos = tempatRaiden.head;
        for (int i = 1; i < tempatRaiden.length; i++) {
            if (i != daratanKe) {
                raidendisinibos = raidendisinibos.next;
            } else {
                tempatRaiden.current = raidendisinibos;
            }
        }

        int jumlahMasukkan = in.nextInt();
        for (int i = 0; i < jumlahMasukkan; i++) {
            String perintah = in.next();
            switch (perintah) {
                case "UNIFIKASI": {
                    String joinkesini = in.next();
                    String sokinjoin = in.next();
                    Pulau pulaujoin = mapPulau.get(joinkesini);
                    Pulau yangdijoinin = mapPulau.get(sokinjoin);
                    pulaujoin.gabunginDaratan(yangdijoinin);
                    pulaujoin.gabunginPulau(yangdijoinin);
                }
                case "PISAH": {
                    String gerakanseparatis = in.next();
                    Pulau adayangmakar = mapKuil.get(gerakanseparatis);
                    adayangmakar.separatismeDaratan();
                    adayangmakar.separatismePulau();
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