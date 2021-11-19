import java.io.*;
import java.util.*;

public class lab5 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static AVLTree tree = new AVLTree();
    public static Map<String, Node> mapValue = new HashMap<>();
    public static Map<String, Integer> mapType = new HashMap<>();
    public static Map<Integer, Node> mapCost = new HashMap<>();

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
        Node max = tree.ceiling(tree.root, R);
        Node min = tree.floor(tree.root, L);
        if (max == null || min == null) {
//            System.out.println("masuk ke null");
            return "-1 -1";
        }
//        System.out.println(max.cost);
//        System.out.println("maxcost " + max.cost + " " + max.listOfType);
//        System.out.println("mincost " + min.cost + " " + min.listOfType);
        if ((min.cost < L) && (max.cost > R)) {
//            System.out.println("masuk ke mincost < L maxcost > R");
            return "-1 -1";
        }
        System.out.println("here: " + min.cost + " " + max.cost);
        if ((min.cost > max.cost)) {
//            System.out.println("masuk ke mincost > maxcost");
            return "-1 -1";
        }
//        System.out.println(max.setOfType + " " + min.setOfType);
        while ((max.type == min.type)) {
            int maxMin = -1;
            int maxAdd = -1;
            if (max.setOfType.size() > 1 || min.setOfType.size() > 1) {
                String maxStr = Integer.toString(max.cost);
                String minStr = Integer.toString(min.cost);
                return minStr + " " + maxStr;
            }
            else if (tree.root.cost == max.cost && tree.root.cost == min.cost){
//                System.out.println("tipe=tipe");
                return "-1 -1";
            }
            else {
                min = tree.floor(tree.root, min.cost+1);
                if (min == null) {
                    return "-1 -1";
                }
                if (min.cost > max.cost) {
                    return "-1 -1";
                }
            }

        }
        String maxStr = Integer.toString(max.cost);
        String minStr = Integer.toString(min.cost);
        return minStr + " " + maxStr;
    }

    //TODO
    static void handleStock(String name, int cost, int type){
        tree.root = tree.insertHelper(tree.root, name, cost, type);
//        mapValue.put(name, tree.root);
        mapType.put(name, type);
        tree.printPreorder(tree.root);
        System.out.println();
    }

    //TODO
    static void handleSoldOut(String name){
        Node node = mapValue.get(name);
        if (node.listOfName.size() > 1) {
            node.listOfName.remove(name);
            node.name = node.listOfName.get(0);
            int type = mapType.get(name);
            node.listOfType.remove(Integer.valueOf(type));
            if (!node.listOfType.contains(type)) {
                node.setOfType.remove(type);
            }
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
        int cost, height;
        int type;
        String name;
        ArrayList<Integer> listOfType = new ArrayList<>();
        ArrayList<String> listOfName = new ArrayList<>();
        Set<Integer> setOfType =new HashSet<>();
        Node left, right;

        public Node(String name, int cost, int type) {
            this.cost = cost;
            this.height = 1;
            this.name = name;
            this.type = type;
            listOfType.add(type);
            listOfName.add(name);
            setOfType.add(type);
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
                mapCost.put(cost, newNode);
                return newNode;
            } else if (cost < node.cost) {
                node.left = insertHelper(node.left, name, cost, type);
            } else if (cost > node.cost){
                node.right = insertHelper(node.right, name, cost, type);
            } else {
                node.listOfName.add(name);
//                System.out.println(!node.listOfType.contains(type))
//                System.out.println(type);
                node.listOfType.add(type);
                node.setOfType.add(type);
                mapValue.put(name, node);
                mapCost.put(cost, node);
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
                // STEP 1: PERFORM STANDARD BST DELETE
                if (node == null)
                    return node;

                // If the key to be deleted is smaller than the root's key,
                // then it lies in left subtree
                if (cost < node.cost)
                    node.left = deleteNode(node.left, name, cost, type);

                    // If the key to be deleted is greater than the root's key,
                    // then it lies in right subtree
                else if (cost > node.cost)
                    node.right = deleteNode(node.right, name, cost, type);

                    // if key is same as root's key, then This is the node
                    // to be deleted
                else {
                    // If key is present more than once, simply decrement
                    // count and return

                    if (node.listOfName.size() > 1) {
//                        System.out.println("masuk sini");
                        int types = mapType.get(name);
//                        System.out.println(name);
//                        System.out.println("ini nodenya: " + node.cost);
//                        System.out.println("before: " + node.listOfType);
                        node.listOfType.remove(Integer.valueOf(types));
//                        System.out.println("after: " + node.listOfType);
//                        System.out.println("settypes: " + node.setOfType);
//                        System.out.println("ini types: " + types);
//                        System.out.println(node.listOfType.contains(types));
                        if (node.listOfType.contains(types)) {
                            return node;
                        } else {
                            node.setOfType.remove(types);
                        }
                        return null;
                    }
                    // ElSE, delete the node

                    // node with only one child or no child
                    if ((node.left == null) || (node.right == null)) {
                        Node temp = node.left != null ? node.left : node.right;

                        // No child case
                        if (temp == null) {
                            temp = node;
                            node = null;
                        }
                        else // One child case
                            node = temp; // Copy the contents of the non-empty child
                    }
                    else {
                        // node with two children: Get the inorder successor (smallest
                        // in the right subtree)
                        Node temp = minimalValueNode(node.right);

                        // Copy the inorder successor's data to this node and update the count
                        node.cost = temp.cost;
                        node.listOfType = temp.listOfType;
                        node.listOfName = temp.listOfName;
                        node.setOfType = temp.setOfType;
                        node.name = temp.name;
                        node.type = temp.type;
                        // Delete the inorder successor
                        node.right = deleteNode(node.right, name, temp.cost, type);
                    }
                }

            if (node == null) {
                return null;
            }

            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

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
        public Node ceiling(Node node, int cost) {
            if (node == null) {
                return null;
            }

            if (node.cost == cost) {
                return node;
            }
            else if (node.cost < cost) {
                Node newInt = ceiling(node.right, cost);
                if (newInt == null) {
                    return node;
                }
                else {
                    return newInt;
                }
            } else if (node.cost > cost) {
                return ceiling(node.left, cost);
            }
            return null;
        }
        //I get this code from https://www.geeksforgeeks.org/smallest-number-in-bst-which-is-greater-than-or-equal-to-n/
        private Node floor(Node root, int cost) {
            Node smallest = null;
            while (root != null)
            {
                if (root.cost >= cost)
                {
                    smallest = root;
                    root = root.left;
                }
                else {
                    root = root.right;
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