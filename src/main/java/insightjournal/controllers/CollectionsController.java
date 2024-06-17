package insightjournal.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import insightjournal.database.Database;
import insightjournal.schema.CategorySchema;
import insightjournal.schema.StorySchema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CollectionsController implements Initializable {

    private static CollectionsController instance;

    public static CollectionsController getInstance() {
        return instance;
    }

    @FXML
    private FlowPane categorycontainer;

    @FXML
    private GridPane storycontainer;

    private ArrayList<StorySchema> stories;

    public static ArrayList<Integer> filteredCategories = new ArrayList<>();

    public static ArrayList<Integer> filteredStories = new ArrayList<>();

    public static void filterByCategory(int category_id, boolean isSelected) {
        CollectionsController.filteredStories.clear();

        if (isSelected) {
            filteredCategories.add(category_id);

            for (Integer integer : filteredCategories) {
                StorySchema.fetchStoryIdByCategory(integer);
            }

            CollectionsController.getInstance().filterStoriesByCategory(false);
        } else {
            for (Integer integer : filteredCategories) {
                if (category_id == integer) {
                    filteredCategories.remove(integer);
                    break;
                }
            }

            if (!filteredCategories.isEmpty()) {

                for (Integer integer : filteredCategories) {
                    StorySchema.fetchStoryIdByCategory(integer);
                }

                CollectionsController.getInstance().filterStoriesByCategory(false);
            } else {
                CollectionsController.getInstance().filterStoriesByCategory(true);
            }

        }
    }

    public void filterStoriesByCategory(boolean isSeletAll) {
        storycontainer.getChildren().clear();
        int row = 1;
        int column = 0;

        try {
            if (!isSeletAll) {
                for (Integer integer : filteredStories) {
                    for (StorySchema story : stories) {
                        if (story.getStoryId() == integer) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storycard.fxml"));
                            VBox storycard = loader.load();

                            StorycardController controller = loader.getController();

                            controller.setValue(story);

                            if (column <= 2) {
                                storycontainer.add(storycard, column, row);
                                column++;
                            } else if (column > 2) {
                                column = 0;
                                row++;
                                storycontainer.add(storycard, column, row);
                                column++;
                            }
                        }
                    }
                }
            } else {
                for (StorySchema story : stories) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storycard.fxml"));
                    VBox storycard = loader.load();

                    StorycardController controller = loader.getController();

                    controller.setValue(story);

                    if (column <= 2) {
                        storycontainer.add(storycard, column, row);
                        column++;
                    } else if (column > 2) {
                        column = 0;
                        row++;
                        storycontainer.add(storycard, column, row);
                        column++;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StorySchema> fetchStory() {
        ArrayList<StorySchema> entries = new ArrayList<>();
        Connection connection = Database.createConnection();
        String sql = "SELECT * FROM story";
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        ArrayList<CategorySchema> categories = new ArrayList<>(CategorySchema.fetchCategory());
        stories = new ArrayList<>(fetchStory());
        filteredCategories.clear();

        int row = 1;
        int column = 0;

        try {
            for (CategorySchema category : categories) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checkbox.fxml"));
                CheckBox checkbox = loader.load();

                CheckboxController controller = loader.getController();

                controller.setValue(category);

                categorycontainer.getChildren().add(checkbox);
            }

            for (StorySchema story : stories) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storycard.fxml"));
                VBox storycard = loader.load();

                StorycardController controller = loader.getController();

                controller.setValue(story);

                if (column <= 2) {
                    storycontainer.add(storycard, column, row);
                    column++;
                } else if (column > 2) {
                    column = 0;
                    row++;
                    storycontainer.add(storycard, column, row);
                    column++;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
