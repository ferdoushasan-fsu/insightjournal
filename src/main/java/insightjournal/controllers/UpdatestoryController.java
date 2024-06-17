package insightjournal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import insightjournal.alert.AlertBox;
import insightjournal.alert.ConfirmationBox;
import insightjournal.schema.CategorySchema;
import insightjournal.schema.StorySchema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class UpdatestoryController implements Initializable {

    @FXML
    private FlowPane categorycontainer;

    @FXML
    private DatePicker date;

    @FXML
    private Text showdate;

    @FXML
    private TextArea storycontent;

    @FXML
    private TextField title;

    private int story_id;

    public void setValue(StorySchema story) {
        story_id = story.getStoryId();
        showdate.setText(story.getStoryDate());
        title.setText(story.getStoryTitle());
        storycontent.setText(story.getStoryContent());

        for (Integer category_id : StorySchema.fetchStoryCategory(story_id)) {
            for (Node node : categorycontainer.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.getUserData() == category_id) {
                        checkBox.setSelected(true);
                    }
                }
            }
        }
    }

    @FXML
    public void updateSaveButtonHandler(ActionEvent event) {
        String date = (this.date.getValue() != null) ? this.date.getValue().toString() : showdate.getText();
        String title = this.title.getText();
        String storycontent = this.storycontent.getText();
        boolean atLeastOneSelected = false;

        for (Node node : categorycontainer.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    atLeastOneSelected = true;
                    break;
                }
            }
        }

        if (!date.isEmpty() && !title.isEmpty() && !storycontent.isEmpty() && atLeastOneSelected) {
            ConfirmationBox confirmationbox = new ConfirmationBox();
            ConfirmationboxController controller = confirmationbox.createConfirmationBox();

            controller.getYesButton().setOnAction(e -> {
                StorySchema.updateStory(story_id, date, title, storycontent);
                StorySchema.insertStoryCategory(story_id, categorycontainer);
                controller.getStage().close();
            });
        } else {
            AlertBox alert = new AlertBox();
            alert.createAlertBox("Please fill up all the fields!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categorycontainer.getChildren().clear();
        try {
            for (CategorySchema category : CategorySchema.fetchCategory()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checkbox.fxml"));
                CheckBox checkBox = loader.load();

                CheckboxController controller = loader.getController();

                controller.setValue(category);

                categorycontainer.getChildren().add(checkBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
