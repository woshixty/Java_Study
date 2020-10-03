package dome_2;

import java.util.ArrayList;

public class Database {

    private ArrayList<Item> listItem = new ArrayList<Item>();

    public void add(Item item) {
        listItem.add(item);
    }

    public void list() {
        for (Item item : listItem) {
            item.print();
            System.out.println();
            System.out.println(item.toString());
        }
//		此地存疑，若要调用父类函数该如何
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Database db = new Database();
        db.add(new CD("abc", 10, "...", "JK.roling", 30));
        db.add(new DVD("efg", 120, "...", "SH.Grand"));
        db.list();
    }

}
