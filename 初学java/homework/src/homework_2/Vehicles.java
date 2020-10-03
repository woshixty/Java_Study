package homework_2;

public class Vehicles {
    public String brand;
    public String color;
    public int id;

    public void run() {
        int i;
        for (i = 0; i < 50; i++)
            System.out.print('#');
        System.out.println("100%");
        System.out.println("启动成功");
    }

    public Vehicles(String brand, String color, int id) {
        this.brand = brand;
        this.color = color;
        this.id = id;
    }

    public void showInfo() {
        System.out.println("The brand is " + brand + ". The color is " + color);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Car V1 = new Car("BMW", "red", 1, 4);
        Truck V2 = new Truck("GTL", "black", 2, 1000.5f);
        V1.showCar();
        V1.run();
        V2.showTruck();
        V2.run();
    }
}
