package ee.taltech.iti0202.socialnetwork.message;

import ee.taltech.iti0202.socialnetwork.user.User;


public class Message {
    private final String title;
    private final String message;
    private final User author;

    /**
     * Overriding the toString() method
     * @return message in string form.
     */
    @Override
    public String toString() {
        return message + " ";
    }

    /**
     * Constructor method for Message.
     */
    public Message(String title, String message, User author) {
        this.title = title;
        this.message = message;
        this.author = author;
    }

    /**
     * Return title from a message object.
     * @return title of message.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return message object.
     * @return message object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return author from a message object.
     * @return author of message.
     */
    public User getAuthor() {
        return author;
    }
}
