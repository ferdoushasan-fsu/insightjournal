package insightjournal.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import insightjournal.schema.StorySchema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DashboardController implements Initializable {

    @FXML
    private Button createstory;

    @FXML
    private GridPane recentstory;

    @FXML
    private Text storycount;

    private BorderPane mainContainer;

    public void setMainContainer(BorderPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    @FXML
    void createstory() {
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/fxml/createstory.fxml"));
            mainContainer.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        storycount.setText(StorySchema.storyCount());
        ArrayList<StorySchema> stories = new ArrayList<>(StorySchema.fetchStoryForDashboard());

        int row = 1;
        int column = 0;

        for (StorySchema story : stories) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storycard.fxml"));
                VBox card = loader.load();

                StorycardController controller = loader.getController();
                controller.setValue(story);

                if (column < 3) {
                    recentstory.add(card, column, row);
                    column++;
                } else if (column > 2) {
                    row++;
                    column = 0;
                    recentstory.add(card, column, row);
                    column++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
