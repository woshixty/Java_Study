package homework_2;

public class Truck extends Vehicles {

    public float load;

    public Truck(String brand, String color, int id, float load) {
        super(brand, color, id);
        this.load = load;
    }

    public void showTruck() {
        System.out.println("This is a truck can " + load + " kg");
        showInfo();
    }

    public static void main(String[] args) {

    }

}
