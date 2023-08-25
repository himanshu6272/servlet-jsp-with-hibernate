package utils;

import models.Address;
import models.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

            if (sessionFactory == null){
                try{
                    Configuration configuration = new Configuration();
                    Properties properties = new Properties();
                    properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    properties.put(Environment.URL, "jdbc:mysql://localhost:3306/sjwhdb");
                    properties.put(Environment.USER, "root");
                    properties.put(Environment.PASS, "Vinay@163");
                    properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                    properties.put(Environment.SHOW_SQL, "true");
                    properties.put(Environment.HBM2DDL_AUTO, "update");

                    configuration.setProperties(properties);
                    configuration.addAnnotatedClass(User.class);
                    configuration.addAnnotatedClass(Address.class);

                    ServiceRegistry registry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    sessionFactory = configuration.buildSessionFactory(registry);
                    return sessionFactory;
                }catch (Exception e){
                    log.error(e);
                }
            }
        return sessionFactory;
    }
}
