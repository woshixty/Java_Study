package inherit;

public class Father_1 {

    private String name;
    private int id;

    public Father_1(String myName, int myid) {
        name = myName;
        id = myid;
    }

    public void eat() {
        System.out.println(name + " " + "Eating now!");
    }

    public void sleep() {
        System.out.println(name + " " + "Sleeping now!");
    }

    public void introduction() {
        System.out.println("Hello everyone, I'm No." + id + " " + name + ".");
    }

    public static void main(String[] args) {
        Son_1 s1 = new Son_1("mouse", 1);
        Son_2 s2 = new Son_2("penguin", 2);
        s1.eat();
        s2.sleep();
    }

}
