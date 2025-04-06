package refactor;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;








//TODO need to couple CalculateMetrics calculations with these return results





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
    public ProfileEntity getById(int id) {
        Session session = getSession();
        ProfileEntity profile = session.get(ProfileEntity.class, id);
        session.close();
        return profile;
    }


    /**
     * retrieves a list of all profile records
     * @return list of Profile objects
     */
    public List<ProfileEntity> getAllProfiles() {
        Session session = getSession();
        HibernateCriteriaBuilder builder = (HibernateCriteriaBuilder) session.getCriteriaBuilder();
        CriteriaQuery<ProfileEntity> criteria = builder.createQuery(ProfileEntity.class);
        Root<ProfileEntity> root = criteria.from(ProfileEntity.class);
        return session.createQuery(criteria).list();
    }


    /**
     * updates a designated profile
     * @param profile profile to be updated
     */
    public void updateProfile(ProfileEntity profile) {
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
    public void insertProfile(ProfileEntity profile) {
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
    public void deleteProfile(ProfileEntity profile) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(profile);
        transaction.commit();
        session.close();
    }
}
