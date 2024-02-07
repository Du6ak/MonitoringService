package org.du6ak;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.du6ak.configuration.DBConfiguration;

import liquibase.Liquibase;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to create and insert tables using Liquibase.
 */
public class LiquibaseCreateAndInsertTables {
    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();

    /**
     * This method updates the database using Liquibase.
     * @param args command line arguments
     * @throws Exception if there is an error updating the database
     */
    public static void main(String[] args) throws Exception {
        try {
            Statement statement = DB_CONFIG.getStatement();
            Database db =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(statement.getConnection()));

            Liquibase liquibase = new Liquibase("db.changelog/changelog.xml", new ClassLoaderResourceAccessor(), db);
            liquibase.update();
            System.out.println("Таблицы успешно созданы и заполнены!");
        } catch (SQLException e) {
            System.out.println("SQL Exception in migration: " + e.getMessage());
        }
    }
}
