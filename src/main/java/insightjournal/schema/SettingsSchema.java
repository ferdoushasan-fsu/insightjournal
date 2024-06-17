package insightjournal.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import insightjournal.database.Database;

public class SettingsSchema {
    private int id;
    private String user_name;

    public SettingsSchema(int id, String user_name) {
        this.id = id;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return user_name;
    }

    public static void saveSettigns(String user_name) {
        Connection connection = Database.createConnection();
        SettingsSchema obj = fetchSettings();
        String sql = null;

        if (obj == null) {
            sql = "INSERT INTO settings ('user_name') VALUES (?)";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user_name);

                int row = ps.executeUpdate();
                if (row > 0) {
                    System.out.println("Inserted");

                    ps.close();
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sql = "UPDATE settings SET user_name = ? WHERE id = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user_name);
                ps.setInt(2, obj.getId());

                int row = ps.executeUpdate();
                if (row > 0) {
                    System.out.println("Updated");

                    ps.close();
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SettingsSchema fetchSettings() {
        Connection connection = Database.createConnection();
        String sql = "SELECT * FROM settings";
        SettingsSchema obj = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                obj = new SettingsSchema(resultSet.getInt(1), resultSet.getString(2));

                resultSet.close();
                ps.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
