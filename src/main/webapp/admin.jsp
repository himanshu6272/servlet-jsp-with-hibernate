<%@page import="models.User, java.util.*, java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="./assets/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/css/style.css">
</head>

<body class="bg-warning">
<%
    response.setHeader("Cache-Control", "no-cache, no-store");
    List<User> users = (List)session.getAttribute("users");
    User admin = (User)session.getAttribute("admin");
    request.setAttribute("admin", admin);
%>

<%@ include file="header.jsp" %>
<div class="container div-cont bg-secondary">
  <div class="row">
    <div class="bg-dark w-100 p-2">
        <h3 class="text-warning d-inline ml-4" style="margin-right:200px;">Admin Dashboard</h3>
        <input type="text" id="myInput" placeholder="Search for names.." class="ml-5">
    </div>
    <table class="table text-warning text-center border-bottom border-white" id="myTable">
      <tr>
        <th style="width:10%;">Sr. No.</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Action</th>
      </tr>
      <c:forEach items="${users}" var="user">
      <tr>
        <td><c:out value="${user.getId()}" /></td>
        <td><c:out value="${user.getFirstName()}" /></td>
        <td><c:out value="${user.getEmail()}" /></td>
        <td><c:out value="${user.getRole()}" /></td>
        <td class="w-25">
        <a href="viewServlet?userId=${user.getId()}" class="btn btn-warning py-1 view-user-btn">View</a>
        <a href="deleteServlet?userId=${user.getId()}" class="btn btn-warning py-1 remove-user-btn">Delete</a>
        </td>
      </tr>
      </c:forEach>
    </table>
  </div>
</div>
<script src="./assets/jquery/jQuery 3.6.4.js"></script>
<script src="./assets/js/admin.js"></script>
<script src="./assets/jquery/jquery-ui.js"></script>
<%@ include file="footer.html" %>
</body>
</html>