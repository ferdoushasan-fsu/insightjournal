package insightjournal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlertboxController {

    @FXML
    private Text message;

    @FXML
    private Button okbutton;

    @FXML
    void clickButtonHandler() {
        stage.close();
    }

    private Stage stage;

    public void setValue(String message) {
        this.message.setText(message);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
