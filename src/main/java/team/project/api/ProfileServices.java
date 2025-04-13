package team.project.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.project.entity.Calculations;
import team.project.entity.Profile;
import team.project.persistence.ProfileDAO;

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

    private static final Logger logger = LogManager.getLogger(ProfileServices.class);
    ProfileDAO dao;
    Calculations calculations;

    /**
     * GETS all Profiles
     * @return response containing a list of all profiles in JSON format with HTTP 200 OK.
     */
    @GET
    @Path("GET/")
    @Operation(
            summary = "Get all profiles",
            description = "Retrieves all profiles. Returns 200 OK.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful retrieval of profiles",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Profile.class)),
                                    examples = {
                                            @ExampleObject(
                                                    name = "All Profiles Example",
                                                    summary = "Example list of profiles",
                                                    value = "[{\"id\": \"1\", \"age\": \"30\", \"sexType\": \"male\", \"height\": \"70\", \"weight\": \"160\", \"activity\": \"1.725\"}," +
                                                            "{\"id\": \"2\", \"age\": \"31\", \"sexType\": \"female\", \"height\": \"65\", \"weight\": \"130\", \"activity\": \"1.3\"}]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public Response getAllProfiles() {
        logger.info("Get request received for all profiles");
        dao = new ProfileDAO();

        List<Profile> profiles = dao.getAllProfiles();
        if (profiles == null || profiles.isEmpty()) {
            logger.warn("No profiles found");
        } else {
            for (Profile profile : profiles) {
                calculations = new Calculations(profile);
                profile.setCalculations(calculations.getCalculations());
            }
            logger.info(profiles.size() + " profiles found");
        }
        return Response.ok(profiles).build();
    }


    /**
     * GETS a profile by ID
     * @return JSON response of Profile data - HTTP 200 OK or a 404 not found if Profile doesn't exist
     */
    @GET
    @Operation(
            summary = "Get a profile by ID",
            description = "Retrieves a single profile by its ID. Returns 200 OK if found, or 404 Not Found if the profile does not exist.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile found and returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Profile Example",
                                                    summary = "A single profile",
                                                    value = "{\"id\": \"1\", \"age\": \"30\", \"height\": \"70\", \"sexType\": \"male\", \"weight\": \"160\", \"activity\": \"1.725\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @Path("GET/{id}")
    public Response getProfileById( @Parameter(
            name = "id",
            in = ParameterIn.PATH,
            required = true,
            description = "The ID of the profile to retrieve",
            example = "101"
    ) @PathParam("id") int id) {

        logger.info("Get request received for profile with id {}", id);
        dao = new ProfileDAO();
        Profile profile = dao.getById(id);

        if (profile == null) {
            logger.warn("Profile not found");
            return Response.status(Response.Status.NOT_FOUND).build();

        } else {

            calculations = new Calculations(profile);
            profile.setCalculations(calculations.getCalculations());
            logger.info("Profile found and returned successfully");
            return Response.ok(profile).build();
        }
    }


    /**
     * POSTS a new Profile record/object from received parameters
     * @return JSON response of Profile data posted - HTTP 201
     */
    @POST
    @Path("POST/{age}/{height}/{weight}/{sexType}/{activity}")
    @Operation(
            summary = "Add a new profile",
            description = "Creates a new profile using the provided parameters. Returns the created profile object and HTTP 201 status.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Profile successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "New Profile Example",
                                                    summary = "A newly created profile",
                                                    value = "{ \"id\": \"101\", \"age\": \"30\", \"height\": \"175.0\", \"weight\": \"160.0\", \"sex\": \"male\", \"activity\": \"1.2\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public Response addProfile(
            @Parameter(name = "age", in = ParameterIn.PATH, required = true, description = "Age in years", example = "30" ) @PathParam("age") int age,
            @Parameter(name = "height", in = ParameterIn.PATH, required = true, description = "Height in inches", example = "65") @PathParam("height") double height,
            @Parameter(name = "weight", in = ParameterIn.PATH, required = true, description = "Weight in pounds", example = "160.0") @PathParam("weight") double weight,
            @Parameter(name = "sexType", in = ParameterIn.PATH, required = true, description = "Biological sex", example = "male") @PathParam("sexType") String sexType,
            @Parameter(name = "activity", in = ParameterIn.PATH, required = true, description = "Activity level as a multiplier", example = "1.2") @PathParam("activity") double activity) {

        logger.info("POST request received to add profile");
        if (sexType == null || sexType.isEmpty()) {
            logger.error("sexType is empty");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        dao = new ProfileDAO();
        Profile newProfile = new Profile(age, height, weight, sexType, activity);
        dao.insertProfile(newProfile);
        logger.info("Profile successfully added");
        return Response.status(Response.Status.CREATED).entity(newProfile).build();
    }


    /**
     * PUTS/updates a Profile object/record from new arguments
     * checks if the entered id matches any records to prevent creating new Profiles/records
     * @return JSON response of Profile data/records being updated - HTTP 200, 404 if not found
     */
    @PUT
    @Path("PUT/{id}/{age}/{height}/{weight}/{sexType}/{activity}")
    @Operation(
            summary = "Update an existing profile",
            description = "Updates a profile by ID using the provided new values. Returns the updated profile if successful, or 404 if the profile does not exist.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Updated Profile Example",
                                                    summary = "An updated profile",
                                                    value = "{ \"id\": \"101\", \"age\": \"35\", \"height\": \"178.0\", \"weight\": \"165.0\", \"sex\": \"female\", \"activity\": \"1.4\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public Response updateProfile(
            @Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "ID of the profile to update", example = "101") @PathParam("id") int id,
            @Parameter(name = "age", in = ParameterIn.PATH, required = true, description = "New age value", example = "35") @PathParam("age") int age,
            @Parameter(name = "height", in = ParameterIn.PATH, required = true, description = "New height in inches", example = "68.0") @PathParam("height") double height,
            @Parameter(name = "weight", in = ParameterIn.PATH, required = true, description = "New weight in pounds", example = "165.0") @PathParam("weight") double weight,
            @Parameter(name = "sexType", in = ParameterIn.PATH, required = true, description = "New sex type", example = "female") @PathParam("sexType") String sexType,
            @Parameter(name = "activity", in = ParameterIn.PATH, required = true, description = "New activity level multiplier", example = "1.4") @PathParam("activity") double activity) {

        logger.info("PUT request received to update profile");
        dao = new ProfileDAO();
        Profile profileToUpdate = dao.getById(id);

        //prevents creating a new profile if the profile-to-edit is null
        if (profileToUpdate == null) {
            logger.warn("Update failed for profile with id " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {

            dao = new ProfileDAO();
            profileToUpdate.setAge(age);
            profileToUpdate.setHeight(height);
            profileToUpdate.setWeight(weight);
            profileToUpdate.setSexType(sexType);
            profileToUpdate.setActivity(activity);

            dao.updateProfile(profileToUpdate);
            logger.info("Profile successfully updated");
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

    @Operation(
            summary = "Delete a profile by ID",
            description = "Deletes the profile associated with the given ID. Returns the deleted profile on success, or 404 if not found.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile successfully deleted",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Deleted Profile Example",
                                                    summary = "A deleted profile",
                                                    value = "{ \"id\": \"101\", \"age\": \"35\", \"height\": \"178.0\", \"weight\": \"165.0\", \"sex\": \"female\", \"activity\": \"1.4\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )

    public Response deleteProfile( @Parameter(
            name = "id",
            in = ParameterIn.PATH,
            required = true,
            description = "ID of the profile to delete",
            example = "101"
    ) @PathParam("id") int id) {

        logger.info("DELETE request received to delete profile");
        dao = new ProfileDAO();
        Profile profileToDelete = dao.getById(id);

        //prevents requests to delete null objects
        if (profileToDelete == null) {
            logger.warn("Delete failed for profile with id " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {

            dao.deleteProfile(profileToDelete);
            logger.info("Profile successfully deleted");
            return Response.ok(profileToDelete).build();
        }
    }
}