package utils;

import model.User;
import model.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
    private static final SessionFactory FACTORY;
    static {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");

        conf.addAnnotatedClass(User.class);
        conf.addAnnotatedClass(Product.class);
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);

    }

    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }
}
