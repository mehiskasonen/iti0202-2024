package ee.taltech.iti0202.spotify;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class User {

    private final List<Playlist> playlists;

    public User() {
        this.playlists = new ArrayList<>();
    }

    /**
     * Add playlist to users list of playlists, ff user doesn't have a playlist with the exact name.
     * Comparison is case-insensitive.
     * @param playlist to add to users list of playlists.
     */
    public void addPlaylist(Playlist playlist) {
        boolean canAdd = true;
        for (Playlist pl : playlists) {
            if (Objects.equals(pl.getName().toLowerCase(), playlist.getName().toLowerCase())) {
                canAdd = false;
                break;
            }
        }
        if (canAdd) {
            playlists.add(playlist);
        }
    }

    /**
     * Adds song to playlist, if user has given playlist.
     * @param song to add to playlist.
     * @param playlist to add the song to.
     */
    public void addSongToPlaylist(Song song, Playlist playlist) {
        if (playlists.contains(playlist)) {
            for (Playlist pl : playlists) {
                if (Objects.equals(pl.getName().toLowerCase(), playlist.getName().toLowerCase())) {
                    pl.addSong(song);
                }
            }
        }
    }

    /**
     * Return list of songs in playlist, if user has given playlist.
     * @param playlist to get songs from.
     * @return list of songs
     * @throws NoSuchElementException if user does not have given playlist.
     */
    public List<Song> getPlaylist(Playlist playlist) throws NoSuchElementException {
        if (playlists.contains(playlist)) {
            List<Song> playlistSongs = new ArrayList<>();
            for (Playlist pl : playlists) {
                if (Objects.equals(pl.getName().toLowerCase(), playlist.getName().toLowerCase())) {
                    playlistSongs.addAll(pl.getSongs());
                }
            }
            return playlistSongs;
        }
        throw new NoSuchElementException();
    }

    /**
     * @return users playlists.
     */
    public List<Playlist> getPlaylists() {
        return playlists;
    }
}