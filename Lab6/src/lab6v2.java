import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class lab6v2 {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        List<Integer> tanah = new ArrayList<>();
        List<Integer> heap = new ArrayList<>();

        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int height = in.nextInt();
            tanah.add(height);
            heap.add(height);

            int size = heap.size() - 1;
//            System.out.println("ini size: " + size);
            while (size != 0 && heap.get(parent(size)) > heap.get(size)) {
                int temp = heap.get(size);
//                System.out.println("ini temp: " + temp);
//                System.out.println("ini parentsize: " + parent(size));
                heap.set(size, heap.get(parent(size)));
                heap.set(parent(size), temp);
                size = parent(size);
//                System.out.println("size = parent(size); hasilnya: " + size);
            }
        }
        System.out.print("heap: ");
        printHeap(heap);
        System.out.print("tanah: ");
        printHeap(tanah);
        int Q = in.nextInt();
        while(Q-- > 0) {
            String query = in.next();
            if (query.equals("A")) {
                // TODO: Handle query A
                int daratanBaru = in.nextInt();
                tanah.add(daratanBaru);
                heap.add(daratanBaru);

                int size = heap.size() - 1;
                while(size != 0 && heap.get(parent(size)) > heap.get(size)) {
                    int temp = heap.get(size);
                    heap.set(size, heap.get(parent(size)));
                    heap.set(parent(size), temp);
                    size = parent(size);
                }
                System.out.print("heap: ");
                printHeap(heap);
                System.out.print("tanah: ");
                printHeap(tanah);
            } else if (query.equals("U")) {
                // TODO: Handle query U
                int index = in.nextInt();
                int tinggi = in.nextInt();
                int curr = tanah.set(index, tinggi);
                for (int k = 0; k < heap.size(); k++) {
                    if (heap.get(k) == curr) {
                        heap.set(k, tinggi);
//                        System.out.println(heap.get(k));
                        if (heap.get(parent(k)) > heap.get(k)) {
                            while (heap.get(parent(k)) > heap.get(k)) {
//                                System.out.println("index: " + k);
//                                System.out.println("parent: " + heap.get(parent(k)));
//                                System.out.println("current: " + heap.get(k));
                                int temp = heap.get(k);
                                heap.set(k, heap.get(parent(k)));
                                heap.set(parent(k), temp);
                                k = parent(k);
                            }
                        } else {
                            minHeapify(k, heap);
                        }
                        break;
//                        System.out.println("parentnya " + tinggi + " adalah " + heap.get(parent(k)));
                    }
                }
                System.out.print("heap: ");
                printHeap(heap);
                System.out.print("tanah: ");
                printHeap(tanah);
            } else {
                // TODO: Handle query R
                int curr = heap.get(0);
                int index = tanah.indexOf(curr);
//                System.out.println(index + " " + curr);
                ArrayList<Integer> change = new ArrayList<>(2);
                int patokan = 0;
                if (index == 0) {
                    if (heap.size() - 1 > 0) {
                        patokan = Math.max(tanah.get(index), tanah.get(index + 1));
//                        System.out.println("patokan: " + patokan);
                        change.add(tanah.set(index, patokan));
                        change.add(tanah.set(index + 1, patokan));
                    } else {
                        patokan = tanah.get(index);
                        change.add(tanah.set(index, tanah.get(index)));
                    }
                } else if (index > 0 && index < heap.size() - 1) {
                    int tinggibefore = tanah.get(index-1);
                    int tingginow = tanah.get(index);
                    int tingginext = tanah.get(index+1);
                    patokan = Math.max(tinggibefore, Math.max(tingginext, tingginow));
                    if (patokan == tinggibefore) {
                        change.add(tanah.set(index, patokan));
                        change.add(tanah.set(index+1,patokan));
                    } else if (patokan == tingginow) {
                        change.add(tanah.set(index-1, patokan));
                        change.add(tanah.set(index+1,patokan));
                    } else {
                        change.add(tanah.set(index-1, patokan));
                        change.add(tanah.set(index, patokan));
                    }
                } else {
                    patokan = Math.max(tanah.get(index), tanah.get(index-1));
                    change.add(tanah.set(index-1, patokan));
                    change.add(tanah.set(index, patokan));
                }
//                System.out.println(change);
                for (int i = 0; i < change.size(); i++) {
                    int j = change.get(i);
//                    System.out.println("change i: " + j);
                    for (int k = 0; k < heap.size(); k++) {
                        if (heap.get(k) == j) {
                            heap.set(k, patokan);
                            if (heap.get(parent(k)) > heap.get(k)) {
                                while (heap.get(parent(k)) > heap.get(k)) {
                                    int temp = heap.get(k);
                                    heap.set(k, heap.get(parent(k)));
                                    heap.set(parent(k), temp);
                                    k = parent(k);
                                }
                            } else {
                                minHeapify(k, heap);
                            }
                            break;
                        }
                    }
                }
                System.out.print("heap: ");
                printHeap(heap);
                System.out.print("tanah: ");
                printHeap(tanah);
                out.println(patokan + " " + index);
            }
        }

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    public static int parent(int i) {
        return (i - 1)/2;
    }

    public static int leftChild(int i) {
        return 2 * i + 1;
    }

    public static int rightChild(int i) {
        return 2 * i + 2;
    }

    public static void minHeapify(int k, List<Integer> heap) {
        int left = leftChild(k);
//        System.out.println("leftchild " + left);
        int right = rightChild(k);
//        System.out.println("rightchild " + right);
        int smallest = k;
        if (left <= heap.size() - 1 && heap.get(left) < heap.get(smallest)) {
//            System.out.println("smallest = left");
            smallest = left;
        }
        if (right <= heap.size() - 1 && heap.get(right) < heap.get(smallest)) {
//            System.out.println("smallest = right");
            smallest = right;
        }
        if (smallest != k) {
            int temp = heap.get(k);
            heap.set(k, heap.get(smallest));
            heap.set(smallest, temp);
            minHeapify(smallest, heap);
        }
    }


    public static void printHeap(List<Integer> heap) {
        for (int i = 0; i < heap.size(); i++) {
            System.out.print(heap.get(i) + " ");
        }
        System.out.println();
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

    }
}