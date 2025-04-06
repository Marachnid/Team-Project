package refactor;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * This file provides a SessionFactory for use with DAOs using Hibernate
 * @version 3.0
 */
public class SessionFactoryProvider {

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    /**Create session factory*/
    public static void createSessionFactory() {

        try {
            registry = new StandardServiceRegistryBuilder().configure().build();    // Create registry
            MetadataSources sources = new MetadataSources(registry);                // Create MetadataSources
            Metadata metadata = sources.getMetadataBuilder().build();               // Create Metadata
            sessionFactory = metadata.getSessionFactoryBuilder().build();           // Create SessionFactory

            System.out.println("SessionFactory created");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SessionFactory creation failed");
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }

    /**
     * Gets session factory.
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }

    /**Close registry*/
    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
