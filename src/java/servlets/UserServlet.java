package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If the add button equals nothing then it will bring up the database and add it to the manager user table
        if (request.getParameter("action") == null) {

            UserService us = new UserService();
            try {
                HttpSession session = request.getSession();
                List<User> users = us.getAll();
                //Sets the users attibutes to users.
                request.setAttribute("users", users);

            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                //Sets an error message if is an exception
                request.setAttribute("message", "error");
            }
        } else if (request.getParameter("action").equals("edit")) {
            UserService us = new UserService();
            try {
                HttpSession session = request.getSession();
                List<User> users = us.getAll();
                request.setAttribute("users", users);
                String emailSelected = request.getParameter("em").replace(" ", "+");
                //Gets the users information based off of the email
                User user = us.get(emailSelected);

                //Sets the attributes to the edit the useres parameters
                request.setAttribute("role_edit", user.getRole());
                request.setAttribute("firstname_edit", user.getFirstName());
                request.setAttribute("lastname_edit", user.getLastName());
                request.setAttribute("email_edit", user.getEmail());
                request.setAttribute("password_edit", user.getPassword());
                request.setAttribute("activeStatus", user.isActive());               
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                //Sets an error message if is an exception
                request.setAttribute("message", "error");
            }
        }else if(request.getParameter("action").equals("delete")){
            UserService us = new UserService();
            try{
                HttpSession session = request.getSession();
                List<User> users = us.getAll();
                request.setAttribute("users", users);
                String emailSelected = request.getParameter("em").replace(" ","+"); 
                //Gets the users information based off of the email
                User user = us.get(emailSelected);            
                us.delete(user.getEmail(), user.isActive(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole());
                users = us.getAll();
                request.setAttribute("users", users);       
            }catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                //Sets an error message if is an exception
                request.setAttribute("message", "error");
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        try {
            HttpSession session = request.getSession();
            List<User> users = us.getAll();
            //If the user click on the "add" button the fields will be added
            if (request.getParameter("ActionInput").equals("add")) {
                try {
                    //Gets the parameters based on the email
                    String email = request.getParameter("email");
                    boolean active = false;
                    //If the active button is clicked, active button set to true
                    if (request.getParameter("active") != null) {
                        active = true;
                    }
                    String first_name = request.getParameter("firstname");
                    String last_name = request.getParameter("lastname");
                    String password = request.getParameter("password");
                    int roleID = Integer.parseInt(request.getParameter("role"));
                    //If all the parameters are empty then insert the following fields
                    if (!first_name.trim().isEmpty() && !last_name.trim().isEmpty() && !password.trim().isEmpty() && !email.trim().isEmpty()) {
                        User user = new User(email, active, first_name, last_name, password, roleID);
                        us.insert(email, active, first_name, last_name, password, roleID);
                        users.add(user);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (request.getParameter("ActionInput").equals("edit")) {
                try {
                    //If the user click on the "edit" button the fields will be edited
                    String email = request.getParameter("email_edit");
                    boolean active = false;
                    //If the active button is clicked, active button set to true
                    if (request.getParameter("active_edit") != null) {
                        active = true;
                    }
                    String first_name = request.getParameter("firstname_edit");
                    String last_name = request.getParameter("lastname_edit");
                    String password = request.getParameter("password_edit");
                    int roleID = Integer.parseInt(request.getParameter("role_edit"));
                    //If all the parameters are empty then edit the following fields
                    if (!first_name.trim().isEmpty() && !last_name.trim().isEmpty() && !password.trim().isEmpty() && !email.trim().isEmpty()) {
                        User user = new User(email, active, first_name, last_name, password, roleID);
                        us.update(email, active, first_name, last_name, password, roleID);
                        users = us.getAll();                        
                    }                   
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }
}
