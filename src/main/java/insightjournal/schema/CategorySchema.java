package insightjournal.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import insightjournal.database.Database;
import insightjournal.controllers.SettingsController;

public class CategorySchema {
    private int category_id;
    private String name;

    public CategorySchema(int category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public int getCategoryId() {
        return category_id;
    }

    public String getCategoryName() {
        return name;
    }

    public static void insertCategory() {

    }

    public static ArrayList<CategorySchema> fetchCategory() {
        ArrayList<CategorySchema> entries = new ArrayList<>();
        Connection connection = Database.createConnection();
        String sql = "SELECT * FROM category";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                CategorySchema obj = new CategorySchema(resultSet.getInt("category_id"), resultSet.getString("name"));
                entries.add(obj);
            }

            resultSet.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public static void deleteCategory(int category_id) {
        Connection connection = Database.createConnection();
        String sql = "DELETE FROM category WHERE category_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, category_id);

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Category Deleted!");
                ps.close();
                connection.close();
                SettingsController.getInstance().initializeCategoryList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
