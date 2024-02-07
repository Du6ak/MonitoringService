package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides methods to interact with the database table "roles".
 */
public class Role {
    private static final Role INSTANCE = new Role();

    /**
     * Returns the only instance of this class.
     */
    public static Role getInstance() {
        return INSTANCE;
    }

    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();

    /**
     * Checks if the given role is present in the database.
     *
     * @param role the role to check
     * @return true if the role is present, false otherwise
     * @throws SQLException if there is an error while executing the query
     */
    public boolean isContains(int role) throws SQLException {
        String sql = "SELECT * FROM service.roles WHERE id =" + role;
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        return rs.next();
    }

    /**
     * Returns the name of the role with the given ID.
     *
     * @param roleId the ID of the role
     * @return the name of the role
     * @throws SQLException if there is an error while executing the query
     */
    public String getRoleName(int roleId) throws SQLException {
        String sql = "SELECT name FROM service.roles WHERE id =" + roleId;
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        rs.next();
        return rs.getString("name");
    }
}