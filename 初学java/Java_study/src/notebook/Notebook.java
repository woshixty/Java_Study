package notebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Notebook {

    //	泛型类 Arraylist
    private ArrayList<String> notes = new ArrayList<String>();

    public void add(String s) {
        notes.add(s);
    }

    public int getSize() {
        return notes.size();
    }

    public String getNote(int index) {
        return notes.get(index);
    }

    public void removeNote(int index) {
        notes.remove(index);
    }

    public String[] list() {
        String[] a = new String[notes.size()];
        notes.toArray(a);
        return a;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Notebook nb = new Notebook();
        nb.add("first");
        nb.add("second");
        Scanner in = new Scanner(System.in);
        nb.add(in.nextLine());
        System.out.println(nb.getSize());
        in.close();
        System.out.println("---------------");

//		hash 相当于一个集合，无重复关系；
        HashSet<String> s = new HashSet<String>();
        s.add("XTY");
        s.add("WX");
        s.add("XTY");
        for (String k : s) {
            System.out.println(k);
        }
        System.out.println(s);
    }

}
