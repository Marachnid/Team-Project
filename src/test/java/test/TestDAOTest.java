package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestDAOTest {

    TestDAO dao;


    @BeforeEach
    void setUp() {
        dao = new TestDAO();

    }

    @Test
    void getById() {

        ProfileEntity profile;

        profile = dao.getById(1);
        System.out.println(profile.getId());
        System.out.println(profile.getAge());
        System.out.println(profile.getWeight());
        System.out.println(profile.getHeight());
        System.out.println(profile.getSex());
    }



    @Test
    void getAll() {

        List<ProfileEntity> profiles = new ArrayList<>();

        profiles = dao.getAllProfiles();


        for (ProfileEntity profile : profiles) {
            System.out.println(profile.getId());
            System.out.println(profile.getAge());
            System.out.println(profile.getWeight());
            System.out.println(profile.getHeight());
            System.out.println(profile.getSex());
        }


    }





}