package team.project.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import team.project.entity.Calculations;
import team.project.entity.Profile;

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

    Profile profile;
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
    @Operation(
        summary = "Get calculation data only (lightweight)",
        description = "Returns only the calculated health and nutrition metrics such as BMR, TDEE, and BMI based on raw profile input. No profile data is returned."
    )
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successfully returned calculation data")}
    )
    public Response getLightCalculations(
            @Parameter(name = "age", description = "Age in years", example = "30") @PathParam("age") int age,
            @Parameter(name = "height", description = "Height in inches", example = "70") @PathParam("height") double height,
            @Parameter(name = "weight", description = "Weight in pounds", example = "165") @PathParam("weight") double weight,
            @Parameter(name = "sexType", description = "Biological sex", example = "female") @PathParam("sexType") String sexType,
            @Parameter(name = "activity", description = "Activity multiplier (e.g., 1.2 for sedentary, 1.9 for very active)", example = "1.55") @PathParam("activity") double activity) {


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
    @Operation(
        summary = "Get full profile with calculation data",
        description = "Returns the full profile object, including the input parameters and calculated values (BMR, TDEE, BMI, etc.)."
    )
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successfully returned profile and calculations")}
    )
    public Response getFullCalculations(
            @Parameter(name = "age", description = "Age in years", example = "30") @PathParam("age") int age,
            @Parameter(name = "height", description = "Height in inches", example = "70") @PathParam("height") double height,
            @Parameter(name = "weight", description = "Weight in pounds", example = "165") @PathParam("weight") double weight,
            @Parameter(name = "sexType", description = "Biological sex", example = "female") @PathParam("sexType") String sexType,
            @Parameter(name = "activity", description = "Activity multiplier (e.g., 1.2 for sedentary, 1.9 for very active)", example = "1.55") @PathParam("activity") double activity) {

        profile = new Profile(age, height, weight, sexType, activity);
        calculations = new Calculations(profile);
        profile.setCalculations(calculations.getCalculations());
        return Response.ok(profile).build();
    }
}
