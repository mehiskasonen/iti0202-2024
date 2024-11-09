    package ee.taltech.iti0202.socialnetwork;

    import ee.taltech.iti0202.socialnetwork.feed.Feed;
    import ee.taltech.iti0202.socialnetwork.group.Group;
    import ee.taltech.iti0202.socialnetwork.message.Message;
    import ee.taltech.iti0202.socialnetwork.user.User;

    import java.util.HashSet;
    import java.util.Set;

    public class SocialNetwork {

        private Set<Group> groups;

        /**
         * Constructor for SocialNetwork.
         * Creates HashSet group.
         */
        public SocialNetwork() {
            this.groups = new HashSet<>();
        }

        /**
         * Register(add) a new group to set of groups.
         * @param group to be registered.
         */
        public void registerGroup(Group group) {
            groups.add(group);
        }

        /**
         * Return set of groups.
         * @return set.
         */
        public Set<Group> getGroups() {
            return groups;
        }

        /**
         * Build a feed for user.
         * Feed is an object, containing user and a set containing all messages of all groups the user belongs to.
         * @param user from users.
         * @return Feed object.
         */
        public Feed getFeedForUser(User user) {
            Set<Message> messages = new HashSet<>();
            for (Group group : groups) {
                if (group.getParticipants().contains(user)) {
                    messages.addAll(group.getMessages());
                }
            }
            return new Feed(user, messages);
        }

        /**
         * Bans user from all social network groups.
         * @param user to be banned from all social network groups.
         */
        public void banUser(User user) {
            for (Group group : groups) {
                group.banUser(user);
            }
        }
    }
