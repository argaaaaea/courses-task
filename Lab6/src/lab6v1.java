import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class lab6v1 {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        HashMap<Integer, Node> urutan = new HashMap<>(); //ngurut
        List<Node> heap = new ArrayList<>(); //heap

        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int height = in.nextInt();
            Node newNode = new Node(height, i, i);
            urutan.put(i ,newNode);
            heap.add(newNode);

            int size = heap.size() - 1;
//            System.out.println("ini size: " + size);
            while (size != 0 && heap.get(parent(size)).tinggi > heap.get(size).tinggi) {
                Node temp = heap.get(size);
//                System.out.println("ini temp: " + temp);
//                System.out.println("ini parentsize: " + parent(size));
                heap.set(size, heap.get(parent(size)));
                heap.set(parent(size), temp);
                heap.get(parent(size)).urutanHeap = parent(size);
                heap.get(size).urutanHeap = size;
                size = parent(size);
//                System.out.println("size = parent(size); hasilnya: " + size);
            }
        }

//        System.out.print("tanah: ");
//        urutan.forEach((key, value) -> System.out.print(value.tinggi + " "));
//        System.out.print("heap: ");
//        printHeap(heap);
//        System.out.println();

//        printHeap(tanah);
        int Q = in.nextInt();
        while(Q-- > 0) {
            String query = in.next();
            if (query.equals("A")) {
                // TODO: Handle query A
                int daratanBaru = in.nextInt();
                Node newNode = new Node(daratanBaru, heap.size(), heap.size());
                urutan.put(urutan.size(), newNode);
                heap.add(newNode);

                int size = heap.size() - 1;
                while(size != 0 && heap.get(parent(size)).tinggi > heap.get(size).tinggi) {
                    Node temp = heap.get(size);
                    heap.set(size, heap.get(parent(size)));
                    heap.set(parent(size), temp);
                    heap.get(size).urutanHeap = size;
                    heap.get(parent(size)).urutanHeap = parent(size);
                    size = parent(size);
                }
//                System.out.print("heap: ");
//                printHeap(heap);
//                System.out.print("tanah: ");
//                urutan.forEach((key, value) -> System.out.print(value.tinggi + " "));
//                System.out.println();

//                printHeap(tanah);
            } else if (query.equals("U")) {
                // TODO: Handle query U
                int index = in.nextInt();
                int tinggi = in.nextInt();
                Node aowkowk = urutan.get(index);
                int tinggiDiUbah= aowkowk.tinggi;
                aowkowk.tinggi = tinggi;
                int k = aowkowk.urutanHeap;
//                System.out.println("urutan" + aowkowk.urutan);
//                System.out.println("urutanheap: " + k);
                if (tinggi != tinggiDiUbah) {
                    if (heap.get(parent(k)).tinggi >= heap.get(k).tinggi && k != 0) {
                        while (heap.get(parent(k)).tinggi >= heap.get(k).tinggi && k != 0) {
//                      System.out.println("index: " + k);
//                      System.out.println("parent: " + heap.get(parent(k)));
//                      System.out.println("current: " + heap.get(k));
                            Node temp = heap.get(k);
                            heap.set(k, heap.get(parent(k)));
                            heap.set(parent(k), temp);
                            heap.get(k).urutanHeap = k;
                            heap.get(parent(k)).urutanHeap = parent(k);
//                        System.out.println("parent: " + heap.get(parent(k)).tinggi);
//                        System.out.println("current: " + heap.get(k).tinggi);
                            while (heap.get(parent(k)).tinggi == heap.get(k).tinggi) {
//                                System.out.println("masuk kesini");
                                if (heap.get(parent(k)).urutan >= heap.get(k).urutan && k != 0) {
//                                    System.out.println("parent: " + heap.get(parent(k)).tinggi);
//                                    System.out.println("child: " + heap.get(k).tinggi);
                                    Node temp2 = heap.get(k);
                                    heap.set(k, heap.get(parent(k)));
                                    heap.set(parent(k), temp2);
                                    heap.get(k).urutanHeap = k;
                                    heap.get(parent(k)).urutanHeap = parent(k);
                                    k = parent(k);
                                }
                            }
                            k = parent(k);
                        }
                    } else {
//                    System.out.println("here");
                        minHeapify(k, heap);
                    }
                }
//                        System.out.println("parentnya " + tinggi + " adalah " + heap.get(parent(k)));
//
//                System.out.print("heap: ");
//                printHeap(heap);
//                System.out.print("tanah: ");
//                urutan.forEach((key, value) -> System.out.print(value.tinggi + " "));
//                System.out.println();

//                printHeap(tanah);
            } else {
                // TODO: Handle query R
                Node curr = heap.get(0);
                int index = curr.urutan;
//                System.out.println("ini index: " + index);
//                System.out.println("ini index di awal: " + index);
                int patokan = 0;
//                System.out.println("currurutan: " + index);
//                System.out.println(index + " " + curr);
                ArrayList<Integer> change = new ArrayList<>(2);
//                ArrayList<Node> beforeChange = new ArrayList<>();
//                System.out.println("ini index: " + index);
//                System.out.println("ini index: " + index);
                if (index == 0) {
                    if (heap.size() - 1 > 0) {
                        patokan = Math.max(urutan.get(index).tinggi, urutan.get(index + 1).tinggi);
//                        System.out.println("patokan: " + patokan);
                        change.add(urutan.get(index).urutan);
                        change.add(urutan.get(index + 1).urutan);
//                        beforeChange.add(urutan.get(index));
//                        beforeChange.add(urutan.get(index+1));
//                        urutan.get(index).tinggi = patokan;
//                        urutan.get(index+1).tinggi = patokan;
                    } else {
                        patokan = urutan.get(index).tinggi;
                        change.add(urutan.get(index).urutan);
//                        beforeChange.add(urutan.get(index));
//                        urutan.get(index).tinggi = patokan;
                    }
                } else if (index > 0 && index < heap.size() - 1) {
                    int tinggibefore = urutan.get(index-1).tinggi;
                    int tingginow = urutan.get(index).tinggi;
                    int tingginext = urutan.get(index+1).tinggi;
                    patokan = Math.max(tinggibefore, Math.max(tingginext, tingginow));
                    if (patokan == tinggibefore) {
//                        System.out.println("Masuk ke patokan == tinggibefore");
//                        System.out.println("patokan: " + patokan);
                        change.add(urutan.get(index).urutan);
                        change.add(urutan.get(index+1).urutan);
//                        beforeChange.add(urutan.get(index));
//                        beforeChange.add(urutan.get(index+1));
//                        urutan.get(index).tinggi = patokan;
//                        urutan.get(index+1).tinggi = patokan;
                    } else if (patokan == tingginow) {
//                        System.out.println("Masuk ke patokan == tingginow");
                        change.add(urutan.get(index-1).urutan);
                        change.add(urutan.get(index+1).urutan);
//                        beforeChange.add(urutan.get(index-1));
//                        beforeChange.add(urutan.get(index+1));
//                        urutan.get(index-1).tinggi = patokan;
//                        urutan.get(index).tinggi = patokan;
                    } else {
//                        System.out.println("Masuk ke patokan == tingginext");
                        change.add(urutan.get(index-1).urutan);
                        change.add(urutan.get(index).urutan);
//                        beforeChange.add(urutan.get(index-1));
//                        beforeChange.add(urutan.get(index));
//                        urutan.get(index-1).tinggi = patokan;
//                        urutan.get(index).tinggi = patokan;
                    }
                }
                else {
//                    System.out.println("masuk ke sini");
                    patokan = Math.max(urutan.get(index).tinggi, urutan.get(index-1).tinggi);
                    change.add(urutan.get(index-1).urutan);
                    change.add(urutan.get(index).urutan);
//                    beforeChange.add(urutan.get(index-1));
//                    beforeChange.add(urutan.get(index));
//                    urutan.get(index-1).tinggi = patokan;
//                    urutan.get(index).tinggi = patokan;
                }
//                System.out.println("ini patokan: " + patokan);
                for (int i = 0; i < change.size(); i++) {
                    int index1 = change.get(i);
                    Node currentNode = urutan.get(index1);
                    int tinggiDiUbah = currentNode.tinggi;
                    int current = currentNode.urutanHeap;
                    int parent = parent(current);
                    currentNode.tinggi = patokan;
//                    System.out.println(" diubah jadi: " + heap.get(parent).tinggi);
//                    System.out.println("parentnya: " + heap.get(parent).tinggi);
//                    System.out.println("tuker ke atas?" + (parent > heap.get(current).tinggi));
//                   System.out.println("change i: " + j);
//                    System.out.println("ini current: " + current);
//                    System.out.println("parent dari " + tinggiDiUbah + " adalah " + heap.get(parent).tinggi);
//                    System.out.println(current != 0);
                    if (patokan != tinggiDiUbah) {
                        if ((heap.get(parent).tinggi >= heap.get(current).tinggi) && (current != 0)) {
                            while (heap.get(parent).tinggi >= heap.get(current).tinggi && current != 0) {
//                            System.out.println("aokwowkaowk");
//                            System.out.println("cek ke atas");
//                      System.out.println("index: " + k);
//                      System.out.println("parent: " + heap.get(parent(k)));
//                      System.out.println("current: " + heap.get(k));
                                Node temp = heap.get(current);
                                heap.set(current, heap.get(parent(current)));
                                heap.set(parent(current), temp);
                                heap.get(current).urutanHeap = current;
                                heap.get(parent(current)).urutanHeap = parent(current);
//                            printHeap(heap);
                                while (heap.get(parent).tinggi == heap.get(current).tinggi) {
//                                System.out.println("orang tua anak kembar");
                                    if (heap.get(parent).urutan >= heap.get(current).urutan && current != 0) {
//                                    System.out.println("Orang tua lebih besar dari anak");
                                        Node temp2 = heap.get(current);
                                        heap.set(current, heap.get(parent(current)));
                                        heap.set(parent(current), temp2);
                                        heap.get(current).urutanHeap = current;
                                        heap.get(parent(current)).urutanHeap = parent(current);
                                        current = parent(current);
                                        parent = parent(current);
                                    }
                                }
                                current = parent(current);
                                parent = parent(current);
                            }
                        } else {
//                        System.out.println("masojfsa");
                            minHeapify(current, heap);
//                            printHeap(heap);
//                        System.out.print("heap: ");
//                        printHeap(heap);
                        }
                    }
                }
                if (index == 0) {
                    if (heap.size() - 1 > 0) {
                        patokan = Math.max(urutan.get(index).tinggi, urutan.get(index + 1).tinggi);
//                        System.out.println("patokan: " + patokan);
//                        change.add(urutan.get(index).urutan);
//                        change.add(urutan.get(index + 1).urutan);
//                        beforeChange.add(urutan.get(index));
//                        beforeChange.add(urutan.get(index+1));
                        urutan.get(index).tinggi = patokan;
                        urutan.get(index+1).tinggi = patokan;
                    } else {
                        patokan = urutan.get(index).tinggi;
//                        change.add(urutan.get(index).urutan);
//                        beforeChange.add(urutan.get(index));
                        urutan.get(index).tinggi = patokan;
                    }
                } else if (index > 0 && index < heap.size() - 1) {
                    int tinggibefore = urutan.get(index-1).tinggi;
                    int tingginow = urutan.get(index).tinggi;
                    int tingginext = urutan.get(index+1).tinggi;
                    patokan = Math.max(tinggibefore, Math.max(tingginext, tingginow));
                    if (patokan == tinggibefore) {
//                        change.add(urutan.get(index).urutan);
//                        change.add(urutan.get(index+1).urutan);
//                        beforeChange.add(urutan.get(index));
//                        beforeChange.add(urutan.get(index+1));
                        urutan.get(index).tinggi = patokan;
                        urutan.get(index+1).tinggi = patokan;
                    } else if (patokan == tingginow) {
//                        change.add(urutan.get(index-1).urutan);
//                        change.add(urutan.get(index+1).urutan);
//                        beforeChange.add(urutan.get(index-1));
//                        beforeChange.add(urutan.get(index+1));
                        urutan.get(index-1).tinggi = patokan;
                        urutan.get(index).tinggi = patokan;
                    } else {
//                        change.add(urutan.get(index-1).urutan);
//                        change.add(urutan.get(index).urutan);
//                        beforeChange.add(urutan.get(index-1));
//                        beforeChange.add(urutan.get(index));
                        urutan.get(index-1).tinggi = patokan;
                        urutan.get(index).tinggi = patokan;
                    }
                } else {
                    patokan = Math.max(urutan.get(index).tinggi, urutan.get(index-1).tinggi);
//                    change.add(urutan.get(index-1).urutan);
//                    change.add(urutan.get(index).urutan);
//                    beforeChange.add(urutan.get(index-1));
//                    beforeChange.add(urutan.get(index));
                    urutan.get(index-1).tinggi = patokan;
                    urutan.get(index).tinggi = patokan;
                }
//                System.out.print("heap: ");
//                printHeap(heap);
//                System.out.print("tanah: ");
//                urutan.forEach((key, value) -> System.out.print(value.tinggi + " "));
//                System.out.println();


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

    public static void minHeapify(int k, List<Node> heap) {
        int left = leftChild(k);
//        System.out.println("leftchild " + left);
        int right = rightChild(k);
//        System.out.println("rightchild " + right);
        int smallest = k;
//        System.out.println("parent tingginya " + heap.get(k).tinggi);
//        System.out.println(left <= heap.size() - 1);

        if ((right <= heap.size() - 1) && heap.get(left).tinggi == heap.get(right).tinggi && heap.get(smallest).tinggi > heap.get(left).tinggi) {
//            System.out.println("anaknya kembar");
            if (heap.get(left).urutan < heap.get(right).urutan) {
                smallest = left;
            } else {
                smallest = right;
            }
        }

        if (right <= heap.size() - 1 && heap.get(right).tinggi == heap.get(left).tinggi && heap.get(right).tinggi == heap.get(smallest).tinggi) {
//            System.out.println("triangle");
            int current = smallest;
            if (heap.get(left).urutan < heap.get(right).urutan) {
//                System.out.println("Smallest = left;");
                smallest = left;
            } else {
//                System.out.println("Smallest = right;");
                smallest = right;
            }
            if (heap.get(current).urutan < heap.get(smallest).urutan) {
//                System.out.println("smallest = current;");
                smallest = current;
            }
        }

        else if (right <= heap.size() - 1 && heap.get(right).tinggi == heap.get(smallest).tinggi) {
//            System.out.println("orang tua sama dengan anak kanan");
            if (heap.get(right).urutan < heap.get(smallest).urutan) {
                smallest = right;
            }
        }

        else if (left <= heap.size() - 1 && heap.get(left).tinggi == heap.get(smallest).tinggi) {
//            System.out.println("orang tua sama dengan anak kiri");
            if (heap.get(left).urutan < heap.get(smallest).urutan) {
                smallest = left;
            }
        }

        if (left <= heap.size() - 1 && heap.get(left).tinggi < heap.get(smallest).tinggi) {
//            System.out.println("anak kiri di index ke-" + left + " tingginya " + heap.get(left).tinggi);System.out.println(heap.get(left).tinggi < heap.get(smallest).tinggi);
//            System.out.println("smallest = left");
            smallest = left;
        }
        if (right <= heap.size() - 1 && heap.get(right).tinggi < heap.get(smallest).tinggi) {
//            System.out.println("smallest = right");
            smallest = right;
        }

        if (smallest != k) {
//            System.out.println("nuker " + heap.get(k).tinggi + " sama " + heap.get(smallest).tinggi);
            Node temp = heap.get(k);
            heap.set(k, heap.get(smallest));
            heap.set(smallest, temp);
            heap.get(k).urutanHeap = k;
            heap.get(smallest).urutanHeap = smallest;
//            System.out.println(heap.get(k).tinggi + " urutannya " + heap.get(k).urutanHeap);
            minHeapify(smallest, heap);
        }
    }


    public static void printHeap(List<Node> heap) {
        for (int i = 0; i < heap.size(); i++) {
            System.out.print(heap.get(i).tinggi + "(" +heap.get(i).urutan + ")"  + " ");
        }
        System.out.println();
    }

    public static class Node {
        int tinggi;
        int urutan;
        int urutanHeap;

        public Node(int tinggi, int urutan, int urutanHeap) {
            this.tinggi = tinggi;
            this.urutan = urutan;
            this.urutanHeap = urutanHeap;
        }
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