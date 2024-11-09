package ee.taltech.iti0202.spotify;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Playlist {
    private final String name;
    private final List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    /**
     * Adds a song to playlist if a song with exactly same title and artist is already not it the playlist
     * comparison is case-insensitive.
     * @param song to be added to playlist.
     */
    public void addSong(Song song) {
        boolean canAdd = true;
        for (Song entry : songs) {
            if (Objects.equals(entry.title().toLowerCase(), song.title().toLowerCase())
                    && Objects.equals(entry.artist().toLowerCase(), song.artist().toLowerCase())) {
                canAdd = false;
                break;
            }
        }
        if (canAdd) {
            songs.add(song);
        }
    }

    /**
     * @param song to be removed from playlist.
     */
    public void removeSong(Song song) {
        songs.remove(song);
    }

    /**
     * @param artist who's songs to return.
     * @return all such songs from playlist, that has the given author (comparison is case-insensitive).
     */
    public List<Song> getSongsByArtist(String artist) {
        List<Song> result = new ArrayList<>();
        for (Song song : songs) {
            if (song.artist().equalsIgnoreCase(artist)) {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * @return playlist that is sorted by songs titles in alphabetical order.
     */
    public List<Song> sortSongsAlphabetically() {
        songs.sort(Comparator.comparing(Song::title));
        return songs;
    }

    /**
     * @return song which length is the greatest. If there are more than one such, return any of them.
     */
    public Song getLongestSong() {
        if (songs.isEmpty()) {
            return null;
        }
        songs.sort(Comparator.comparing(Song::duration).reversed());
        return songs.get(0);
    }

    /**
     * Returns the whole length of the playlist. Length must be returned as a string, that has
     * been formatted to hours (h), minutes (m) and seconds (s) according to need.
     * If any of the previously mentioned time units would return 0, then it is not used.
     * If playlist length is 37 seconds, return must be 37s.
     * If playlist length is 120 seconds, return must be 2m (not 2m 0s!).
     * If playlist length is 7215 seconds, return must be 2h 15s.
     * @return string representing length.
     */
    public String getDurationOfPlaylist() {
        if (songs.isEmpty()) {
            return "0s";
        }
        int playListDuration = songs.stream().mapToInt(Song::duration).sum();
        int durationHours = playListDuration / 3600;
        int durationMinutes = (playListDuration % 3600) / 60;
        int durationSeconds = playListDuration % 60;
        StringBuilder returnString = new StringBuilder();
        if (durationHours > 0) {
            returnString.append(durationHours).append("h ");
        }
        if (durationMinutes > 0 ) {
            returnString.append(durationMinutes).append("m ");
        }
        if (durationSeconds > 0) {
            returnString.append(durationSeconds).append("s");
        }
        return returnString.toString().trim();
    }

    /**
     * @return all the songs in playlist.
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * @return name of playlist.
     */
    public String getName() {
        return name;
    }

}
