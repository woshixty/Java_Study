package homework_2;

public class Car extends Vehicles {
    private int seats;

    public Car(String brand, String color, int id, int seats) {
        super(brand, color, id);
        this.seats = seats;
    }

    public void showCar() {
        System.out.println("This is a car with " + seats + " seats");
        showInfo();
    }

    public static void main(String[] args) {
    }
}
