package ee.taltech.iti0202.socialnetwork.user;

import ee.taltech.iti0202.socialnetwork.group.Group;

import java.util.HashSet;
import java.util.Set;

public class User {

    private final String name;
    private Integer age;

    private Set<Group> userGroups = new HashSet<>();

    /**
     * Overriding the toString() method.
     * @return name in string form.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Constructor method for User, if the user also has age.
     */
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.userGroups = new HashSet<>();

    }

    /**
     * Ocerriding constructor method for User, when user has no age.
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Get name of user.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get age of user if user has made age public.
     * @return age
     */
    public Integer getAge() {
        if (age == null) {
            return null;
        }
        return age;
    }

    /**
     * Adds a group to the set of user groups.
     * @param group object.
     */
    public void addGroup(Group group) {
        userGroups.add(group);
    }

    /**
     * Removes a group from users set of groups they are no longer a member of.
     * @param group object.
     */
    public void removeGroup(Group group) {
        userGroups.remove(group);
    }

    /**
     * @return Set of groups where the given user belongs to.
     */
    public Set<Group> getGroups() {
        return userGroups;
    }
}
