import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class TP2v2 {
    private static InputReader in;
    private static PrintWriter out;
    private static Map<String, Pulau> mapPulau = new HashMap<>();
    private static Map<String, Pulau> mapKuil = new HashMap<>();

    static class NodeDaratan {
        public long tinggi;
        public NodeDaratan next, prev;
        public String namaKuil;

        public NodeDaratan(long tinggi){
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
            } else if (length >= 1) {
                this.tail.next = newDaratan;
                newDaratan.prev = this.tail;
                this.tail = newDaratan;
            }
            length++;
        }

        public void gabunginDaratan(Pulau pulau) {
//            this.tail.next = pulau.head;
//            out.println(tail.next.tinggi);
//            pulau.head.prev = this.tail;
            Pulau head = this;
//            out.println(head.namaPulau + " " + this.tail.tinggi +" "+ this.tail.next.tinggi);
            if (head.next != null) {
                while (head.next != null) {
                    head.tail = pulau.tail;
//                    out.println(head.namaPulau + " ekornya " + head.tail.tinggi);
                    head = head.next;
                }
            }
//            out.println(head.namaPulau);
            NodeDaratan b = pulau.head;
            head.tail.next = pulau.head;
            pulau.head.prev = head.tail;
            NodeDaratan node = this.head;

//            out.println(head.namaPulau + " ekornya: " + head.tail.tinggi);
        }

        public void gabunginPulau(Pulau pulau) {
            this.length += pulau.length;
            Pulau current = this;
            if (current.next != null) {
                while (current.next != null) {
                    current = current.next;
                    current.length += pulau.length;
                }
            }
            current.next = pulau;
            pulau.prev = current;
//            out.println("UNIFIKASI panjang pulau " + this.namaPulau + " " + this.length);
            out.println(this.length);
        }

        public void separatismeDaratan() {
            NodeDaratan sebelumhead = this.head.prev;
            sebelumhead.next = null;
            this.head.prev = null;
            Pulau pulau = this.prev;
            if (pulau.prev != null) {
                while (pulau.prev != null) {
                    pulau.tail = sebelumhead;
//                    out.println(pulau.namaPulau + " ekornya: " + pulau.tail.tinggi);
                    pulau = pulau.prev;
                }
            }
            pulau.tail = sebelumhead;
        }

        public void separatismePulau() {
            Pulau pulausebelumnya = this.prev;
            pulausebelumnya.length -= this.length;
            this.prev.next = null;
            this.prev = null;
            while (pulausebelumnya.prev != null) {
                pulausebelumnya.prev.length -= this.length;
                pulausebelumnya = pulausebelumnya.prev;
            }
            out.println(pulausebelumnya.length + " " + this.length);
        }

        public void kekananmanise() {
            if (this.current.next != null) {
                this.current = this.current.next;
            }
        }

        public void kekirimanise() {
            if (this.current.prev != null) {
                this.current = this.current.prev;
            }
        }

        public int bikingunung(int minimalnaik, int tinggi) {
            NodeDaratan awal = this.head;
//            out.println(this.tail.tinggi+ " " +this.namaPulau);
            int count = 0;
            if (awal.next != null) {
                while (awal.next != null) {
//                out.println("nilainya segini " + awal.tinggi);
                    if (awal.tinggi > minimalnaik) {
                        awal.tinggi += tinggi;
//                        out.println("jadi segini " + awal.tinggi);
                        count++;
                    }
                    awal = awal.next;
                }
            }
//            out.println(awal.tinggi + " > " + minimalnaik);
            if (awal.tinggi > minimalnaik) {
//                out.println("masuk kesini");
                awal.tinggi += tinggi;
                count++;
            }
            return count;
        }

        public int bikinlembah(int minimalturun, int tinggi) {
            NodeDaratan awal = this.head;
            int count = 0;
            if (awal.next != null) {
                while (awal.next != null) {
//                    out.println("nilainya segini " + awal.tinggi);
                    if (awal.tinggi < minimalturun) {
                        awal.tinggi -= tinggi;
//                        out.println("jadi segini " + awal.tinggi);
                        count++;
                    } awal = awal.next;
                }
            }
//            out.println(awal.tinggi + " < " + minimalturun);
            if (awal.tinggi < minimalturun) {
                awal.tinggi -= tinggi;
                count++;
            }
            return count;
        }

        public void hancurinbos() {
            long tinggiyghilang = this.current.tinggi;
//            System.out.println("tadi ada di sini " + tinggiyghilang);
            if (this.current.namaKuil != null) {
                out.println(0);
                return;
            }
            if (this.current.prev != null && this.current.next == null) {
                this.current.prev.next = null;
                this.current = this.current.prev;
                Pulau tail = this;
                if (tail.prev != null) {
                    while (tail.prev != null) {
                        tail.tail = this.current;
//                    out.println(tail.namaPulau + " " + tail.tail.tinggi);
                        tail = tail.prev;
                    }
                } tail.tail = this.current;
//                out.println(tail.namaPulau + " " + tail.tail.tinggi);
//                System.out.println("sekarang ada di sini " + this.current.tinggi);
            }
            else if (this.current.prev != null) {
                this.current.prev.next = this.current.next;
                this.current.next.prev = this.current.prev;
                this.current = this.current.prev;
            }
            this.length -= 1;
            Pulau curr = this;
//            out.println("pulau " + curr.namaPulau + " panjangnya " + curr.length);
            if (curr.prev != null) {
                while (curr.prev != null) {
                    curr = curr.prev;
                    curr.length -= 1;
//                    out.println("pulau " + curr.namaPulau + " panjangnya " + curr.length);
                }
            }
//                System.out.println("sekarang ada di sini " + this.current.tinggi);
            out.println(tinggiyghilang);
        }

        public void buatdaratan() {
            NodeDaratan daratan = this.current;
//            System.out.println("tadi ada di sini " + daratan.tinggi);
            NodeDaratan newDaratan;
            if (this.current.namaKuil != null) {
                out.println(0);
                return;
            }
            if (this.current.prev.tinggi < daratan.tinggi) {
                newDaratan = new NodeDaratan(this.current.prev.tinggi);
            } else {
                newDaratan = new NodeDaratan(daratan.tinggi);
            }
            if (this.current.next != null) {
                newDaratan.next = this.current.next;
                newDaratan.next.prev = newDaratan;
                newDaratan.prev = this.current;
                this.current.next = newDaratan;
            }
            else {
                this.current.next = newDaratan;
                newDaratan.prev = this.current;
                this.tail = newDaratan;
            }
            this.length += 1;
            Pulau curr = this;
//            out.println("pulau " + curr.namaPulau + " panjangnya " + curr.length);
            if (curr.prev != null) {
                while (curr.prev != null) {
                    curr = curr.prev;
                    curr.length += 1;
//                    out.println("pulau " + curr.namaPulau + " panjangnya " + curr.length);
                }
            }
            out.println(this.current.next.tinggi);
        }

        public int banjirbandang(int tinggi) {
            NodeDaratan kepala = this.head;
            int count = 0;
            if (kepala.next != null) {
                while(kepala.next != null) {
                    if (kepala.tinggi < tinggi) {
//                        out.println(kepala.tinggi + " < " + tinggi);
                        count++;
                    } kepala = kepala.next;
                }
            }
            if (kepala.tinggi < tinggi) {
//                out.println(kepala.tinggi + " < " + tinggi);
                count += 1;
            }
            return count;
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
//            out.println("add daratannya pulau " + pulauBaru.namaPulau);
            for (int j = 0; j < jumlahDaratan; j++) {
                int tinggi = in.nextInt();
                pulauBaru.addDaratan(tinggi);
//                out.println("add daratan ke-" + j);
                if (pulauBaru.length == 1) {
//                    out.println("lagi add kuil");
                    pulauBaru.head.addKuil(namaPulau);
                    mapKuil.put(namaPulau, pulauBaru);
                }
            }
        }

        String pulauRaiden = in.next();
        int daratanKe = in.nextInt();
        Pulau tempatRaiden = mapPulau.get(pulauRaiden);
        NodeDaratan raidendisinibos = tempatRaiden.head;
        for (int i = 0; i < tempatRaiden.length; i++) {
            if (i != daratanKe - 1) {
                raidendisinibos = raidendisinibos.next;
            } else {
                tempatRaiden.current = raidendisinibos;
                break;

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
//                    out.println("mau gabungin pulau " + pulaujoin.namaPulau + " di pulau " +yangdijoinin.namaPulau);
//                    out.println("-------UNFIKASI------");
                    pulaujoin.gabunginDaratan(yangdijoinin);
                    pulaujoin.gabunginPulau(yangdijoinin);
                    break;
                }

                case "PISAH": {
                    String gerakanseparatis = in.next();
//                    out.println("mau misahin " + gerakanseparatis);
                    Pulau adayangmakar = mapKuil.get(gerakanseparatis);
//                    System.out.println(adayangmakar.namaPulau);
//                    out.println("------PISAH------");
                    adayangmakar.separatismeDaratan();
                    adayangmakar.separatismePulau();
                    break;
                }

                case "GERAK": {
                    String gerakkemana = in.next();
                    int geraksejauhapa = in.nextInt();
                    for (int j = 0; j < geraksejauhapa; j++) {
                        switch (gerakkemana) {
                            case "KIRI": {
                                tempatRaiden.kekirimanise();
                                break;
                            }
                            case "KANAN": {
                                tempatRaiden.kekananmanise();
                                break;
                            }
                        }
                    }
                    out.println(tempatRaiden.current.tinggi);
                    break;
                }

                case "TEBAS": {
                    String senggolkemana = in.next();
                    int senggolberapapulau = in.nextInt();
                    long patokan = tempatRaiden.current.tinggi;
                    NodeDaratan raiden = null;
                    NodeDaratan move = tempatRaiden.current;
                    NodeDaratan beforemove;
                    for (int j = 0; j < senggolberapapulau;) {
                        if (senggolkemana.equals("KIRI")) {
                            beforemove = tempatRaiden.current;
                            tempatRaiden.kekirimanise();
                            if (tempatRaiden.current.tinggi == patokan && tempatRaiden.current != beforemove) {
                                raiden = tempatRaiden.current;
                                j += 1;
                            }
                            if (tempatRaiden.current.prev == null) {
                                break;
                            }
                        } else {
                            beforemove = tempatRaiden.current;
                            tempatRaiden.kekananmanise();
                            if (tempatRaiden.current.tinggi == patokan && tempatRaiden.current != beforemove) {
//                                System.out.println("here");
                                raiden = tempatRaiden.current;
                                j += 1;
                            }
                            if (tempatRaiden.current.next == null) {
                                break;
                            }
                        }
                    }
                    if (raiden == null) {
                        tempatRaiden.current = move;
                        out.println(0);
                    }
                    else {
                        tempatRaiden.current = raiden;
                        if (senggolkemana.equals("KIRI"))
                            out.println(tempatRaiden.current.next.tinggi);
                        else
                            out.println(tempatRaiden.current.prev.tinggi);
                    }
                    break;
                }

                case "TELEPORTASI": {
                    String teleportkekuil = in.next();
                    tempatRaiden.current = null;
                    tempatRaiden = mapKuil.get(teleportkekuil);
                    tempatRaiden.current = tempatRaiden.head;
                    out.println(tempatRaiden.current.tinggi);
                    break;
                }
                case "RISE": {
                    String pulaunaik = in.next();
                    int datarannaik = in.nextInt();
                    int naiksebesar = in.nextInt();
                    Pulau pulaunya = mapPulau.get(pulaunaik);
//                    out.println("lagi rise " + pulaunaik);
                    out.println(pulaunya.bikingunung(datarannaik, naiksebesar));
                    break;
                }
                case "QUAKE": {
                    String pulauturun = in.next();
                    int dataranturun = in.nextInt();
                    int turunsebesar = in.nextInt();
                    Pulau pulaunya = mapPulau.get(pulauturun);
//                    out.println("lagi quake " + pulauturun);
                    out.println(pulaunya.bikinlembah(dataranturun, turunsebesar));
                    break;
                }
                case "CRUMBLE": {
                    tempatRaiden.hancurinbos();
                    break;
                }
                case "STABILIZE": {
                    tempatRaiden.buatdaratan();
                    break;
                }
                case "SWEEPING": {
                    String pulautenggelem = in.next();
                    int tinggibanjir = in.nextInt();
                    Pulau pulau = mapPulau.get(pulautenggelem);
                    out.println(pulau.banjirbandang(tinggibanjir));
                    break;

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