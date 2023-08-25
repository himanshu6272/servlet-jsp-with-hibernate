package controllers;

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
import java.sql.SQLException;
import java.util.Objects;

@MultipartConfig
public class ForgotPasswordController extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    private static final Logger logger = Logger.getLogger(ForgotPasswordController.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Constants constants = new Constants();
        String email = req.getParameter(constants.email);
        String question = req.getParameter(constants.securityQuestion);
        String answer = req.getParameter(constants.securityAnswer);

        User user = null;
        try {
            user = this.userService.getUserByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user.getEmail()==null){
            resp.getWriter().println("User does not exist with this email");
        }else if(user.getSecurityQuestion().equals(question) && user.getSecurityAnswer().equals(answer)){
            session.setAttribute(constants.email, email);
            logger.info("http://localhost:9899/ServletJspTask/reset.jsp");
            resp.getWriter().println("sent");
        }else if (!Objects.equals(user.getSecurityQuestion(), question)){
            resp.getWriter().println("Please enter valid security question!");
        }else if (!Objects.equals(user.getSecurityAnswer(), answer)){
            resp.getWriter().println("Please enter valid security answer!");
        }

    }
}
