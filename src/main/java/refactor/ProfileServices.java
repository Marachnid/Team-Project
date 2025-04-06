package refactor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Primary class for Profile operations/endpoints
 * handles GET/PUT/POST/DELETE of Profile objects via ProfileDAO
 */
@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileServices {

    ProfileDAO dao;
    Calculations calculations;

    /**
     * GETS all Profiles
     * @return response containing a list of all profiles in JSON format with HTTP 200 OK.
     */
    @GET
    @Path("GET/")
    public Response getAllUsers() {
        dao = new ProfileDAO();
        List<ProfileEntity> profiles = dao.getAllProfiles();

        //iterate through retrieved list to set calculation map of each Profile
        for (ProfileEntity profile : profiles) {
            calculations = new Calculations(profile);
            profile.setCalculations(calculations.getCalculations());
        }
        return Response.ok(profiles).build();
    }


    /**
     * GETS a profile by ID
     * @return JSON response of Profile data - HTTP 200 OK or a 404 not found if Profile doesn't exist
     */
    @GET
    @Path("GET/{id}")
    public Response getUserById(@PathParam("id") int id) {

        ProfileEntity profile = dao.getById(id);

        if (profile == null) {

            return Response.status(Response.Status.NOT_FOUND).build();
        } else {

            calculations = new Calculations(profile);
            profile.setCalculations(calculations.getCalculations());
            return Response.ok(profile).build();
        }
    }


    /**
     * POSTS a new Profile record/object from received parameters
     * @return JSON response of Profile data posted - HTTP 201
     */
    @POST
    @Path("POST/{age}/{height}/{weight}/{sexType}/{activity}")
    public Response addProfile(
            @PathParam("age") int age,
            @PathParam("height") double height,
            @PathParam("weight") double weight,
            @PathParam("sexType") String sexType,
            @PathParam("activity") double activity) {

        ProfileEntity newProfile = new ProfileEntity(age, height, weight, sexType, activity);
        dao.insertProfile(newProfile);
        return Response.status(Response.Status.CREATED).entity(newProfile).build();
    }



    /**
     * PUTS/updates a Profile object/record from new arguments
     * checks if the entered id matches any records to prevent creating new Profiles/records
     * @return JSON response of Profile data/records being updated - HTTP 200, 404 if not found
     */
    @PUT
    @Path("PUT/{id}/{age}/{height}/{weight}/{sexType}/{activity}")
    public Response updateUser(
            @PathParam("id") int id,
            @PathParam("age") int age,
            @PathParam("height") double height,
            @PathParam("weight") double weight,
            @PathParam("sexType") String sexType,
            @PathParam("activity") double activity) {


        ProfileEntity profileToUpdate = dao.getById(id);

        //prevents creating a new profile if the profile-to-edit is null
        if (profileToUpdate == null) {

            return Response.status(Response.Status.NOT_FOUND).build();
        } else {

            profileToUpdate.setAge(age);
            profileToUpdate.setHeight(height);
            profileToUpdate.setWeight(weight);
            profileToUpdate.setSexType(sexType);
            profileToUpdate.setActivity(activity);

            return Response.ok(profileToUpdate).build();
        }
    }


    /**
     * DELETE a Profile object/record by ID
     * checks if record-to-delete exists to prevent deleting null objects
     * @return JSON response showing which Profile object/record was deleted - HTTP 200, 404 if not found
     */
    @DELETE
    @Path("/DELETE/{id}")
    public Response deletePerson(@PathParam("id") int id) {

        ProfileEntity profileToDelete = dao.getById(id);

        //prevents requests to delete null objects
        if (profileToDelete == null) {

            return Response.status(Response.Status.NOT_FOUND).build();
        } else {

            dao.deleteProfile(profileToDelete);
            return Response.ok(profileToDelete).build();
        }
    }
}

