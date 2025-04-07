package team.project.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculation class for determining and returning a calculated map of metrics
 * Results are ONLY estimations - actual metrics are very individualized and nuanced from person-to-person
 */
public class Calculations {

    //instance variables
    @Schema(description = "Age (in years)", example = "38")
    private int age;
    @Schema(description = "Height (in inches)", example = "70")
    private double height;
    @Schema(description = "Weight (in pounds)", example = "165")
    private double weight;
    @Schema(description = "Biological Sex", example = "male")
    private String sexType;
    @Schema(description = "Activity Modifier, which is used to estimate how many calories are burned per day based on activity level", example = "1.725")
    private double activity;


    /**empty constructor*/
    public Calculations() {}

    /**
     * calculation constructor with raw parameters/arguments
     * used for performing calculations with no profile attachment
     * @param age age
     * @param height height
     * @param weight weight
     * @param sexType sexType
     * @param activity activity levels
     */
    public Calculations(int age, double height, double weight, String sexType, double activity) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sexType = sexType.toLowerCase();
        this.activity = activity;
    }


    /**
     * calculation constructor with predefined Profile object
     * used for performing and attaching calculations to Profile objects
     * @param profile Profile object
     */
    public Calculations(Profile profile) {
        this.age = profile.getAge();
        this.height = profile.getHeight();
        this.weight = profile.getWeight();
        this.sexType = profile.getSexType().toLowerCase();
        this.activity = profile.getActivity();
    }


    /**
     * calculates estimated BMI (body mass index) with height and weight
     * https://my.clevelandclinic.org/health/articles/9464-body-mass-index-bmi --- formula source
     * @return calculated BMI
     */
    public double calculateBMI() {
        return weight * 703 / Math.pow(height, 2);
    }


    /**
     * calculates estimated BMR (basal metabolic rate) with sexType, height, weight, age
     * @return calculated BMR
     */
    public double calculateBMR() {
        double bmr = 0;

        if (sexType.equals("male")) {
            bmr = (88.362 + (13.397 * (weight / 2.2)) + (4.799 * (height * 2.54)) - (5.677 * age));

        } else if (sexType.equals("female")) {
            bmr =  447.593 + (9.247 * (weight / 2.2)) + (3.098 * (height * 2.54)) - (4.330 * age);
        }

        return bmr;
    }


    /**
     * calculates estimated LBM (lean body mass) with sexType, height, weight
     * https://www.bmi-calculator.net/lean-body-mass-calculator/ --- formula source (boer formula)
     * has to convert from metric kg/cm to imperial lbs/in units (multiply and dividing by 2.2 and 2.54)
     * @return calculated LBM
     */
    public double calculateLBM() {
        double lbm = 0;

        if (sexType.equals("male")) {
            lbm = ((0.407 * (weight / 2.2)) + ((0.267 * height * 2.54)) - 19.2) * 2.2;

        } else if (sexType.equals("female")) {
            lbm = ((0.407 * (weight / 2.2)) + ((0.267 * height * 2.54)) - 19.2) * 2.2;
        }

        return lbm;
    }


    /**
     * calculates estimated body fat percentage with sexType, BMI, age
     * https://www.calculator.net/body-fat-calculator.html --- formula source
     * @return calculated bodyFat percentage
     */
    public double calculateBodyFat() {
        double bodyFat = 0;

        if (sexType.equals("male")) {
            bodyFat = (1.2 * calculateBMI()) + (0.23 * age) - 16.2;

        } else if (sexType.equals("female")) {
            bodyFat = (1.2 * calculateBMI()) + (0.23 * age) - 5.4;
        }

        return bodyFat;
    }


    /**
     * calculates estimated TDEE (total daily energy expenditure) with BMR and activity
     * https://www.gigacalculator.com/calculators/tdee-calculator.php --- formula source
     * @return calculated TDEE
     */
    public double calculateTDEE() {
        return calculateBMR() * activity;
    }


    /**
     * calculates estimated IBW (ideal body weight) based on height - not very accurate
     * initial values are in kilograms -- multiplied by 2.2 to convert to pounds
     * https://www.bmi-calculator.net/ideal-weight-calculator/robinson-formula/ --- formula source
     * @return calculated IBW
     */
    public double calculateIBW() {
        double ibw = 0;

        if (sexType.equals("male")) {
            ibw = (52 + (1.9 * (height - 60))) * 2.2;

        } else if (sexType.equals("female")) {
            ibw = (49 + (1.7 * (height - 60))) * 2.2;
        }
        return ibw;
    }


    /**
     * GETS calculations (rounds results to 2 decimal places)
     * @return calculations
     */
    public Map<String, Double> getCalculations() {
        Map<String, Double>calculations = new HashMap<>();
        calculations.put("BMI", Math.round(calculateBMI() * 100.0) / 100.0);
        calculations.put("BMR", Math.round(calculateBMR() * 100.0) / 100.0);
        calculations.put("LBM", Math.round(calculateLBM() * 100.0) / 100.0);
        calculations.put("bodyfat", Math.round(calculateBodyFat() * 100.0) / 100.0);
        calculations.put("TDEE", Math.round(calculateTDEE() * 100.0) / 100.0);
        calculations.put("IBW", Math.round(calculateIBW() * 100.0) / 100.0);
        return calculations;
    }
}