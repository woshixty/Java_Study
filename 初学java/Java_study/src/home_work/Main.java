package home_work;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Fraction a = new Fraction(in.nextInt(), in.nextInt());
        Fraction b = new Fraction(in.nextInt(), in.nextInt());
        a.print();
        b.print();
        a.plus(b).print();
        a.multiply(b).plus(new Fraction(5, 6)).print();
        a.print();
        b.print();
        in.close();
    }
}

class Fraction {

    int i;
    int j;
    double h;

    Fraction(int a, int b) {
        i = a;
        j = b;
    }

    double toDouble() {
        h = (double) i / j;
        return h;
    }

    void print() {
        if (i == j) {
            System.out.println(1);
            return;
        }
        int t = MCF(i, j);
        i /= t;
        j /= t;
        System.out.println(i + "/" + j);
    }

    int MCF(int a, int b) {
        int c;
        c = a % b;
        if (c == 0)
            return b;
        return MCF(Math.abs(a - b), a % b);
    }

    Fraction plus(Fraction r) {
        Fraction t = new Fraction(this.i * r.j + this.j * r.i, this.j * r.j);
        return t;
    }

    Fraction multiply(Fraction r) {
        Fraction t = new Fraction(this.i * r.i, this.j * r.j);
        return t;
    }
}


