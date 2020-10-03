package hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HashTable {

    private HashMap<Integer, String> coinnames = new HashMap<Integer, String>();

    public HashTable() {
        coinnames.put(50, "half-dollar");
        coinnames.put(1, "penny");
        coinnames.put(25, "quater");
        coinnames.put(10, "dime");
        System.out.println(coinnames.keySet());
        System.out.println(coinnames);
        ArrayList<String> names = new ArrayList<String>();
        for (Integer k : coinnames.keySet()) {
            names.add(coinnames.get(k));
            System.out.println(coinnames.get(k));
            System.out.println(names);
        }
    }

    public String getName(int amount) {
        if (coinnames.containsKey(amount))
            return coinnames.get(amount);
        else
            return ("人家没有啦");
//		switch (amount) {
//		case 10:
//			return "dime";
//		}
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner in = new Scanner(System.in);
        HashTable coin = new HashTable();
        System.out.println(coin.getName(in.nextInt()));
        in.close();
        ;
    }

}
