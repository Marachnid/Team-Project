package refactor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Lightweight Endpoint responsible for handling calculations without persistence/DB interactions
 * raw profile values are entered by URI to perform calculations and return data directly
 * can choose whether to receive only calculation data, or calculation data + the parameters used in calculations
 */
@Path("calculate")
@Produces(MediaType.APPLICATION_JSON)
public class CalculationServices {

    ProfileEntity profile;
    Calculations calculations;


    /**
     * Lightweight version - this only returns calculations, no Profile interaction
     * @param age age
     * @param height height
     * @param weight weight
     * @param sexType sexType
     * @param activity activity levels
     * @return JSON response of only calculation data
     */
    @GET
    @Path("/light/{age}/{height}/{weight}/{sexType}/{activity}")
    public Response getLightCalculations(
            @PathParam("age") int age,
            @PathParam("height") double height,
            @PathParam("weight") double weight,
            @PathParam("sexType") String sexType,
            @PathParam("activity") double activity) {


        calculations = new Calculations(age, height, weight, sexType, activity);
        return Response.ok(calculations.getCalculations()).build();
    }


    /**
     * Full version - returns both calculation data + parameter/Profile data
     * @param age age
     * @param height height
     * @param weight weight
     * @param sexType sexType
     * @param activity activity levels
     * @return JSON response of calculation and parameter/Profile data
     */
    @GET
    @Path("/full/{age}/{height}/{weight}/{sexType}/{activity}")
    public Response getFullCalculations(
            @PathParam("age") int age,
            @PathParam("height") double height,
            @PathParam("weight") double weight,
            @PathParam("sexType") String sexType,
            @PathParam("activity") double activity) {


        profile = new ProfileEntity(age, height, weight, sexType, activity);
        calculations = new Calculations(profile);
        return Response.ok(calculations).build();
    }
}
