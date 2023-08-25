package controllers;

import models.Address;
import models.Constants;
import models.User;
import org.apache.log4j.Logger;
import services.UserService;
import services.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

@MultipartConfig
public class AuthController extends HttpServlet {

    private static final long serialVersionUID= 2677845101220700857L;
    private static final Logger log = Logger.getLogger(AuthController.class);
    private UserService userService = new UserServiceImpl();
    private transient Base64.Decoder decoder = Base64.getDecoder();
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Constants constants = new Constants();
        String email = req.getParameter(constants.email);
        log.info(email);
        String password = req.getParameter(constants.password);
        log.info(password);
        User user = null;
        try {
            user = this.userService.getUserByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info(user);
        log.info(user.getId());
        if (user.getId() == 0){
            resp.getWriter().write("User does not exist with this email, please create one!!");
            return;
        }
        String existingUserPassword = user.getPassword();
        log.info(existingUserPassword);
        byte[] bytes = decoder.decode(existingUserPassword);
        String decodedPassword = new String(bytes, StandardCharsets.UTF_8);
        log.info(decodedPassword);
        User existingUser = new User(
                user.getId(), user.getFirstName(), user.getLastName(), user.getMobile(), user.getEmail(),
                user.getRole(), user.getDob(), user.getGender(), decodedPassword, user.getSecurityQuestion(), user.getSecurityAnswer(), user.getFileName(), user.getAddresses()
        );
        HttpSession session = req.getSession();

        if (password.equals(decodedPassword)){
            if (existingUser.getRole().equals(constants.ADMIN)){
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
                session.setAttribute(constants.loggedInUser, constants.admin);
                session.setAttribute(constants.admin, existingUser);
                resp.getWriter().write(constants.admin);
            }else {
                List<Address> addresses = existingUser.getAddresses();
                session.setAttribute(constants.user, existingUser);
                session.setAttribute(constants.addresses, addresses);
                session.setAttribute(constants.updateUserId, existingUser.getId());
                session.setAttribute(constants.loggedInUser, constants.user);
                resp.getWriter().write(constants.user);
            }
        }else {
            log.error("Invalid Credentials");
            resp.getWriter().write("Invalid Credentials");
        }

    }
}
