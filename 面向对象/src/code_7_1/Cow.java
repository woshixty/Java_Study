package code_7_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/7
 **/
public class Cow {
    private double weight;
    //外部类的构造器
    public Cow(double weight) { this.weight = weight; }
    //定义一个非静态内部类
    private class CowLeg {
        //非静态内部类的两个实例变量
        private double length;
        private String color;
        //内部类的构造器
        public CowLeg(double length, String color) {
            this.length = length;
            this.color = color;
        }
        //非静态内部类的实例方法
        public void info() {
            System.out.println("牛腿颜色为：" + color);
            System.out.println("牛高：" + length);
            //直接访问外部类private修饰的成员变量
            System.out.println("奶牛重：" + weight);
        }
    }
    public void test() {
        var cl = new CowLeg(1.12, "黑白相间");
        cl.info();
    }
    public static void main(String[] args) {
        var cow = new Cow(378.9);
        cow.test();
    }
}
