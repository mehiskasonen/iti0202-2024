package projbasetest;

import ee.taltech.iti0202.sportsclub.users.User;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class ProjBaseTest {

    List<User> testUsers;
    @BeforeEach
    void setUp() {

        testUsers = new ArrayList<>();
        User user = new TestUser("John");
        testUsers.add(user);
    }

    // Custom User subclass to expose constructor for testing
    public static class TestUser extends User {
        public TestUser(String name) {
            super(name);
        }
    }
}
