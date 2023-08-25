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
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Base64;
@MultipartConfig
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID= 754203001383842251L;

    private UserService userService = new UserServiceImpl();
    private static final Logger logger = Logger.getLogger(ForgotPasswordController.class);

    private transient Base64.Encoder encoder = Base64.getEncoder();

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Constants constants = new Constants();
        String email = req.getParameter(constants.email);
        logger.info(email);
        String password = req.getParameter(constants.password);
        String encodedPassword = encoder.encodeToString(password.getBytes(Charset.forName("UTF-8")));
        if (email.equals("null")){
            resp.getWriter().println("Reset link has been expired, please generate new one!!");
        }else {
            User user = new User(email, encodedPassword);
            try {
                this.userService.updateUserPassword(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.getWriter().println("reset");
        }
    }
}
