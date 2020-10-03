package test;

import practice.Practice_1;

public class Test_2_1 {

    public int age;
    public String name;
    public char cup;

    public Test_2_1(int age, String name, char cup) {
        // TODO Auto-generated constructor stub
        this.age = age;
        this.name = name;
        this.cup = cup;
    }

    public void print() {
        System.out.println(age);
        System.out.println(name);
        System.out.println(cup);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Practice_1 p1 = new Practice_1(12.1, 13.3);
        p1.showSize();
    }

}
