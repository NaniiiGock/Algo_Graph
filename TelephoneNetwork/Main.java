// find the shortest way for safe houses
//then we will add the dangerous houses to the net
// possible only if graph will be (зв'язний)

// if we have no safe houses:
    //1 dangerous -> 0
    // more then 2 -> impossible

// also impossible if the only way to add dangerous home is to
// add it to another dangerous home(if one is a leaf of the another dangerous)
//
// @-----@-----& where @ - dangerous and & - save

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int[] general_list;

    // methods to implement Kruskal algorithm
    static int Rep(int n) {
        while (n != general_list[n]) {
            n = general_list[n];
        }
        return n;
    }
    static boolean isConnected(int x, int y) {
        x = Rep(x);
        y = Rep(y);
        if (x == y) {
            return false;
        }
        general_list[y] = x;
        return true;
    }


    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
//        reading input data
        int n = scanner.nextInt(); // number of houses
        int m = scanner.nextInt(); // number of direct connections
        int p = scanner.nextInt(); // number of dangerous houses

//        list for houses
        general_list = new int[n+1];
        for(int i = 1; i <= n; i++)
            general_list[i] = i;

//        reading dangerous houses
        int[] dangerous = new int[n+1];
        for (int i = 0; i < p; i++) {
            int u = scanner.nextInt();
//            assigning the maximum value
//            that will be changed to the minimum one
            dangerous[u] = Integer.MAX_VALUE;
        }


//        finding the closest safe house to the dangerous one
        ArrayList<Edge> e = new ArrayList<>();
        int lenn = 0;
        for(int i = 0; i < m; i++)
        {
            int u = scanner.nextInt(); // first node
            int v = scanner.nextInt(); // second node
            lenn = scanner.nextInt(); // length of edge

//            finding the minimum distance from dangerous houses
//            to the safe ones
//            we find this distances for (u, v),
//            where one of the nodes is for dangerous house
//            and one for the safe
            if ((dangerous[u] > 0) && (dangerous[v] == 0)) {
                dangerous[u] = Math.min(dangerous[u], lenn);
            }
            if ((dangerous[v] > 0) && (dangerous[u] == 0)) {
                dangerous[v] = Math.min(dangerous[v], lenn);
            }
            if ((dangerous[u] == 0) && (dangerous[v] == 0)) {
                e.add(new Edge(u, v, lenn));
            }
        }

//        general check:
//        if graph is not containing edges -> 0
        if (m == 0) {
            System.out.println("0");
            return;
        }

//        if only 1 edge (2 houses) -> current distance
        if (m == 1) {
            System.out.println(lenn);
            return;
        }

//        if more than 2 houses:
//        sorting edges(only for safe houses now)
        e.sort(new comparator());

//        find the spanning tree by using Kruskal algorithm
        long numOfEdges = 0;
        long spanningLenght = 0;

        for (Edge edge:e){
            if (isConnected(edge.u, edge.v)) {
                spanningLenght += edge.lens;
                numOfEdges+=1;
            }
        }

//        adding dangerous houses
        for (int i = 0; i <= n-1; i++) {
            if (dangerous[i+1] > 0) {
                spanningLenght += dangerous[i];
                numOfEdges+=1;
            }
        }

//        checking the possibility of creating the net
        if (numOfEdges != n-1) {
                System.out.println("impossible");
            }
//        if exists dangerous house with no safe one to be connected with
        if (spanningLenght > Integer.MAX_VALUE) {
            System.out.println("impossible");
        }
        else {
            System.out.println(spanningLenght);
        }
    }
}