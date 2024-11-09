package ee.taltech.iti0202.sportsclub.users;

import java.util.UUID;

/**
 * Represents a user in the sports club system.
 */
public abstract class User {
    private UUID id;
    private String name;

    /**
     * Constructs a new user with the given name and generates a unique ID.
     *
     * @param name The name of the user.
     */
    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    /**
     * Retrieves the unique ID of the user.
     *
     * @return The ID of the user.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }
}
