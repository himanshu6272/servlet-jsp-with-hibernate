package dao;

import models.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;
import java.io.Serializable;
import java.util.List;

public class UserDao implements Serializable {

    private static final long serialVersionUID = -4360092092454209390L;
    private static final Logger logger = Logger.getLogger(UserDao.class);

    public boolean saveUser(User user){
        logger.info(user);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
        return true;
    }

    public User getByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User u where u.email=:email");
        query.setParameter("email", email);
        User user;
        try {
            user = query.getSingleResult();
        }catch (Exception e){
            user = new User();
        }

        session.close();
        return user;
    }

    public List<User> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User");
        List<User> users = query.getResultList();
        session.close();
        return users;
    }

    public User getById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User u where u.id=:id");
        query.setParameter("id", id);
        User user = query.getSingleResult();
        session.close();
        return user;
    }

    public boolean update(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
        return true;
    }

    public boolean delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("delete User u where u.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.close();
        return true;
    }

    public boolean updatePassword(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query<User> query = session.createQuery("update User u set u.password=:password where u.email=:email");
        query.setParameter("password", user.getPassword());
        query.setParameter("email", user.getEmail());
        query.executeUpdate();
        tx.commit();
        session.close();
        return true;
    }

}
