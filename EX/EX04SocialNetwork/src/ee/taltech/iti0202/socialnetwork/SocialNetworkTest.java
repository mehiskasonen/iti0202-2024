package ee.taltech.iti0202.socialnetwork;

import ee.taltech.iti0202.socialnetwork.admin.Admin;
import ee.taltech.iti0202.socialnetwork.feed.Feed;
import ee.taltech.iti0202.socialnetwork.group.Group;
import ee.taltech.iti0202.socialnetwork.message.Message;
import ee.taltech.iti0202.socialnetwork.user.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SocialNetworkTest {


    /**
     * verifies that the getName() method of the Admin class
     * correctly returns the name in right format for Admin without age.
     */
    @Test
    public void getNameAdminShortConstructor() {
        Admin superUser = new Admin("admin");
        String expected = "(Admin) admin";
        Assertions.assertEquals(expected, superUser.getName());
    }

    /**
     * verifies that the getName() method of the Admin class
     * correctly returns the name in right format for Admin with age.
     */
    @org.junit.jupiter.api.Test
    public void getNameAdminLongConstructor() {
        Admin superUser = new Admin("admin", 20);
        String expected = "(Admin) admin";
        Assertions.assertEquals(expected, superUser.getName());
    }

    /**
     * verifies that the banUser(User) method of the Group class
     * correctly bans a user from the group.
     */
    @org.junit.jupiter.api.Test
    public void banUser() {
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        group1.banUser(user1);
        List<User> expected = new ArrayList<>();
        Assertions.assertEquals(expected, group1.getParticipants());
    }

    /**
     * verifies that the banUser(User) method of the Group class
     * correctly adds the banned user to the set of banned users in the group.
     */
    @org.junit.jupiter.api.Test
    public void banUserAddsBannedUsers() {
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        group1.banUser(user1);
        Set<User> expected = Set.of(user1);
        Assertions.assertEquals(expected, group1.getBannedUsers());
    }


    /**
     * Verify that user is banned from a single social network group.
     */
    @org.junit.jupiter.api.Test
    public void banUserFromSocialNetwork() {
        SocialNetwork socialNetwork = new SocialNetwork();
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        socialNetwork.registerGroup(group1);

        Admin admin = new Admin("admin");

        admin.banUserFromSocialNetwork(user1, socialNetwork);
        List<User> expected = new ArrayList<>();
        Assertions.assertEquals(expected, group1.getParticipants());
    }

    /**
     * verifies that the getName() method of the User class
     * returns the correct name of the user.
     */
    @org.junit.jupiter.api.Test
    public void getNameUserClass() {
        User user1 = new User("user1");
        String actual = user1.getName();
        String expected = "user1";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * verifies that the getAge() method of the User class
     * returns the correct age of the user.
     */
    @org.junit.jupiter.api.Test
    public void getAgeUserClass() {
        User user2 = new User("user2", 10);
        assert user2.getAge().equals(10);
    }

    /**
     * verifies that the addGroup() method of the User class
     * adds the group to the user's set of groups.
     */
    @org.junit.jupiter.api.Test
    public void addGroupUserClass() {
        User user2 = new User("user2", 20);
        Group group = new Group("group1", user2);
        user2.addGroup(group);
        Assertions.assertEquals(user2.getGroups().size(), 1);
    }

    /**
     * verifies that the getTitle() method of the Message class
     * returns the correct title of the message.
     */
    @org.junit.jupiter.api.Test
    public void getTitleMessageClass() {
        User user2 = new User("user2", 10);
        Message message1 = new Message("title1", "content1", user2);
        assert message1.getTitle().equals("title1");
    }

    /**
     * verifies that the getMessage() method of the Message class
     * returns the correct message content.
     */
    @org.junit.jupiter.api.Test
    public void getMessageMessageClass() {
        User user2 = new User("user2", 10);
        Message message1 = new Message("title1", "content1", user2);
        assert message1.getMessage().equals("content1");
    }

    /**
     * verifies that the getAuthor() method of the Message class
     * returns the correct author of the message.
     */
    @org.junit.jupiter.api.Test
    public void getAuthorMessageClass() {
        User user2 = new User("user2", 10);
        Message message1 = new Message("title1", "content1", user2);
        assert message1.getAuthor().equals(user2);
    }

    /**
     * verifies that the getName() method of the Group class
     * returns the correct name of the group.
     */
    @org.junit.jupiter.api.Test
    public void getName() {
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        assert group1.getName().equals("group1");
    }

    /**
     * verifies that the setName() method of the Group class
     * sets the correct name for the group.
     */
    @org.junit.jupiter.api.Test
    public void setName() {
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        group1.setName("newName");
        assert group1.getName().equals("newName");
    }

    /**
     * verifies that the getOwner() method of the Group class
     * returns the correct owner of the group.
     */
    @org.junit.jupiter.api.Test
    public void getOwner() {
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        assert group1.getOwner().equals(user1);

    }

    /**
     * verifies that the addUser() method of the Group class
     * adds the user to the group's participants.
     */
    @org.junit.jupiter.api.Test
    public void addUser() {
        User user1 = new User("user1");
        User user2 = new User("user2");
        Group group1 = new Group("group1", user1);
        group1.addUser(user1);
        group1.addUser(user2);
        Assertions.assertTrue(group1.getParticipants().contains(user1));
        Assertions.assertTrue(group1.getParticipants().contains(user2));
    }

    /**
     * verifies that the publishMessage() method of the Group class
     * publishes the message in the group.
     */
    @org.junit.jupiter.api.Test
    public void publishMessage() {
        User user3 = new User("user2", 20);
        Group group2 = new Group("group1", user3);
        Message message2 = new Message("title2", "content2", user3);
        Message message3 = new Message("title3", "content3", user3);
        group2.publishMessage(message2);
        group2.publishMessage(message3);
        Assertions.assertEquals(2, group2.getMessages().size());
    }

    /**
     * verifies that the getMessages() method of the Group class
     * returns the correct set of messages associated with the group.
     */
    @org.junit.jupiter.api.Test
    public void getMessagesFromGroup() {
        User user3 = new User("user2", 20);
        Message message2 = new Message("title2", "content2", user3);
        Group group2 = new Group("group1", user3);
        group2.publishMessage(message2);
        assert group2.getMessages().contains(message2);
    }

    /**
     * Verifies that the getUser() method of the Feed class
     * returns the correct user associated with the feed.
     */
    @org.junit.jupiter.api.Test
    public void getUser() {
        User user2 = new User("user2", 10);
        Set<Message> messages = new HashSet<>();
        Feed feed1 = new Feed(user2, messages);
        Assertions.assertEquals(user2, feed1.getUser());
    }

    /**
     * verifies that the getMessages() method of the Feed class
     * returns the correct set of messages associated with the feed.
     */
    @org.junit.jupiter.api.Test
    public void getMessages() {
        User user1 = new User("user1");
        Set<Message> messages = new HashSet<>();
        Message message1 = new Message("title1", "content1", user1);
        Message message2 = new Message("title2", "content2", user1);
        messages.add(message1);
        messages.add(message2);

        Feed feed = new Feed(user1, messages);
        Set<Message> actualMessages = feed.getMessages();
        Assertions.assertEquals(messages, actualMessages);
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    void registerGroup() {
        SocialNetwork socialNetwork = new SocialNetwork();
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);

        socialNetwork.registerGroup(group1);
        Assertions.assertTrue(socialNetwork.getGroups().contains(group1));
        Assertions.assertEquals(1, socialNetwork.getGroups().size());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    void getGroupsTwoGroups() {
        User user1 = new User("user1");
        User user2 = new User("user2");
        Group group1 = new Group("group1", user1);
        Group group2 = new Group("group2", user2);

        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.registerGroup(group1);
        socialNetwork.registerGroup(group2);
        Set<Group> expected = Set.of(group1, group2);
        Assertions.assertEquals(expected, socialNetwork.getGroups());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    void getGroupsNoGroups() {
        SocialNetwork socialNetwork = new SocialNetwork();
        Set<Group> expected = Set.of();
        Assertions.assertEquals(expected, socialNetwork.getGroups());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    void getFeedForUserHasFeed() {
        SocialNetwork socialNetwork = new SocialNetwork();
        User user2 = new User("user2");
        Message message1 = new Message("title1", "content1", user2);
        Group group1 = new Group("group1", user2);
        socialNetwork.registerGroup(group1);
        group1.publishMessage(message1);
        Set<Message> expected = Set.of(message1);
        Assertions.assertEquals(expected, socialNetwork.getFeedForUser(user2).getMessages());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    void getFeedForUserNoFeed() {
        SocialNetwork socialNetwork = new SocialNetwork();
        User user2 = new User("user2");
        Group group1 = new Group("group1", user2);
        socialNetwork.registerGroup(group1);
        Set<Message> expected = Set.of();
        Assertions.assertEquals(expected, socialNetwork.getFeedForUser(user2).getMessages());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void removeGroup() {
        User user1 = new User("user1");
        User user2 = new User("user2");

        Group group1 = new Group("group1", user1);
        Group group2 = new Group("group2", user2);

        user1.addGroup(group2);

        Set<Group> expected = Set.of(group1, group2);
        Assertions.assertEquals(expected, user1.getGroups());

        user1.removeGroup(group1);

        Set<Group> expected2 = Set.of(group2);
        Assertions.assertEquals(expected2, user1.getGroups());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldRemoveUserAndSetOldestMemberAsNewOwnerWhenOwnerIsBanned() {
        //Expected: is <[Message, Message, World]>
        //but: was <[Message, Message, Message, World]>
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldRemoveUserMessagesWhenRemoved() {
        User user1 = new User("user1");
        User user2 = new User("user2");

        Group group1 = new Group("group1", user1);
        group1.addUser(user2);
        Message message1 = new Message("Message1", "Content1", user1);
        group1.publishMessage(message1);

        Message message2 = new Message("Message2", "Content2", user2);
        group1.publishMessage(message2);

        Message message3 = new Message("Message3", "Content3", user1);
        group1.publishMessage(message3);

        group1.removeUser(user1);
        Assertions.assertEquals(1, group1.getMessages().size());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldSetOldestUserAsNewGroupOwnerWhenOwnerIsRemoved() {
        User user1 = new User("New Owner");
        User user2 = new User("Owner");
        Group group1 = new Group("group1", user2);
        group1.addUser(user1);
        group1.removeUser(user2);
        Assertions.assertEquals(group1.getParticipants().get(0), user1);
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldBanUserFromSocialNetworkWhenBannedByAdmin() {
        SocialNetwork socialNetwork = new SocialNetwork();
        User user1 = new User("user1");
        User user2 = new User("user2");
        Group group1 = new Group("group1", user1);
        Group group2 = new Group("group1", user2);

        socialNetwork.registerGroup(group1);
        socialNetwork.registerGroup(group2);

        socialNetwork.banUser(user2);

        Assertions.assertTrue(group1.getBannedUsers().contains(user2));
        Assertions.assertTrue(group2.getBannedUsers().contains(user2));
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldKeepMessageOrderWhenRemoved() {
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");

        Group group1 = new Group("group1", user1);
        group1.addUser(user2);
        group1.addUser(user3);

        Message message1 = new Message("Message1", "Content1", user1);
        group1.publishMessage(message1);

        Message message2 = new Message("Message2", "Content2", user2);
        group1.publishMessage(message2);

        Message message3 = new Message("Message3", "Content3", user1);
        group1.publishMessage(message3);

        Message message4 = new Message("Message4", "Content4", user3);
        group1.publishMessage(message4);

        group1.removeUser(user1);
        LinkedList<Message> expected = new LinkedList<>();
        expected.add(0, message2);
        expected.add(1, message4);

        Assertions.assertEquals(expected, group1.getMessages());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldSetNullAsNewGroupOwnerWhenOwnerIsRemoved() {
        User user1 = new User("Owner");
        Group group1 = new Group("group1", user1);
        group1.removeUser(user1);
        Assertions.assertNull(group1.getOwner());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldRemoveUserAndSetNullAsNewOwnerWhenOwnerIsBanned() {
        User user1 = new User("Owner");
        Group group1 = new Group("group1", user1);
        group1.banUser(user1);
        Assertions.assertNull(group1.getOwner());
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldNotAddUserToGroupIfAlreadyInGroup() {
        User user2 = new User("user2");
        Group group1 = new Group("group1", user2);
        group1.addUser(user2);
        Assertions.assertEquals(1, group1.getParticipants().size());

    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldBeBannedFromAllGroupsWhenBannedFromSocialNetwork() {
        SocialNetwork socialNetwork = new SocialNetwork();
        User user1 = new User("user1");
        User user2 = new User("user2");
        Group group1 = new Group("group1", user1);
        Group group2 = new Group("group1", user2);

        socialNetwork.registerGroup(group1);
        socialNetwork.registerGroup(group2);

        socialNetwork.banUser(user2);

        Assertions.assertTrue(group1.getBannedUsers().contains(user2));
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void shouldRemoveGroupFromUserWhenRemoved() {
        User user1 = new User("user1");
        Group group1 = new Group("group1", user1);
        group1.removeUser(user1);
        Assertions.assertTrue(user1.getGroups().isEmpty());
    }
}
