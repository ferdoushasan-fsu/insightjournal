package insightjournal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        App.launch(args);
    }

    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/app.fxml"));

            Scene scene = new Scene(root, 800, 500);

            primaryStage.setTitle("InsightJournal");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Font.loadFont(getClass().getResourceAsStream("assets\fonts\steelfish\steelfish-eb.otf"),
            // 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}