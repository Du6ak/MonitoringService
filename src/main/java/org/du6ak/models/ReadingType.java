package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.du6ak.services.exceptions.AlreadyExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides methods for managing reading types in the database.
 */
public class ReadingType {

    /**
     * The only instance of this class.
     */
    private static final ReadingType INSTANCE = new ReadingType();

    /**
     * Returns the only instance of this class.
     *
     * @return the instance
     */
    public static ReadingType getInstance() {
        return INSTANCE;
    }

    /**
     * The database configuration.
     */
    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();

    /**
     * Checks if a reading type with the specified ID exists.
     *
     * @param typeId the ID of the reading type
     * @return true if the reading type exists, false otherwise
     * @throws SQLException if there is an error communicating with the database
     */
    public boolean isContains(int typeId) throws SQLException {
        String sql = "SELECT * FROM service.reading_types WHERE id =" + typeId;
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        return rs.next();
    }

    /**
     * Checks if a reading type with the specified name exists.
     *
     * @param typeName the name of the reading type
     * @return true if the reading type exists, false otherwise
     * @throws SQLException if there is an error communicating with the database
     */
    public boolean isContains(String typeName) throws SQLException {
        String sql = "SELECT * FROM service.reading_types WHERE name ='" + typeName + "';";
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        return rs.next();
    }

    /**
     * Returns the name of the reading type with the specified ID.
     *
     * @param typeId the ID of the reading type
     * @return the name of the reading type, or null if the reading type does not exist
     * @throws SQLException if there is an error communicating with the database
     */
    public String getTypeName(int typeId) throws SQLException {
        String sql = "SELECT name FROM service.reading_types WHERE id =" + typeId;
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        if (rs.next()) {
            return rs.getString("name");
        }
        return null;
    }

    /**
     * Adds a new reading type to the database.
     *
     * @param newTypeName the name of the new reading type
     * @throws SQLException           if there is an error communicating with the database
     * @throws AlreadyExistsException if a reading type with the specified name already exists
     */
    public void addReadingType(String newTypeName) throws SQLException, AlreadyExistsException {
        if (isContains(newTypeName)) {
            throw new AlreadyExistsException();
        }
        Connection connection = DB_CONFIG.getConnection();
        String sql = "INSERT INTO service.reading_types (name) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        {
            statement.setString(1, newTypeName);
        }
        statement.executeUpdate();
    }

    /**
     * Deletes the reading type with the specified ID from the database.
     *
     * @param typeId the ID of the reading type
     * @throws SQLException if there is an error communicating with the database
     */
    public void deleteReadingType(int typeId) throws SQLException {
        String sql = "DELETE FROM service.reading_types WHERE id =" + typeId;
        DB_CONFIG.getStatement().executeUpdate(sql);
    }

}
