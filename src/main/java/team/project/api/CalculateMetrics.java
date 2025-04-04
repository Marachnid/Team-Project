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
        Map<String, Double> calculations = new HashMap<>();
        calculations.put("BMI", calculateBMI(data));
        calculations.put("BMR", calculateBMR(data));
        calculations.put("LBM", calculateLBM(data));
        calculations.put("bodyfat", calculateBodyFat(data));
        calculations.put("IBW", calculateIBW(data));

        // TODO find activity level
        // calculations.put("TDEE", calculateTDEE(data, activityLevel));

        //example of just grabbing/using the raw parameters instead of storing in an object
        String message = age + " " + weight + " " + height + " " + sex;


        //return calculations map
        return Response.status(200).entity(calculations).build();
    }


    /**
     * @param data Profile data
     * @return Body mass index
     */
    public double calculateBMI(Profile data) {
        int weight = data.getWeight();
        int height = data.getHeight();

        return weight * 703 / Math.pow(height, 2);

    }

    /**
     * @param data Profile data
     * @return Basal metabolic rate
     */
    public double calculateBMR(Profile data) {
        int weight = data.getWeight();
        int height = data.getHeight();
        int age = data.getAge();
        String sex = data.getSex();
        double LBM = calculateLBM(data);

        if (sex.equals("male") || sex.equals("Male")) {
            return 66 + (6.23 * weight) + (12.7 * height) - (6.8 * age);
        } else if (sex.equals("female") || sex.equals("Female")) {
            return 655 + (4.35 * weight) + (4.7 * height) - (4.7 * age);
        } else {
            return 370 / (9.8 * LBM);
        }

    }

    /**
     * @param data Profile data
     * @return Lean body mass
     */
    public double calculateLBM(Profile data) {
        int weight = data.getWeight();
        int height = data.getHeight();
        String sex = data.getSex();

        if (sex.equals("male") || sex.equals("Male")) {
            return (0.407 * weight) + (0.267 * height) - 19.2;
        } else if (sex.equals("female") || sex.equals("Female")) {
            return (0.252 * weight) + (0.473 * height) - 48.3;
        } else {
            return weight * (1 - calculateBMR(data) / 100);
        }

    }

    /**
     * @param data Profile data
     * @return Body fat percentage
     */
    public double calculateBodyFat(Profile data) {
        double BMI = calculateBMI(data);
        int age = data.getAge();
        String sex = data.getSex();

        if (sex.equals("male") || sex.equals("Male")) {
            return (1.2 * BMI) + (0.23 * age) - 16.2;
        } else if (sex.equals("female") || sex.equals("Female")) {
            return (1.2 * BMI) + (0.23 * age) - 5.4;
        } else {
            return -1;
        }

    }

    /**
     * @param data Profile data
     * @param activityLevel User activity level
     * @return Total daily energy expenditure
     */
    public double calculateTDEE(Profile data, double activityLevel) {
        double BMR = calculateBMR(data);

        return BMR * activityLevel;
    }


    // TODO choose if we want to use of IBW calculation

    /**
     * @param data Profile data
     * @return Ideal body weight
     */
    public double calculateIBW(Profile data) {
        int height = data.getHeight();
        String sex = data.getSex();

        if (sex.equals("male") || sex.equals("Male") && height > 60) {
            return 50 + (2.3 * height);
        } else if (sex.equals("female") || sex.equals("Female") && height > 60) {
            return 45.5 + (2.3 * height);
        } else {
            return -1;
        }

    }

}
