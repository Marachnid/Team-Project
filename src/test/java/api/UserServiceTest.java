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
        userService.resetUsers();
    }

    @Test
    void testAddUser() {
        Profile user = new Profile();
        Response response = userService.addUser(user);

        assertEquals(201, response.getStatus());
        Profile createdUser = (Profile) response.getEntity();
        assertNotNull(createdUser.getId());
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
    }

    @Test
    void testGetUserById() {
        Profile user = new Profile();
        Response addResponse = userService.addUser(user);
        Profile createdUser = (Profile) addResponse.getEntity();

        Response response = userService.getUserById(createdUser.getId());
        assertEquals(200, response.getStatus());

        Profile fetchedUser = (Profile) response.getEntity();
        assertEquals(createdUser.getId(), fetchedUser.getId());
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

    }

    @Test
    void testGetAllUsers() {
        Profile user1 = new Profile();
        Profile user2 = new Profile();
        userService.addUser(user1);
        userService.addUser(user2);

        Response response = userService.getAllUsers();
        assertEquals(200, response.getStatus());

        List<Profile> users = (List<Profile>) response.getEntity();
        assertEquals(2, users.size());
    }

    @Test
    void testUpdateUser() {
        Profile user = new Profile();
        Response addResponse = userService.addUser(user);
        Profile createdUser = (Profile) addResponse.getEntity();

        Profile updatedUser = new Profile();
        updatedUser.setId(createdUser.getId());

        Response response = userService.updateUser(createdUser.getId(), updatedUser);
        assertEquals(200, response.getStatus());

        Profile returnedUser = (Profile) response.getEntity();
        assertEquals(createdUser.getId(), returnedUser.getId());

    }

    @Test
    void testDeleteUser() {
        Profile user = new Profile();
        Response addResponse = userService.addUser(user);
        Profile createdUser = (Profile) addResponse.getEntity();

        Response response = userService.deletePerson(createdUser.getId());
        assertEquals(204, response.getStatus());

        Response getResponse = userService.getUserById(createdUser.getId());
        assertEquals(getResponse.getStatus(), Response.Status.NOT_FOUND.getStatusCode());


    }
}
