import java.io.*;
import java.util.*;

class lab5udah5 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static AVLTree tree = new AVLTree();
    public static Map<String, Node> mapValue = new HashMap<>();
    public static Map<String, Integer> mapType = new HashMap<>();

    public static void main(String[] args) {

        //Menginisialisasi kotak sebanyak N
        int N = in.nextInt();
        for(int i = 0; i < N; i++){
            String nama = in.next();
            int harga = in.nextInt();
            int tipe = in.nextInt();
            handleStock(nama, harga, tipe);
        }

        //Query
        //(method dan argumennya boleh diatur sendiri, sesuai kebutuhan)
        int NQ = in.nextInt();
        for(int i = 0; i < NQ; i++){
            String Q = in.next();
            if (Q.equals("BELI")){
                int L = in.nextInt();
                int R = in.nextInt();
                out.println(handleBeli(L, R));

            }else if(Q.equals("STOCK")){
                String nama = in.next();
                int harga = in.nextInt();
                int tipe = in.nextInt();
                handleStock(nama, harga, tipe);

            }else{ //SOLD_OUT
                String nama = in.next();
                handleSoldOut(nama);

            }
        }

        out.flush();
    }

    //TODO
    static String handleBeli(int L, int R){
        Node left = tree.root;
        Node right = tree.root;
        if (left.cost < L || right.cost > R) {
            return "-1 -1";
        }
        if (L == left.cost && R == right.cost && tree.root.listOfType.size() == 1) {
            System.out.println("here");
            return "-1 -1";
        }
        else {
            int max = tree.ceiling(tree.root, R);
            int min = tree.floor(tree.root, L);
            String maxStr = Integer.toString(max);
            String minStr = Integer.toString(min);
            return minStr + " " + maxStr;
        }
    }

    //TODO
    static void handleStock(String name, int cost, int type){
        tree.root = tree.insertHelper(tree.root, name, cost, type);
//        mapValue.put(name, tree.root);
        mapType.put(name, type);
//        tree.printPreorder(tree.root);
//        System.out.println();
    }

    //TODO
    static void handleSoldOut(String name){
        Node node = mapValue.get(name);
        if (node.listOfName.size() > 1) {
            node.listOfName.remove(name);
            int type = mapType.get(name);
            node.listOfType.remove(Integer.valueOf(type));
        } else {
            int cost = node.cost;
            int type = mapType.get(name);
            tree.root = tree.deleteNode(tree.root, name, cost, type);
            node.listOfName.remove(name);
            node.listOfType.remove(Integer.valueOf(type));
        }
//        tree.printPreorder(tree.root);
//        System.out.println();
    }

    static class Node {
        int cost, height, type;
        String name;
        ArrayList<Integer> listOfType = new ArrayList<>();
        ArrayList<String> listOfName = new ArrayList<>();
        Node left, right;

        public Node(String name, int cost, int type) {
            this.cost = cost;
            this.height = 1;
            this.name = name;
            this.type = type;
            listOfType.add(type);
            listOfName.add(name);
        }
    }
    static class AVLTree {
        Node root;

        public AVLTree() {
            root = null;
        }

        public int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        public int balanceCheck(Node node) {
            if (node == null) {
                return 0;
            }
            return getHeight(node.left) - getHeight(node.right);
        }
        //i get this code from geeksforgeeks.com
        public Node rightRotation(Node node) {
            Node temp = node.left;
//            if (temp.right == null) {
//                temp.right = node;
//            } else {
                Node temp2 = temp.right;
                temp.right = node;
                node.left = temp2;
//            }

            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;
            return temp;
        }
        //i get this code from geeksforgeeks.com
        public Node leftRotation(Node node) {
            Node temp = node.right;
//            if (temp.left == null) {
//                temp.left = node;
//            } else {
                Node temp2 = temp.left;
                temp.left = node;
                node.right = temp2;
//            }

            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;
            return temp;
        }
        //i get this code from https://www.geeksforgeeks.org/avl-tree-set-1-insertion/?ref=lbp with some changes
        public Node insertHelper(Node node, String name, int cost, int type) {
            if (node == null) {
                Node newNode = new Node(name, cost, type);
                mapValue.put(name, newNode);
                return newNode;
            } else if (cost < node.cost) {
                node.left = insertHelper(node.left, name, cost, type);
            } else if (cost > node.cost){
                node.right = insertHelper(node.right, name, cost, type);
            } else {
                node.listOfName.add(name);
//                System.out.println(!node.listOfType.contains(type));
                if (!node.listOfType.contains(type)) {
                    node.listOfType.add(type);
                }
                mapValue.put(name, node);
            }
            node.height = Math.max(getHeight(node.right), getHeight(node.left)) + 1;

            int checkBalance = balanceCheck(node);

            if (checkBalance > 1 && cost < node.left.cost) {
                Node right = rightRotation(node);
                return right;
            }

            if (checkBalance < -1 && cost > node.right.cost) {
                Node left = leftRotation(node);
                return left;
            }

            if (checkBalance > 1 && cost > node.left.cost) {
                node.left = leftRotation(node.left);
                Node right = rightRotation(node);
                return right;
            }

            if (checkBalance < -1 && cost < node.right.cost) {
                node.right = rightRotation(node.right);
                Node left = leftRotation(node);
                return left;
            }
            return node;
        }

        public Node minimalValueNode(Node node) {
            Node current = node;

            while (current.left != null) {
                current = current.left;
            }
            return current;
        }
        //i get this code from https://www.geeksforgeeks.org/avl-tree-set-2-deletion/?ref=lbp with some changes
        public Node deleteNode(Node node, String name, int cost, int type) {
            if (node == null) {
                return null;
            } if (cost < node.cost) {
                node.left = deleteNode(node.left, name, cost, type);
            } else if (cost > node.cost) {
                node.right = deleteNode(node.right, name, cost, type);
            } else {
                if ((node.left == null) || (node.right == null)) {
                    Node temp = null;

                    if (node.left == null) {
                        temp = node.right;
                    } else {
                        temp = node.left;
                    }
                    if (temp == null) {
                        temp = node;
                        node = null;
                    }
                    else {
                        node = temp;
                    }
                }
                else {
                    Node temp = minimalValueNode(node.right);
                    node.cost = temp.cost;
                    node.name = temp.name;
                    node.type = temp.type;
                    node.listOfName = temp.listOfName;
                    node.listOfType = temp.listOfType;

                    node.right = deleteNode(node.right, node.right.name, node.right.cost, node.right.type);
                }
            }

            if (node == null) {
                return null;
            }

            root.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

            int checkBalance = balanceCheck(node);

            if (checkBalance > 1 && balanceCheck(node.left) >= 0) {
                return rightRotation(node);
            }

            if (checkBalance < -1 && balanceCheck(node.right) <= 0) {
                return leftRotation(node);
            }

            if (checkBalance > 1 && balanceCheck(node.left) < 0) {
                node.left = leftRotation(node.left);
                return rightRotation(node);
            }

            if (checkBalance < -1 && balanceCheck(node.right) > 0) {
                node.right = rightRotation(node.right);
                return leftRotation(node);
            }
            return node;
        }
        //I get this code from https://www.geeksforgeeks.org/largest-number-bst-less-equal-n/ with some changes
        public int ceiling(Node node, int cost) {
            if (node == null) {
                return -1;
            }

            if (node.cost == cost) {
                return node.cost;
            }
            else if (node.cost < cost) {
                int newInt = ceiling(node.right, cost);
                if (newInt == -1) {
                    return node.cost;
                }
                else {
                    return newInt;
                }
            } else if (node.cost > cost) {
                return ceiling(node.left, cost);
            }
            return -1;
        }
        //I get this code from https://www.geeksforgeeks.org/floor-in-binary-search-tree-bst/ with some changes
        public int floor(Node node, int cost) {
            int smallest = 0;
            while (node != null) {
                if (node.cost >= cost) {
                    smallest = node.cost;
                    node = node.left;
                }
                else {
                    node = node.right;
                }
            }
            return smallest;
        }

        //for debugging purposes
        void printPreorder(Node node) {
            if (node == null)
                return;

            System.out.print(node.cost + " ");

            printPreorder(node.left);

            printPreorder(node.right);
        }
    }


    // taken from https://codeforces.com/submissions/Petr
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