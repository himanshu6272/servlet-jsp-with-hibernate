package services;

import models.User;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface UserService extends Serializable {
    int registerUser(User user) throws SQLException;

    List<User> getAllUsers() throws SQLException;
    User getUserByEmail(String email) throws SQLException;
    User getUserById(int id) throws SQLException;
    void updateUser(User user) throws SQLException;
    void updateUserPassword(User user) throws SQLException;

    void deleteUser(int id) throws SQLException;

}
