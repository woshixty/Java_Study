package dome_2;

public class DVD extends Item {
    public String director;


    public DVD(String title, int playingTime, String comment, String director) {
        super(title, playingTime, "DVD", comment);
        this.director = director;
    }

    @Override
    public void print() {
        System.out.print(what + ":" + director + " " + title + " " + playingTime);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
