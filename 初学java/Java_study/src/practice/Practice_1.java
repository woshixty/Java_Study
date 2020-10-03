package practice;

public class Practice_1 {

    public double Longth;
    public double Width;

    public Practice_1(double Longth, double Width) {
        // TODO Auto-generated constructor stub
        this.Longth = Longth;
        this.Width = Width;
    }

    public double getPerimeter() {
        return (Longth + Width) * 2;
    }

    public double getArea() {
        return Longth * Width;
    }

    public void showSize() {
        System.out.println(Longth + " " + Width);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
