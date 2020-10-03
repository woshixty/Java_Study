package dome_2;

public class CD extends Item {

    public String artist;
    public int numofTracks;


    public CD(String title, int playingTime, String comment, String artist, int numofTracks) {
        super(title, playingTime, "CD", comment);
        this.artist = artist;
        this.numofTracks = numofTracks;
    }

    public void print() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
