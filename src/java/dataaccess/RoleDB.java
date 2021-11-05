package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role;

public class RoleDB {
    public List<Role> getAll() throws SQLException {
        
        List<Role> roles = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //Selects all from the role table
        String sql = "SELECT * FROM role";

        try {
            //Connects to the prepare statment and sets it to a "ps" variable
            ps = con.prepareStatement(sql);
            //Executes the query and sets it to a "rs" variable
            rs = ps.executeQuery();
            //Goes through each line in the query
            while (rs.next()) {
                int roleID = rs.getInt(1);
                String roleName = rs.getString(2);
                
                //Sets the roleID and the roleName to the role object then adds it
                Role role = new Role(roleID,roleName);
                roles.add(role);
                

            }
        } finally {
            //Closes everything
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return roles;
    }
    
}
