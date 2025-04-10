package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.project.entity.Profile;
import team.project.persistence.ProfileDAO;
import utilities.Database;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Main testing class for Profile DAO operations
 */
class ProfileDAOTest {

    ProfileDAO dao;
    Database database;
    Profile profile;
    Profile alteredProfile;
    List<Profile> profiles;


    /**Sets up testing environment*/
    @BeforeEach
    void setUp() {
        dao = new ProfileDAO();
        profiles = new ArrayList<>();
        database = Database.getInstance();
        database.runSQL("profiles_clean.sql");
    }


    /**Tests getting a profile by its ID*/
    @Test
    void getByIdSuccess() {
        profile = dao.getById(1);
        assertNotNull(profile);
        assertEquals(20, profile.getAge());
        assertEquals(150, profile.getWeight());
    }


    /**Tests retrieving all profiles*/
    @Test
    void getAllSuccess() {
        profiles = dao.getAllProfiles();
        assertEquals(6, profiles.size());
    }


    /**Tests adding a profile*/
    @Test
    void addProfileSuccess() {
        profile = new Profile(20, 70, 185, "male", 1);
        dao.insertProfile(profile);

        alteredProfile = dao.getById(profile.getId());
        assertNotNull(alteredProfile);
        assertEquals(20, alteredProfile.getAge());
        assertEquals(185, alteredProfile.getWeight());
    }


    /**Tests updating a profile*/
    @Test
    void updateProfileSuccess() {
        profile = dao.getById(1);
        profile.setAge(45);
        profile.setHeight(75);
        profile.setWeight(200);
        profile.setActivity(2);
        dao.updateProfile(profile);

        alteredProfile = dao.getById(1);
        assertNotNull(alteredProfile);
        assertEquals(45, alteredProfile.getAge());
        assertEquals(75, alteredProfile.getHeight());
        assertEquals(200, alteredProfile.getWeight());
        assertEquals(2, alteredProfile.getActivity());
    }


    /**Tests deleting a profile*/
    @Test
    void deleteProfileSuccess() {
        profile = dao.getById(1);
        dao.deleteProfile(profile);

        assertNull(dao.getById(1));
        assertEquals(5, dao.getAllProfiles().size());
    }

    /**Test for non-existent id*/
    @Test
    void getByIdNotFound() {
        profile = dao.getById(555);
        assertNull(profile);
    }

    /**Test for wrong datatype*/
    @Test
    void invalidStringToIntConversionThrowsException() {
        String invalidAge = "twenty";
        assertThrows(NumberFormatException.class, () -> {
            int age = Integer.parseInt(invalidAge);
        });
    }

}