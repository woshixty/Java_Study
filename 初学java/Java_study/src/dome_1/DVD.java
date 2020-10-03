package dome_1;

public class DVD {

    private String title;
    private String director;
    private int playingTime;
    private boolean gotIt = false;
    private String comment;

    public DVD(String title, String direction, int playingTime, String comment) {
        super();
        this.title = title;
        this.director = direction;
        this.playingTime = playingTime;
        this.comment = comment;
    }

    public void print() {
        System.out.println("DVD:" + title + ":" + director);
    }

    public static void main(String[] args) {

    }

}
