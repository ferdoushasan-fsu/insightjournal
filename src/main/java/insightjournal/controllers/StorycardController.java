package insightjournal.controllers;

import insightjournal.schema.StorySchema;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class StorycardController {

    @FXML
    private Text date;

    @FXML
    private Button readMoreButton;

    @FXML
    private Text title;

    private StorySchema story;

    public void setValue(StorySchema story) {
        this.story = story;
        date.setText(story.getStoryDate());
        title.setText(story.getStoryTitle());
    }

    @FXML
    void readMoreHandler() {
        AppController.getInstance().storydetailsScene(story);
    }

}
