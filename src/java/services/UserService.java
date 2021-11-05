package services;

import dataaccess.UserDB;
import java.util.List;
import models.User;

public class UserService {

    //Creating class to get the email from the database
    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    //Creating the class to add users to user lists
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    //Creating the classes to insert all the parametes that the user has inputed from the website
    public void insert(String email, boolean active, String first_name, String last_name, String password, int roleID) throws Exception {
        User user = new User(email, active, first_name, last_name, password,roleID);
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    //Creating the classes to update the parametes that the user has inputed from the website
    public void update(String email, boolean active, String first_name, String last_name, String password, int roleID) throws Exception {
        User user = new User(email, active, first_name, last_name, password,roleID);
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    //Creating the classes to delete all the parametes that the user has inputed from the website
    public void delete(String email, boolean active, String first_name, String last_name, String password, int roleID) throws Exception {
        User user = new User(email, active, first_name, last_name, password,roleID);
        UserDB userDB = new UserDB();
        userDB.delete(user);
    }

}
