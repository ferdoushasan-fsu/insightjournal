package insightjournal.alert;

import insightjournal.controllers.ConfirmationboxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmationBox {
    public ConfirmationboxController createConfirmationBox() {
        Stage stage = new Stage();
        ConfirmationboxController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmationbox.fxml"));
            VBox vbox = loader.load();

            controller = loader.getController();

            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            controller.setStage(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return controller;
    }
}
