package insightjournal.database;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DB_RESOURCE_PATH = "/database/database.db";

    public static Connection createConnection() {
        Connection connection = null;
        try {
            File jarDir = DatabaseUtil.getJarDir();
            File dbFile = DatabaseUtil.copyDatabaseFromResources(DB_RESOURCE_PATH,
                    jarDir.getAbsolutePath() + "/database.db");
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

            connection = DriverManager.getConnection(url);

            if (connection != null) {
                System.out.println("Connection to SQLite database established.");
            }
        } catch (SQLException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
