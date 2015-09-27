package utils;

import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY;
    private static final ServiceRegistry SERVICE_REGISTRY;

    private static Session mSession;

    static {
        try {
            Configuration configuration = getConfiguration();

            SERVICE_REGISTRY = new ServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).buildServiceRegistry();
            SESSION_FACTORY = configuration.buildSessionFactory(SERVICE_REGISTRY);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session openSession() throws HibernateException {

        mSession = SESSION_FACTORY.openSession();

        System.out.printf("Session is %s \n", mSession.isOpen());

        return mSession;
    }

    public static void close()
    {
        mSession.close();

        System.out.printf("Session is %s \n", mSession.isConnected());
    }

    public static Session getCurrentSession(){
        Session session = SESSION_FACTORY.getCurrentSession();
        return  session;
    }

    public static Configuration getConfiguration() {
        Configuration cfg = new Configuration();

        cfg.addAnnotatedClass(User.class);
        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/mmorpg-postgres");
        cfg.setProperty("hibernate.connection.username", "postgres");
        cfg.setProperty("hibernate.connection.password", "qwerty01");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        cfg.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        cfg.setProperty("hibernate.current_session_context_class", "thread");

        return cfg;
    }
}
