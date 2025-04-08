package team.project.persistence;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.slf4j.LoggerFactory;
import team.project.entity.Profile;

import java.util.List;

/**
 * DAO class for Profile Entity
 * accesses team_project schema and profiles table/entity to perform insert/delete/edit/get operations
 */
public class ProfileDAO {

    // Implementing log4J2
    private final Logger logger = LogManager.getLogger(this.getClass());
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
        if (profile == null) {
            logger.error("Profile with id " + id + " not found");
        } else {
            logger.info("Profile with id " + id + " found");
        }
        return profile;
    }


    /**
     * retrieves a list of all profile records
     * @return list of Profile objects
     */
    public List<Profile> getAllProfiles() {
        logger.debug("Retrieving all profiles");
        Session session = getSession();
        HibernateCriteriaBuilder builder = (HibernateCriteriaBuilder) session.getCriteriaBuilder();
        CriteriaQuery<Profile> criteria = builder.createQuery(Profile.class);
        Root<Profile> root = criteria.from(Profile.class);
        criteria.select(root);
        List<Profile> profiles = session.createQuery(criteria).getResultList();
        session.close();
        logger.info("Retrieved " + profiles.size() + " profiles");
        return profiles;
    }


    /**
     * updates a designated profile
     * @param profile profile to be updated
     */
    public void updateProfile(Profile profile) {
        if (profile == null) {
            logger.error("Profile with id " + profile.getId() + " not found");
            throw new IllegalArgumentException("Profile with id " + profile.getId() + " not found");
        }
        logger.debug("Updating profile with id " + profile.getId());
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(profile);
        transaction.commit();
        session.close();

        logger.info("Profile with id " + profile.getId() + " updated");
    }


    /**
     * inserts a new profile object as a db record
     * @param profile profile object to be added
     */
    public void insertProfile(Profile profile) {
        if (profile == null) {
            logger.error("Profile with id " + profile.getId() + " not found");
            throw new IllegalArgumentException("Profile with id " + profile.getId() + " not found");
        }
        logger.debug("Inserting profile with id " + profile.getId());
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(profile);
        transaction.commit();
        session.close();

        logger.info("Profile with id " + profile.getId() + " inserted");
    }


    /**
     * deletes a profile record
     * @param profile profile object/record to be deleted
     */
    public void deleteProfile(Profile profile) {
        if (profile == null) {
            logger.error("Attempted to delete null profile");
            throw new IllegalArgumentException("Profile can't be null");
        }
        logger.debug("Deleting profile with id " + profile.getId());
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(profile);
        transaction.commit();
        session.close();
    }


}
