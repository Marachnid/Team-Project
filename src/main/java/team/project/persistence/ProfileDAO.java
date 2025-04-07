package team.project.persistence;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import team.project.entity.Profile;

import java.util.List;

/**
 * DAO class for Profile Entity
 * accesses team_project schema and profiles table/entity to perform insert/delete/edit/get operations
 */
public class ProfileDAO {

    //open hibernate session
    private Session getSession() { return SessionFactoryProvider.getSessionFactory().openSession(); }


    /**
     * retrieves profile records by id
     * @param id profile id
     * @return profile
     */
    public Profile getById(int id) {
        Session session = getSession();
        Profile profile = session.get(Profile.class, id);
        session.close();
        return profile;
    }


    /**
     * retrieves a list of all profile records
     * @return list of Profile objects
     */
    public List<Profile> getAllProfiles() {
        Session session = getSession();
        HibernateCriteriaBuilder builder = (HibernateCriteriaBuilder) session.getCriteriaBuilder();
        CriteriaQuery<Profile> criteria = builder.createQuery(Profile.class);
        Root<Profile> root = criteria.from(Profile.class);
        return session.createQuery(criteria).list();
    }


    /**
     * updates a designated profile
     * @param profile profile to be updated
     */
    public void updateProfile(Profile profile) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(profile);
        transaction.commit();
        session.close();
    }


    /**
     * inserts a new profile object as a db record
     * @param profile profile object to be added
     */
    public void insertProfile(Profile profile) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(profile);
        transaction.commit();
        session.close();
    }


    /**
     * deletes a profile record
     * @param profile profile object/record to be deleted
     */
    public void deleteProfile(Profile profile) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(profile);
        transaction.commit();
        session.close();
    }
}
