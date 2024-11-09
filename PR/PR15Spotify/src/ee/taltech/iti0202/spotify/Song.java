package ee.taltech.iti0202.spotify;

public record Song(String title, String artist, int duration) {

    /**
     * @return string representing the song in the format: "[title] by [artist]"
     */
    @Override
    public String toString() {
        return "\"" + title + "\" by " + artist;
    }

    /**
     * @return title of the song.
     */
    @Override
    public String title() {
        return title;
    }

    /**
     * @return artist of the song.
     */
    @Override
    public String artist() {
        return artist;
    }

    /**
     * @return duration of the song in seconds.
     */
    @Override
    public int duration() {
        return duration;
    }
}
