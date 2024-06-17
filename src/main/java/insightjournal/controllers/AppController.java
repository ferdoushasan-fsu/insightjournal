package insightjournal.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import insightjournal.schema.StorySchema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AppController implements Initializable {

    private static AppController instance;

    public static AppController getInstance() {
        return instance;
    }

    @FXML
    private Button collections;

    @FXML
    private Button dashboard;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private Button settings;

    @FXML
    void collectionsScene() {
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/fxml/collections.fxml"));
            mainContainer.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dashboardScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            VBox root = loader.load();

            DashboardController controller = loader.getController();
            controller.setMainContainer(mainContainer);

            mainContainer.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void settingsScene() {
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/fxml/settings.fxml"));
            mainContainer.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storydetailsScene(StorySchema story) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storydetails.fxml"));
            ScrollPane root = loader.load();
            StorydetailsController controller = loader.getController();
            controller.setValue(story);
            mainContainer.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStoryScene(StorySchema story) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/updatestory.fxml"));
            VBox root = loader.load();

            UpdatestoryController controller = loader.getController();
            controller.setValue(story);

            mainContainer.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        dashboardScene();
    }

}
