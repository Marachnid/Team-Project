package team.project.api;

import team.project.entity.Profile;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


/**
 * Calculation API - calculates health metrics from raw parameters on a call-by-call basis
 * Application path is routed through 'services' --- check DataApplication
 */
@Path("/calculations")
public class CalculateMetrics {

    /*
        handles HTTP GET requests for retrieving calculation values w/raw parameters
        includes path parameters
        URL/URI would look like:
        http://localhost:8080/Team_Project_war/services/calculations/20/male/70/200
     */
    @GET
    @Path("/{age}/{sex}/{height}/{weight}")
    @Produces("application/json")
    public Response getProfiles(
            @PathParam("age") int age,
            @PathParam("sex") String sex,
            @PathParam("height") int height,
            @PathParam("weight") int weight
    ) {

        //used as an easy way to store and pass data as an object instead of individual values
        Profile data = new Profile(age, sex, height, weight);

        //the output results are compiled into a Map and will be displayed as such
        Map<String, Integer> calculations = new HashMap<>();
        calculations.put("BMI", calculateBMI(data));
        calculations.put("BMR", calculateBMR(data));
        calculations.put("LBM", calculateLBM(data));
        calculations.put("bodyfat", calculateBodyFat(data));
        calculations.put("IBW", calculateIBW(data));
        calculations.put("TDEE", calculateTDEE(data));

        //example of just grabbing/using the raw parameters instead of storing in an object
        String message = age + " " + weight + " " + height + " " + sex;


        //return calculations map
        return Response.status(200).entity(calculations).build();
    }


    public int calculateBMI(Profile data) {

        return data.getAge();
    }

    public int calculateBMR(Profile data) {

        return data.getAge();
    }

    public int calculateLBM(Profile data) {

        return data.getAge();
    }

    public int calculateBodyFat(Profile data) {

        return data.getAge();
    }

    public int calculateIBW(Profile data) {

        return data.getAge();
    }

    public int calculateTDEE(Profile data) {

        return data.getAge();
    }
}
