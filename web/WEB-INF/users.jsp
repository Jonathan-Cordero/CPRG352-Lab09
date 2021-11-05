<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/style.css"%></style>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="wrapper">
            <div class="userInputBoxWrapper">
                <div class="userInputBox">
                    <%-- Left side of the page to add the user--%>
                    <h3>Add User</h3>
                    <form method="POST" method="users">
                        <input type="text" name="firstname" placeholder="Firstname"/> <br/>
                        <input type="text" name="lastname" placeholder="Lastname"/><br/>
                        <input type="text" name="email" placeholder="Email"/><br/>
                        <input type="text" name="password" placeholder="Password"/><br/>
                        <select name="role">
                            <option value="1">System admin</option>
                            <option value="2">Regular user</option>
                            <option value="3">Company admin</option>
                        </select>
                        <br/>
                        <input type="checkbox"  name="active" value="active">
                        <label>Active</label> <br/>
                        <input type="submit" value="Add"/>
                        <input type ="hidden" name="ActionInput" value="add"/>
                    </form>
                </div>
            </div>
            <div class="userDisplayWrapper">
                <div class="userDisplay">
                    <%-- Middle of the page that shows the users--%>
                    <h3>Manage User</h3>
                    <table cellpadding="7" border="1">
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Active</th>
                            <th>Role_id</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <%-- Gets the following parameter from user class and adds them to the table--%>
                                <td>${user.getFirstName()}</td>
                                <td>${user.getLastName()}</td>
                                <td>${user.getEmail()}</td>
                                <td>${user.getPassword()}</td>
                                <td>${user.isActive()}</td>
                                <td>${user.getRole()}</td>
                                <td>
                                    <%-- Allows you to edit the user by getting their email and edditing their parameters--%>
                                    <a href="users?action=edit&em=${user.getEmail()}">Edit</a>
                                </td>
                                <td>
                                    <%-- Allows you to delete the user by getting their email--%>
                                    <a href="users?action=delete&em=${user.getEmail()}">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br/>
                    <p>Note for role_id:</p>
                    <p>1: System admin</p>
                    <p>2: Regular user</p>
                    <p>3: Company admin</p>   
                </div>
            </div>
            <div class="editInfoWrapper">
                <div class="editInfo">
                    <%-- Right side of the page to edit the user--%>
                    <h3>Edit User</h3>
                    <%-- Allows you to edit the user then added the parameter to the value--%>
                    <form method="post" action="users">
                        <input type="text" name="firstname_edit" placeholder="Firstname" value="${firstname_edit}"/> <br/>
                        <input type="text" name="lastname_edit" placeholder="Lastname" value="${lastname_edit}"/><br/>
                        <input type="text" name="email_edit" placeholder="Email" value="${email_edit}" readonly/><br/>
                        <input type="text" name="password_edit" placeholder="Password" value="${password_edit}"/><br/>
                        <%-- JSTL to verify the users chose based on the selected item--%>
                        <c:choose>
                            <c:when test="${role_edit == 1}">
                                <select  name="role_edit">
                                    <option value="1" selected="selected">System admin</option>
                                    <option value="2">Regular user</option>
                                    <option value="3">Company admin</option>
                                </select>
                            </c:when>
                            <c:when test="${role_edit == 2}">
                                <select  name="role_edit">
                                    <option value="1" >System admin</option>
                                    <option value="2" selected="selected">Regular user</option>
                                    <option value="3">Company admin</option>
                                </select>
                            </c:when>
                            <c:when test="${role_edit == 3}">
                                <select  name="role_edit">
                                    <option value="1" >System admin</option>
                                    <option value="2" >Regular user</option>
                                    <option value="3" selected="selected">Company admin</option>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <select name="role_edit">
                                    <option value="1" >System admin</option>
                                    <option value="2" >Regular user</option>
                                    <option value="3" >Company admin</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                        <br/>
                        <%-- JSTL to chanse the ative status--%>
                        <c:choose>
                            <c:when test="${activeStatus}">
                                <input type="checkbox"  name="active_edit" value="active" checked/>
                                <label>Active</label> <br/>
                            </c:when>
                            <c:when test="${!activeStatus}">
                                <input type="checkbox"  name="active_edit" value="active" />
                                <label>Active</label> <br/>
                            </c:when>
                        </c:choose>
                        <input type="submit" value="Save"/>
                        <input type ="hidden" name="ActionInput" value="edit"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
