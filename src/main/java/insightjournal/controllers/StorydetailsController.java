package insightjournal.controllers;

import java.util.ArrayList;

import insightjournal.alert.ConfirmationBox;
import insightjournal.schema.CategorySchema;
import insightjournal.schema.StorySchema;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class StorydetailsController {

    @FXML
    private FlowPane categorycontainer;

    @FXML
    private Text date;

    @FXML
    private Text thestory;

    @FXML
    private Text title;

    private int story_id;

    private StorySchema story;

    public void setValue(StorySchema story) {
        story_id = story.getStoryId();
        title.setText(story.getStoryTitle());
        date.setText(story.getStoryDate());
        thestory.setText(story.getStoryContent());

        this.story = story;

        ArrayList<CategorySchema> categories = new ArrayList<>(CategorySchema.fetchCategory());

        for (Integer category_id : story.getCategory()) {
            for (CategorySchema category : categories) {
                if (category.getCategoryId() == category_id) {
                    Text text = new Text(category.getCategoryName());
                    text.setStyle(
                            "-fx-font-size: 16px; -fx-font-family: Outfit;");

                    categorycontainer.getChildren().add(text);
                }
            }
        }
    }

    public void updateButtonHandler() {
        AppController.getInstance().updateStoryScene(story);
    }

    public void deleteButtonHandler() {
        ConfirmationBox confirmationbox = new ConfirmationBox();
        ConfirmationboxController controller = confirmationbox.createConfirmationBox();

        controller.getYesButton().setOnAction(e -> {
            StorySchema.deleteStory(story_id);
            controller.getStage().close();
            AppController.getInstance().collectionsScene();
        });
    }

}
