package refactor;


import refactor.ProfileEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculation class for determining and returning a calculated map of metrics
 * Results are ONLY estimations - actual metrics are very individualized and nuanced from person-to-person
 */
public class Calculations {

    //instance variables
    private int age;
    private double height;
    private double weight;
    private String sexType;
    private double activity;
    private Map<String, Double> calculations;
    private ProfileEntity profile;


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
    public Calculations(ProfileEntity profile) {
        this.age = profile.getAge();
        this.height = profile.getHeight();
        this.weight = profile.getWeight();
        this.sexType = profile.getSexType().toLowerCase();
        this.activity = profile.getActivity();
    }


    /**
     * calculates estimated BMI (body mass index) with height and weight
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
            bmr =  66 + (6.23 * weight) + (12.7 * height) - (6.8 * age);

        } else if (sexType.equals("female")) {
            bmr =  655 + (4.35 * weight) + (4.7 * height) - (4.7 * age);
        }

        return bmr;
    }


    /**
     * calculates estimated LBM (lean body mass) with sexType, height, weight
     * @return calculated LBM
     */
    public double calculateLBM() {
        double lbm = 0;

        if (sexType.equals("male")) {
            lbm = (0.407 * weight) + (0.267 * height) - 19.2;

        } else if (sexType.equals("female")) {
            lbm = (0.252 * weight) + (0.473 * height) - 48.3;
        }

        return lbm;
    }


    /**
     * calculates estimated body fat percentage with sexType, BMI, age
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
     * @return calculated TDEE
     */
    public double calculateTDEE() {
        return calculateBMR() * activity;
    }


    /**
     * calculates estimated IBW (ideal body weight) based on height - not very accurate
     * @return calculated IBW
     */
    public double calculateIBW() {
        double ibw = 0;

        if (sexType.equals("male")) {
            ibw = 50 + (2.3 * height);

        } else if (sexType.equals("female")) {
            ibw = 45.5 + (2.3 * height);
        }

        return ibw;
    }


    /**
     * GETS age
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * SETS age
     * @param age age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * GETS height
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * SETS height
     * @param height height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * GETS weight
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * SETS weight
     * @param weight weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * GETS sexType
     * @return sexType
     */
    public String getSexType() {
        return sexType;
    }

    /**
     * SETS sexType
     * @param sexType sexType
     */
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    /**
     * GETS activity levels
     * @return activity levels
     */
    public double getActivity() {
        return activity;
    }

    /**
     * SETS activity levels
     * @param activity activity levels
     */
    public void setActivity(double activity) {
        this.activity = activity;
    }

    /**
     * GETS calculations (rounds results to 2 decimal places)
     * @return calculations
     */
    public Map<String, Double> getCalculations() {
        calculations = new HashMap<>();
        calculations.put("BMI", Math.round(calculateBMI() * 100.0) / 100.0);
        calculations.put("BMR", Math.round(calculateBMR() * 100.0) / 100.0);
        calculations.put("LBM", Math.round(calculateLBM() * 100.0) / 100.0);
        calculations.put("bodyfat", Math.round(calculateBodyFat() * 100.0) / 100.0);
        calculations.put("TDEE", Math.round(calculateTDEE() * 100.0) / 100.0);
        calculations.put("IBW", Math.round(calculateIBW() * 100.0) / 100.0);
        return calculations;
    }

    /**
     * SETS calculations
     * @param calculations calculations
     */
    public void setCalculations(Map<String, Double> calculations) {
        this.calculations = calculations;
    }
}