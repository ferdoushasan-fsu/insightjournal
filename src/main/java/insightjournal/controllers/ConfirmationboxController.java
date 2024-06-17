package insightjournal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfirmationboxController {

    private Stage stage;

    @FXML
    private Text message;

    @FXML
    private Button yesbutton;

    public Button getYesButton() {
        return yesbutton;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void nobuttonHandler() {
        stage.close();
    }

}
