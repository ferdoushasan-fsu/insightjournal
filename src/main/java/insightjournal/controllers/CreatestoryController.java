package insightjournal.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import insightjournal.alert.AlertBox;
import insightjournal.alert.ConfirmationBox;
import insightjournal.schema.CategorySchema;
import insightjournal.schema.StorySchema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class CreatestoryController implements Initializable {

    @FXML
    private FlowPane categorycontainer;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea storycontent;

    @FXML
    private TextField title;

    private ArrayList<CategorySchema> categories;

    @FXML
    public void insertStoryHandler() {
        String story_date = (date.getValue() != null) ? date.getValue().toString() : "";
        String story_title = title.getText();
        String story_content = storycontent.getText();
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

        if (!story_date.isEmpty() && !story_title.isEmpty() && !story_content.isEmpty() && atLeastOneSelected) {
            ConfirmationBox confirmationbox = new ConfirmationBox();
            ConfirmationboxController controller = confirmationbox.createConfirmationBox();

            controller.getYesButton().setOnAction(e -> {
                int story_id = StorySchema.insertStory(story_date, story_title, story_content);
                StorySchema.insertStoryCategory(story_id, categorycontainer);
                controller.getStage().close();

                date.setValue(null);
                title.setText(null);
                storycontent.setText(null);
                for (Node node : categorycontainer.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) node;
                        checkBox.setSelected(false);
                    }
                }

            });

        } else {
            AlertBox alertBox = new AlertBox();
            alertBox.createAlertBox("Please Fill up All Field!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categorycontainer.getChildren().clear();
        categories = new ArrayList<>(CategorySchema.fetchCategory());
        try {
            for (CategorySchema category : categories) {
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
