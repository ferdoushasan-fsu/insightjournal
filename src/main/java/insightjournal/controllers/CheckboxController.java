package insightjournal.controllers;

import insightjournal.schema.CategorySchema;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class CheckboxController {

    @FXML
    private CheckBox category;

    public void setValue(CategorySchema category) {
        this.category.setText(category.getCategoryName());
        this.category.setUserData(category.getCategoryId());
    }

    public void checkboxButtonHandler() {
        if (CollectionsController.getInstance() != null) {
            CollectionsController.filterByCategory((int) category.getUserData(), (boolean) category.isSelected());
        }
    }

}
