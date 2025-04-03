package team.project.api;

import team.project.entity.Profile;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * Class representing user service
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
    private static Map<Integer, Profile> users = new HashMap<>();
    private static int idCounter = 1;

    /**
     * GET all users
     * @return response containing a list of all users in JSON format with HTTP 200 OK.
     */
    @GET
    public Response getAllUsers() {
        return Response.ok(new ArrayList<>(users.values())).build();
    }

    /**
     * GET one user
     * @return response containing user JSON format with HTTP 200 OK or a 404 not found if user doesn't exist
     */
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        Profile user = users.get(id);
        return (user != null) ? Response.ok(user).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * POST a user
     * @return response containing user in JSON format with HTTP 201 - created
     */
    @POST
    public Response addUser(Profile user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    /**
     * PUT a user
     * @return response containing updated user in JSON format with HTTP 200
     */
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, Profile updatedUser) {
        if (!users.containsKey(id)) return Response.status(Response.Status.NOT_FOUND).build();
        updatedUser.setId(id);
        users.put(id, updatedUser);
        return Response.ok(updatedUser).build();
    }

    /**
     * DELETE a user
     * @return response HTTP 204 indicating that user was removed or 404 not found if user was not found
     */
    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") int id) {
        return (users.remove(id) != null) ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Clears all stored user Profiles in the system
     * Primarily for testing scenarios removing all existing users ensuring a fresh state before each test execution.
     */
    public void resetUsers() {
        users.clear();
    }

}

