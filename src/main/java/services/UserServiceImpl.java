package services;

import dao.UserDao;
import models.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    public static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    public UserDao userDao = new UserDao();
    public int registerUser(User user) throws SQLException {
        int status = 0;
        if (userDao.saveUser(user)){
            logger.info("user saved successfully");
            status = 1;

        }else {
            logger.error("something went wrong");
        }
        return status;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = this.userDao.getAll();
        return users;
    }


    public User getUserByEmail(String email) throws SQLException {
        User user = this.userDao.getByEmail(email);
        return user;
    }

    public User getUserById(int id) throws SQLException {
        User user = this.userDao.getById(id);
        return user;
    }

    public void updateUser(User user) throws SQLException {
        if(this.userDao.update(user)){
            logger.info("user updated successfully");
        }else {
            logger.error("something went wrong");
        }

    }


    public void updateUserPassword(User user) throws SQLException {
        if(this.userDao.updatePassword(user)){
            logger.info("password changed successfully");
        }else {
            logger.error("something went wrong");
        }
    }

    public void deleteUser(int id) throws SQLException {
        if (this.userDao.delete(id)){
            logger.info("user deleted successfully");
        }else {
            logger.error("something went wrong");
        }
    }
}
