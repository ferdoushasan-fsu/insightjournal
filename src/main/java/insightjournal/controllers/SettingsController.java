package insightjournal.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import insightjournal.alert.AlertBox;
import insightjournal.database.Database;
import insightjournal.schema.CategorySchema;
import insightjournal.schema.SettingsSchema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsController implements Initializable {

    public static SettingsController instance;

    public static SettingsController getInstance() {
        return instance;
    }

    @FXML
    private TextField catagorytf;

    @FXML
    private VBox categorycontainer;

    @FXML
    private Text categoryname;

    @FXML
    private TextField nametf;

    public void initializeCategoryList() {
        categorycontainer.getChildren().clear();
        ArrayList<CategorySchema> categories = new ArrayList<>(CategorySchema.fetchCategory());
        try {
            for (CategorySchema category : categories) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
                HBox hbox = loader.load();

                CategoryController controller = loader.getController();

                controller.setValue(category);

                categorycontainer.getChildren().add(hbox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void insertCatagoryHandler() {
        if (!catagorytf.getText().isEmpty()) {
            Connection connection = Database.createConnection();
            String sql = "INSERT INTO category ('name') VALUES (?)";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, catagorytf.getText());

                int row = ps.executeUpdate();

                if (row > 0) {
                    System.out.println(catagorytf.getText() + " inserted.");
                    initializeCategoryList();
                    ps.close();
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AlertBox alertbox = new AlertBox();
            alertbox.createAlertBox("Please Insert Category!");

        }

    }

    @FXML
    public void saveData() {
        if (!nametf.getText().isEmpty()) {
            SettingsSchema.saveSettigns(nametf.getText());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        SettingsSchema obj = SettingsSchema.fetchSettings();
        if (obj != null) {
            nametf.setText(obj.getUserName());
        }

        initializeCategoryList();
    }

}
