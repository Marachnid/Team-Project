package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.project.entity.Calculations;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for Calculations - validates calculations are returning expected values
 * Tests male and female (some calculation differences between each)
 * Expected values were calculated by hand -- too much variance in how online calculators round
 */
class CalculationsTest {

    Calculations maleCalculation;
    Calculations femaleCalculation;

    /**Instantiates commonly used testing Profile objects*/
    @BeforeEach
    void setUp() {
        maleCalculation = new Calculations(25, 70, 200, "male", 1.55);
        femaleCalculation = new Calculations(20, 65, 155, "female", 1.375);
    }

    /**tests BMI calculations*/
    @Test
    void calculateBMI() {
        double maleBMI = Math.round(maleCalculation.calculateBMI() * 100) / 100.0;
        double femaleBMI = Math.round(femaleCalculation.calculateBMI() * 100) / 100.0;

        assertEquals(28.69, maleBMI);
        assertEquals(25.79, femaleBMI);
    }

    /**tests BMR calculations*/
    @Test
    void calculateBMR() {
        double maleBMR = Math.round(maleCalculation.calculateBMR() * 100) / 100.0;
        double femaleBMR = Math.round(femaleCalculation.calculateBMR() * 100) / 100.0;

        assertEquals(2017.61, maleBMR);
        assertEquals(1523.97, femaleBMR);
    }

    /**tests LBM calculations*/
    @Test
    void calculateLBM() {
        double maleLBM = Math.round(maleCalculation.calculateLBM() * 100) / 100.0;
        double femaleLBM = Math.round(femaleCalculation.calculateLBM() * 100) / 100.0;

        assertEquals(143.6, maleLBM);
        assertEquals(117.82, femaleLBM);
    }

    /**tests BF% calculations*/
    @Test
    void calculateBodyFat() {

        double maleBodyFat = Math.round(maleCalculation.calculateBodyFat() * 100) / 100.0;
        double femaleBodyFat = Math.round(femaleCalculation.calculateBodyFat() * 100) / 100.0;

        assertEquals(23.98, maleBodyFat);
        assertEquals(30.15, femaleBodyFat);
    }

    /**tests TDEE calculations*/
    @Test
    void calculateTDEE() {

        double maleTDEE = Math.round(maleCalculation.calculateTDEE() * 100) / 100.0;
        double femaleTDEE = Math.round(femaleCalculation.calculateTDEE() * 100) / 100.0;

        assertEquals(3127.29, maleTDEE);
        assertEquals(2095.45, femaleTDEE);
    }

    /**tests IBW calculations*/
    @Test
    void calculateIBW() {

        double maleIBW = Math.round(maleCalculation.calculateIBW() * 100) / 100.0;
        double femaleIBW = Math.round(femaleCalculation.calculateIBW() * 100) / 100.0;

        assertEquals(156.2, maleIBW);
        assertEquals(126.5, femaleIBW);
    }

    /**tests that all 6 calculations are present*/
    @Test
    void getCalculations() {
        assertEquals(6, maleCalculation.getCalculations().size());
    }
}