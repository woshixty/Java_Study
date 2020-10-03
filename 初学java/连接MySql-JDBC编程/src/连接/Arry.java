package 连接;

public class Arry {
    public Arry(String name, int money, String shopcar, int id) {
        super();
        this.name = name;
        this.money = money;
        this.shopcar = shopcar;
        this.id = id;
    }

    private String name;
    private int money;
    private String shopcar;
    private int id;

    @Override
    public String toString() {
        return "容器 [name=" + name + ", money=" + money + ", shopcar=" + shopcar + ", id=" + id + "]";
    }

    public String getName() {
        return name;
    }


    public int getMoney() {
        return money;
    }

    public String getShopcar() {
        return shopcar;
    }

    public int getId() {
        return id;
    }

}
