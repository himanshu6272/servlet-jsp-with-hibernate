package controllers;

import models.Address;
import models.Constants;
import models.User;
import org.apache.log4j.Logger;
import services.UserService;
import services.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

public class ViewServlet extends HttpServlet {

    private static final long serialVersionUID= -6601341627645607229L;

    private static final Logger logger = Logger.getLogger(ValidationServlet.class);

    private transient Base64.Decoder decoder = Base64.getDecoder();
    private UserService userService = new UserServiceImpl();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Constants constants = new Constants();
        int id = Integer.parseInt(req.getParameter(constants.userId));
        HttpSession session = req.getSession();
        session.setAttribute(constants.updateUserId, id);
        User user = null;
        try {
            user = this.userService.getUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = decoder.decode(user.getPassword());
        String decodedPassword = new String(bytes, StandardCharsets.UTF_8);
        logger.info(decodedPassword);

            User user1 = new User(
                    user.getId(), user.getFirstName(), user.getLastName(), user.getMobile(), user.getEmail(),
                    user.getRole(), user.getDob(), user.getGender(), decodedPassword, user.getSecurityQuestion(), user.getSecurityAnswer(), user.getFileName(), user.getAddresses()
            );

        List<Address> addresses = user1.getAddresses();
        session.setAttribute(constants.user, user1);
        session.setAttribute(constants.addresses, addresses);
        resp.sendRedirect("view.jsp");
    }
}
