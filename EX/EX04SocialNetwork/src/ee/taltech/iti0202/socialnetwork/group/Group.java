package ee.taltech.iti0202.socialnetwork.group;
import ee.taltech.iti0202.socialnetwork.message.Message;
import ee.taltech.iti0202.socialnetwork.user.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Group {
    private String name;
    private User owner;
    private List<User> participants;

    private Set<User> bannedUsers = new HashSet<>();

    private List<Message> messages = new LinkedList<>();

    /**
     * Constructor method for Group.
     */
    public Group(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.participants = new ArrayList<>();
        this.participants.add(owner);
        owner.addGroup(this);
    }

    /**
     * Get group name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Change group name
     * @param name of existing group.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return owner of a group.
     * @return owner object.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set the group owner.
     * If the user is not part of the group, they can not be set as the owner.
     * @param user of existing group.
     */
    public void setOwner(User user) {
        if (participants.contains(user)) {
            owner = user;
        }
    }

    /**
     * Add user to set of participants.
     */
    public void addUser(User user) {
        if (!participants.contains(user)) {
            if (!bannedUsers.contains(user)) {
                participants.add(user);
                user.addGroup(this);
            }
        }
    }

    /**
     * Remove user from set of participants.
     */
    public void removeUser(User user) {
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            if (msg.getAuthor().equals(user)) {
                iterator.remove();
            }
        }
        if (this.owner.equals(user)) {
            if (this.getParticipants().size() == 1) {
                this.owner = null;
            } else {
                this.owner = this.getParticipants().get(1);
            }
        }
        this.participants.remove(user);
        user.removeGroup(this);
    }

    /**
     * A member who is banned from a group can not be added back to the group.
     * A member can be banned from the group even if they are not a member of it.
     * @param user to be banned.
     */
    public void banUser(User user) {
        if (participants.contains(user)) {
            removeUser(user);
        }
        bannedUsers.add(user);
    }

    /**
     * @return all users banned from a given group.
     */
    public Set<User> getBannedUsers() {
        return bannedUsers;
    }

    /**
     * Return all participants(users) from a group.
     * @return List of users.
     */
    public List<User> getParticipants() {
        return participants;
    }

    /**
     * Method for adding a message.
     * Only messages that have been written by group members can be added.
     * @param message object.
     */
    public void publishMessage(Message message) {
        //Message(String title, String message, User author)
        if (participants.contains(message.getAuthor())) {
            messages.add(message);
        }
    }

    /**
     * Method to get all messages.
     * @return list of messages.
     */
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return name;
    }
}
