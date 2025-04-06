package refactor;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;



public class ProfileDAO {

    private Session getSession() { return SessionFactoryProvider.getSessionFactory().openSession(); }



    public ProfileEntity getById(int id) {
        Session session = getSession();
        ProfileEntity profile = session.get(ProfileEntity.class, id);
        session.close();
        return profile;
    }


    public List<ProfileEntity> getAllProfiles() {
        Session session = getSession();
        HibernateCriteriaBuilder builder = (HibernateCriteriaBuilder) session.getCriteriaBuilder();
        CriteriaQuery<ProfileEntity> criteria = builder.createQuery(ProfileEntity.class);
        Root<ProfileEntity> root = criteria.from(ProfileEntity.class);
        return session.createQuery(criteria).list();
    }


    public void updateProfile(ProfileEntity profile) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(profile);
        transaction.commit();
        session.close();
    }


    public void insertProfile(ProfileEntity profile) {

        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(profile);
        transaction.commit();
        session.close();

    }


    public void deleteProfile(ProfileEntity profile) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(profile);
        transaction.commit();
        session.close();
    }


}
