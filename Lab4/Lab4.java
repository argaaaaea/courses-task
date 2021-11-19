import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

// TODO - class untuk Lantai
class Lantai {
    public String nama;
    public Lantai next;
    public Lantai previous;

    public Lantai() {
        this.nama = null;
        this.next = null;
        this.previous = null;
    }

    public String getValue(){
        return this.nama;
    }

}


// TODO - class untuk Gedung
class Gedung {
    public String nama;
    public Lantai a;
    public int length;
    public Lantai head;
    public Lantai tail;

    public Gedung(String nama) {
        a = new Lantai();
        this.nama = nama;
        this.length = 0;
        this.head = a;
        this.tail = a;
    }

//    public Lantai getFirst() {
//        Lantai b = a;
//        while(b.previous != null && b.previous.nama != null){
//            b = b.previous;
//        }
//        return b;
//    }
//
//    public Lantai getLast() {
//        Lantai b = a;
//        while (b.next != null && b.next.nama != null) {
//            b = b.next;
//        }
//        return b;
//    }

    public void bangun(String input){
        // TODO - handle BANGUN
        // Untuk membuat lantai baru dengan next yang tidak ditentukan
        Lantai newLantai = new Lantai();
        newLantai.nama = input;

        if (length == 0) {
            newLantai.previous = null;
            newLantai.next = null;
            a = newLantai;
            this.head = newLantai;
            this.tail = newLantai;
        } else if (a.next == null){
            a.next = newLantai;
            newLantai.previous = a;
            a = newLantai;
            this.tail = a;
        } else {
            newLantai.next = a.next;
            a.next.previous = newLantai;
            a.next = newLantai;
            newLantai.previous = a;
            a = newLantai;
        }
        length++;
//        System.out.println(length);
    }

    public void lift(String input){
        // TODO - handle LIFT
        if (input.equals("BAWAH")) {
            if (a.previous != null && a.previous.nama != null){
                a = a.previous;
            }
        }
        else {
            if (a.next != null && a.next.nama != null) {
                a = a.next;
            }
        }
    }

    public void hancurkan(){
        // TODO - handle HANCURKAN
//        System.out.println(length);
//        System.out.println(length);
        if (length == 1){
            a.previous = null;
            a.next = null;
            a.nama = null;
        }
        else if (a.next == null) {
            a.nama = null;
            a = a.previous;
            if (a.next.nama == null) {
                this.tail = a;
            }
            a.next = null;

        } else if (a.previous != null) {
            a.previous.next = a.next;
            a.next.previous = a.previous;
            a.nama = null;
            a = a.previous;

        } else {
            a.nama = null;
            a = a.next;
            if (a.previous.nama == null) {
                this.head = a;
            }
            a.previous = null;
        }
        length--;
    }

    public void timpa(String input, Map<String, Gedung> banyakGedung){
        // TODO - handle TIMPA
        Gedung curr = banyakGedung.get(input);
        Lantai lantaiGedungIni = this.tail;
        Lantai lantaiGedungSebelah = curr.head;
        lantaiGedungIni.next = lantaiGedungSebelah;
        lantaiGedungSebelah.previous = lantaiGedungIni;
        if (lantaiGedungIni.next.nama != null && lantaiGedungSebelah.previous.nama != null){
            this.tail = curr.tail;
//            System.out.println(lantaiGedungIni.next.nama);
//            System.out.println(lantaiGedungSebelah.previous.nama);
            length += curr.length;
        }
    }

    public StringBuilder sketsa(){
        // TODO - handle SKETSA
        Lantai lantaiGedungIni = this.head;
        StringBuilder mauNgeprint = new StringBuilder();
        if (length == 0){
            mauNgeprint.append("");
        } else {
            while (lantaiGedungIni != null && lantaiGedungIni.nama != null) {
                mauNgeprint.append(lantaiGedungIni.nama);
                lantaiGedungIni = lantaiGedungIni.next;
            }
        }
        mauNgeprint.append("\n");
        return mauNgeprint;
    }
}

public class Lab4 {
    private static InputReader in;
    public static PrintWriter out;
    public static Gedung Gedung;
    public static Map<String, Gedung> banyakGedung = new HashMap<String, Gedung>();
    public static StringBuilder printGeming = new StringBuilder();
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // N operations
        int N = in.nextInt();
        String cmd;

        // TODO - handle inputs
        for (int zz = 0; zz < N; zz++) {

            cmd = in.next();


            if(cmd.equals("FONDASI")){
                String A = in.next();
                Gedung gedungBaru = new Gedung(A);
                banyakGedung.put(A, gedungBaru);
            }
            else if(cmd.equals("BANGUN")){
                String A = in.next();
                String X = in.next();
                // TODO
//                System.out.println("Bangun " + A + " " + X);
                Gedung gedungGue = banyakGedung.get(A);
                gedungGue.bangun(X);
            }
            else if(cmd.equals("LIFT")){
                String A = in.next();
                String X = in.next();
                // TODO
                Gedung gedungGue = banyakGedung.get(A);
                gedungGue.lift(X);
//                System.out.println("LIFT " + A + " " + X + " " +gedungGue.a.nama);
                printGeming.append(gedungGue.a.nama);
                printGeming.append("\n");
            }
            else if(cmd.equals("SKETSA")){
                String A = in.next();
                // TODO
                Gedung gedungGue = banyakGedung.get(A);
//                System.out.println("sketsa " + A + " " + baru);
                printGeming.append(gedungGue.sketsa());

            }
            else if(cmd.equals("TIMPA")){
                String A = in.next();
                String B = in.next();
                // TODO
                Gedung gedungGue = banyakGedung.get(A);
                gedungGue.timpa(B, banyakGedung);

            }
            else if(cmd.equals("HANCURKAN")){
                String A = in.next();
                // TODO
                Gedung gedungGue = banyakGedung.get(A);
//                System.out.println("hancurkan " + A + " " + gedungGue.a.nama);
                printGeming.append(gedungGue.a.nama);
                printGeming.append("\n");
                gedungGue.hancurkan();
            }
        }

        // don't forget to close/flush the output
        out.print(printGeming);
        out.close();
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