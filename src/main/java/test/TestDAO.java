package test;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import java.util.List;



public class TestDAO {

    private Session getSession() { return SessionFactoryProvider.getSessionFactory().openSession(); }





    public ProfileEntity getById(int id) {
        Session session = getSession();

        ProfileEntity dao = session.get(ProfileEntity.class, id);
        session.close();

        return dao;
    }



}
