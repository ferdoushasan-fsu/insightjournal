package insightjournal.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import insightjournal.controllers.CollectionsController;
import insightjournal.database.Database;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

public class StorySchema {
    private int story_id;
    private String date;
    private String title;
    private String content;
    private ArrayList<Integer> categories;

    public StorySchema(int story_id, String date, String title, String content) {
        this.story_id = story_id;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public int getStoryId() {
        return story_id;
    }

    public String getStoryDate() {
        return date;
    }

    public String getStoryTitle() {
        return title;
    }

    public String getStoryContent() {
        return content;
    }

    public ArrayList<Integer> getCategory() {
        this.categories = new ArrayList<>(fetchStoryCategory(story_id));
        return categories;
    }

    public static int insertStory(String date, String title, String content) {
        Connection connection = Database.createConnection();
        String sql = "INSERT INTO story ('title', 'date', 'content') VALUES (?, ?, ?)";
        int storyId = -1;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, date);
            ps.setString(3, content);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println(title + " inserted.");
                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    storyId = resultSet.getInt(1);
                }
                resultSet.close();
            }
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return storyId;
    }

    public static ArrayList<StorySchema> fetchStoryForDashboard() {
        ArrayList<StorySchema> entries = new ArrayList<>();
        Connection connection = Database.createConnection();
        String sql = "SELECT * FROM story ORDER BY story_id DESC LIMIT 6";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                StorySchema obj = new StorySchema(resultSet.getInt("story_id"), resultSet.getString("date"),
                        resultSet.getString("title"), resultSet.getString("content"));
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

    public static void updateStory(int id, String date, String title, String content) {
        Connection connection = Database.createConnection();
        String sql = "UPDATE story SET title=?, date=?, content=? WHERE story_id=?";

        deleteStoryCategory(id);

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, date);
            ps.setString(3, content);
            ps.setInt(4, id);

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

    public static void insertStoryCategory(int story_id, Pane categorycontainer) {
        try {
            Connection connection = Database.createConnection();
            String sql = "INSERT INTO story_category ('story_id', 'category_id') VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Node node : categorycontainer.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected()) {
                        ps.setInt(1, story_id);
                        ps.setInt(2, (int) checkBox.getUserData());

                        ps.executeUpdate();
                    }
                }
            }

            ps.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Integer> fetchStoryCategory(int story_id) {
        ArrayList<Integer> entries = new ArrayList<>();
        Connection connection = Database.createConnection();
        String sql = "SELECT category_id FROM story_category WHERE story_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, story_id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                entries.add(resultSet.getInt("category_id"));
            }

            resultSet.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public static void fetchStoryIdByCategory(int category_id) {
        Connection connection = Database.createConnection();
        String sql = "SELECT story_id FROM story_category WHERE category_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, category_id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (CollectionsController.filteredStories.indexOf(resultSet.getInt("story_id")) < 0) {
                    CollectionsController.filteredStories.add(resultSet.getInt("story_id"));
                }

            }

            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteStoryCategory(int story_id) {
        Connection connection = Database.createConnection();
        String sql = "DELETE FROM story_category WHERE story_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, story_id);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Deleted!");
                ps.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String storyCount() {
        Connection connection = Database.createConnection();
        String sql = "SELECT COUNT(*) AS count FROM story";
        Integer count = 0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
                ps.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count.toString();
    }

    public static void deleteStory(int story_id) {

        Connection connection = Database.createConnection();
        String sql = "DELETE FROM story WHERE story_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, story_id);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Story Deleted!");
                deleteStoryCategory(story_id);
                ps.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
