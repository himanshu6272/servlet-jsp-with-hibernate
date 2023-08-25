<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="models.User, java.util.*, java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="./assets/bootstrap/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-light sticky-top font-weight-bold p-3">
        <a class="navbar-brand ml-5" href="index.jsp"><i class="fa-sharp fa-solid fa-house fa-2xl text-warning"></i></a>
        <c:if test="${admin.getRole() eq 'ADMIN'}"><a href="admin.jsp" class="btn btn-outline-warning ml-5" id="dashboard-btn">Dashboard</a></c:if>
        <div class="mr-5" id="header-div">
            <a href="register.jsp" class="btn btn-outline-warning" id="register-btn">Register</a>
            <a href="viewServlet?userId=${admin.getId()}" class="btn btn-outline-warning d-none" id="update-profile-btn">Edit Profile</a>
            <a href="login.jsp" class="btn btn-outline-warning ml-2" id="login-btn">Login</a>
        </div>
    </nav>
</body>
</html>
