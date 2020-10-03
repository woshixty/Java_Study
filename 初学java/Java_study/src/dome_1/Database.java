package dome_1;

import java.util.ArrayList;

public class Database {
    private ArrayList<CD> listcd = new ArrayList<CD>();
    private ArrayList<DVD> listdvd = new ArrayList<DVD>();

    public void add(CD cd) {
        listcd.add(cd);
    }

    public void add(DVD dvd) {
        listdvd.add(dvd);
    }

    public void list() {
        for (CD cd : listcd)
            cd.print();
        for (DVD dvd : listdvd)
            dvd.print();
    }


    public static void main(String[] args) {
        Database db = new Database();
        db.add(new CD("abc", "abc", 10, 5, "..."));
        db.add(new CD("efg", "efg", 4, 3, "..."));
        db.add(new DVD("xxx", "david", 120, "......."));
        db.list();
    }

}
