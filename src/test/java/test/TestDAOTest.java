package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestDAOTest {

    TestDAO dao;


    @BeforeEach
    void setUp() {
        dao = new TestDAO();

    }

    @Test
    void getById() {

        ProfileEntity profile = new ProfileEntity();

        profile = dao.getById(1);
        System.out.println(profile.getId());
        System.out.println(profile.getAge());
        System.out.println(profile.getWeight());
        System.out.println(profile.getHeight());
        System.out.println(profile.getSex());




    }
}