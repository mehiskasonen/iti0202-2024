import ee.taltech.iti0202.spotify.Playlist;
import ee.taltech.iti0202.spotify.Song;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Playlist playlist = new Playlist("Playlist");
        Song song1 = new Song("Down with the sickness", "Disturbed", 0);
        //Song song2 = new Song("Down with the negatives", "Undisturbed", -2);

        playlist.addSong(song1);
        //playlist.addSong(song2);

        //System.out.println(song1);
        System.out.println(playlist.getDurationOfPlaylist());
    }
}