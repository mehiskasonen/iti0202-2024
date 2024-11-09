package ee.taltech.iti0202.sportsclub.users;

import org.junit.jupiter.api.Test;
import projbasetest.ProjBaseTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void getId() {
        User user = new ProjBaseTest.TestUser("John");
        UUID id = user.getId();
        assertNotNull(id);
    }

    @Test
    void getName() {
        User user = new ProjBaseTest.TestUser("John");
        assertEquals("John", user.getName());
    }

}
