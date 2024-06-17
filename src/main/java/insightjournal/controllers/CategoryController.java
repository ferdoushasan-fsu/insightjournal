package insightjournal.controllers;

import insightjournal.alert.ConfirmationBox;
import insightjournal.schema.CategorySchema;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CategoryController {

    @FXML
    private Text categoryname;

    @FXML
    private Button deletebutton;

    public void setValue(CategorySchema category) {
        categoryname.setText(category.getCategoryName());
        categoryname.setUserData(category.getCategoryId());
    }

    @FXML
    public void deleteCategoryHandler() {
        ConfirmationBox confirmationbox = new ConfirmationBox();
        ConfirmationboxController controller = confirmationbox.createConfirmationBox();

        controller.getYesButton().setOnAction(e -> {
            CategorySchema.deleteCategory((int) categoryname.getUserData());
            controller.getStage().close();
        });

    }

}
