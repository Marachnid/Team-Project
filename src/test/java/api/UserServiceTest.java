package api;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.ws.rs.core.Response;
import team.project.entity.Profile;
import team.project.api.UserService;
import java.util.*;


public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    //TODO this is the start of a testing class UserService - use this first test as an example to continue testing other methods in UserService class until we have full coverage!
    @Test
    void testAddUser() {
        Profile user = new Profile();
        user.setId(1);
        Response response = userService.addUser(user);

        assertEquals(201, response.getStatus());
        Profile createdUser = (Profile) response.getEntity();
        assertEquals(1, createdUser.getId());
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
    }
}
