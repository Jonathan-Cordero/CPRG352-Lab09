package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserDB {

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Selects all from the user table
        String sql = "SELECT * FROM user";

        try {
             //Connects to the prepare statment and sets it to a "ps" variable
            ps = con.prepareStatement(sql);
            //Executes the query and sets it to a "rs" variable
            rs = ps.executeQuery();
            //Goes through each line in the query and sets each line to is appropriate variable
            while (rs.next()) {
                String email = rs.getString(1);
                Boolean active = rs.getBoolean(2);
                String firstname = rs.getString(3);
                String lastname = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                //Addes everything to the user object
                User user = new User(email, active, firstname, lastname, password,roleID);
                users.add(user);
            }
        } finally {
            //Closes everything
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return users;
    }

    public User get(String email) throws Exception {
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Creates a select string from all the user table where the email equals the requested users email
        String sql = "SELECT * FROM user WHERE email=?";

        try {
            //Connects to the prepare statment and sets it to a "ps" variable
            ps = con.prepareStatement(sql);
            //Sets the String to 1 and the users email
            ps.setString(1, email);
            //Executes the Query
            rs = ps.executeQuery();
            if (rs.next()) {
                
                Boolean active = rs.getBoolean(2);
                String firstname = rs.getString(3);
                String lastname = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                user = new User(email, active, firstname, lastname, password,roleID);
            }
        } finally {
            //Closes everything
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return user;
    }

    public void insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //Creates a sql string to insert the users values in the table
        String sql = "INSERT INTO user (email,active, first_name, last_name,password,role) VALUES (?,?,?,?,?,?)";

        try {
            //Addes everything to the table bases on the parameters
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setBoolean(2, user.isActive());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getRole());
            ps.executeUpdate();
        } finally {
            //Closes everything
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //Creates a sql string to update the users values in the table
        String sql = "UPDATE user SET active=?, first_name=?,last_name=?,password=?,role=? where email =? ";

        try {
            //Addes everything to the table bases on the parameters
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, user.isActive());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole());
            ps.setString(6,user.getEmail());
            ps.executeUpdate();
        } finally {
            //Closes everything
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //Creates a sql string to delete the users values in the table based on the email
        String sql = "DELETE FROM user WHERE email=?";

        try {
            //Deletes everything from the table bases on the users email
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } finally {
            //Closes everything
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
