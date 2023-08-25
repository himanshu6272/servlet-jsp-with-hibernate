package controllers;

import models.Address;
import models.Constants;
import models.User;
import org.apache.log4j.Logger;
import services.UserService;
import services.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@MultipartConfig
public class UserController extends HttpServlet {
    private static final Logger log = Logger.getLogger(User.class);

    private static final long serialVersionUID= 3522684328714479049L;
    public UserService userService = new UserServiceImpl();
//    public AddressService addressService = new AddressServiceImpl();
    private transient Base64.Encoder encoder = Base64.getEncoder();
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Constants constants = new Constants();
        HttpSession session = req.getSession();
        Object userObjId = session.getAttribute(constants.updateUserId);
        int updateUserId = 0;
        if (userObjId != null){
            updateUserId = (int) userObjId;
        }

//        ---------getting all form fields---------


        String firstname = req.getParameter(constants.firstname);
        String lastname = req.getParameter(constants.lastname);
        String email = req.getParameter(constants.email);
        if (email == null){
            try {
                email = this.userService.getUserById(updateUserId).getEmail();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String mobile = req.getParameter(constants.mobile);
        String password = req.getParameter(constants.password);
        String encodedPassword = encoder.encodeToString(password.getBytes(Charset.forName("UTF-8")));
        String role = req.getParameter(constants.role);
        if (role == null){
            try {
                role = this.userService.getUserById(updateUserId).getRole();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String dob = req.getParameter(constants.dob);
        String gender = req.getParameter(constants.gender);
        String question = req.getParameter(constants.securityQuestion);
        String answer = req.getParameter(constants.securityAnswer);
        Part profilePhoto = req.getPart(constants.profilePhoto);
        String fileName = profilePhoto.getSubmittedFileName();

//        ---------getting all address fields---------

        String[] street = req.getParameterValues(constants.street);
        String[] city = req.getParameterValues(constants.city);
        String[] state = req.getParameterValues(constants.state);
        String[] zip = req.getParameterValues(constants.zip);
        String[] country = req.getParameterValues(constants.country);
        String[] addressId = req.getParameterValues(constants.addressId);

        User user = new User();
        List<Address> addresses = new ArrayList<>();
        if(userObjId == null){
            for (int i= 0; i<street.length;i++){
                Address address = new Address(street[i], city[i], state[i], zip[i], country[i]);
                address.setUser(user);
                addresses.add(address);
            }
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setMobile(mobile);
            user.setEmail(email);
            user.setRole(role);
            user.setDob(dob);
            user.setGender(gender);
            user.setPassword(encodedPassword);
            user.setSecurityQuestion(question);
            user.setSecurityAnswer(answer);
            user.setFileName(fileName);
            user.setAddresses(addresses);

            try {
                if (this.userService.registerUser(user) == 1){
                    log.info("user registered");
                    String path = getServletContext().getRealPath("") + "profilePhotos";
                    profilePhoto.write(path+File.separator+fileName);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        resp.getWriter().write("done");
        }else {
            for (int i= 0; i<street.length;i++){
                Address address;
                if (Objects.equals(addressId[i], "")){
                    address = new Address(street[i], city[i], state[i], zip[i], country[i]);
                }else {
                    address = new Address(Integer.parseInt(addressId[i]), street[i], city[i], state[i], zip[i], country[i]);
                }
                address.setUser(user);
                addresses.add(address);
            }
            user.setId(updateUserId);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setMobile(mobile);
            user.setEmail(email);
            user.setRole(role);
            user.setDob(dob);
            user.setGender(gender);
            user.setPassword(encodedPassword);
            user.setSecurityQuestion(question);
            user.setSecurityAnswer(answer);
            user.setFileName(fileName);
            user.setAddresses(addresses);
//            String[] addressId = req.getParameterValues(constants.addressId);
//            User user = new User(updateUserId, firstname,lastname,mobile,email,role,dob,gender,encodedPassword, question, answer, fileName, a);
            try {
                this.userService.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String path = getServletContext().getRealPath("") + "profilePhotos";
            profilePhoto.write(path+File.separator+fileName);
//            for (int i = 0; i < street.length; i++) {
//
//                if (addressId == null) {
//                        Address address = new Address(updateUserId, street[i], city[i], state[i], zip[i], country[i]);
//                    try {
//                        this.addressService.saveAddress(address);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                        if (Objects.equals(addressId[i], "")){
//                            Address address = new Address(updateUserId, street[i], city[i], state[i], zip[i], country[i]);
//                            try {
//                                this.addressService.saveAddress(address);
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }else {
//                            int aId = Integer.parseInt(addressId[i]);
//                            Address address = new Address(aId, updateUserId, street[i], city[i], state[i], zip[i], country[i]);
//                            try {
//                                this.addressService.updateAddress(address);
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    }
//                }
//
            session.removeAttribute(constants.user);
            session.removeAttribute(constants.addresses);
            User user1 = null;
            try {
                user1 = this.userService.getUserById(updateUserId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<Address> user1Addresses = user1.getAddresses();
            session.setAttribute(constants.user, user1);
            session.setAttribute(constants.addresses, user1Addresses);
            resp.getWriter().write("updated");
        }

    }


}
