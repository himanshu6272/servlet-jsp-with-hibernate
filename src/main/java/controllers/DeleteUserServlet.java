package controllers;

import models.Constants;
import models.User;
import services.UserService;
import services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteUserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Constants constants = new Constants();
        HttpSession session = req.getSession();
        session.removeAttribute(constants.users);
        User existingUser = (User) session.getAttribute(constants.admin);
        int id = Integer.parseInt(req.getParameter(constants.userId));
        try {
            this.userService.deleteUser(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<User> users = null;
        try {
            users = this.userService.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == existingUser.getId()){
                continue;
            }
            userList.add(users.get(i));
        }
        session.setAttribute(constants.users, userList);
        resp.sendRedirect("admin.jsp");
    }
}
