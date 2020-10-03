package dome_2;

public class Item {

    @Override
    public String toString() {
        return "Item [title=" + title + ", playingTime=" + playingTime + ", gotIt=" + gotIt + ", what=" + what
                + ", comment=" + comment + "]";
    }

    public String title;
    public int playingTime;
    public boolean gotIt = false;
    public String what;

    public Item(String title, int playingTime, String what, String comment) {
        super();
        this.title = title;
        this.playingTime = playingTime;
        this.what = what;
        this.comment = comment;
    }

    public String comment;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

    public void print() {
        System.out.println("AHH");
    }

}
