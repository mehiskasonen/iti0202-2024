package ee.taltech.iti0202.socialnetwork.admin;

import ee.taltech.iti0202.socialnetwork.SocialNetwork;
import ee.taltech.iti0202.socialnetwork.user.User;

public class Admin extends User {

    /**
     * Constructor method for Admin.
     * @param name of admin.
     */
    public Admin(String name) {
        super(name);
    }

    /**
     * Constructor method for Admin, if admin also has age.
     */
    public Admin(String name, Integer age) {
        super(name, age);
    }

    /**
     * Bans user from all groups in the social network, unless the user is an admin.
     * @param user object.
     * @param socialNetwork object.
     */
    public void banUserFromSocialNetwork(User user, SocialNetwork socialNetwork) {
        if (!(user instanceof Admin)) {
            socialNetwork.getGroups().forEach(group -> {
                group.banUser(user);
            });
        }
    }

    /**
     * @return name of Admin.
     */
    @Override
    public String getName() {
        return "(Admin) " + super.getName();
    }
}
