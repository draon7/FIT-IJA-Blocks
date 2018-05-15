package ija.ija2017.ui.dialogs.ui;

import ija.ija2017.ui.dialogs.data.DataFighterDialog;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Class representing dialog for entering FighterData
 */
public class FighterDialog {
    private Dialog<DataFighterDialog> fighterDialog;
    private DialogPane dialogPane;
    private TextField healthField;
    private TextField powerField;
    private TextField dexterityField;
    private TextField intelligenceField;

    /**
     * Constructor for FighterDialog, it creates new dialog, sets title
     * and result converter
     */
    public FighterDialog(){
        fighterDialog = new Dialog<>();
        fighterDialog.setTitle("Fighter");
        dialogPane = fighterDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.APPLY);

        Node ui = createUI();
        dialogPane.setContent(ui);

        fighterDialog.setResultConverter((ButtonType button) -> {
            if(healthField.getText().isEmpty())return null;
            if(powerField.getText().isEmpty())return null;
            if(dexterityField.getText().isEmpty())return null;
            if(intelligenceField.getText().isEmpty())return null;
            if(button == ButtonType.APPLY){
                return new DataFighterDialog(
                        Double.parseDouble(healthField.getText()),
                        Double.parseDouble(powerField.getText()),
                        Double.parseDouble(dexterityField.getText()),
                        Double.parseDouble(intelligenceField.getText()));
            }
            return null;
        });
    }

    /**
     * Class shows dialog and waits for data
     * @return returns entered data
     */
    public DataFighterDialog showAndWait(){
        healthField.clear();
        powerField.clear();
        dexterityField.clear();
        intelligenceField.clear();
        Optional<DataFighterDialog> newData = fighterDialog.showAndWait();
        unlockFighterField();
        if(newData.isPresent()){
            return newData.get();
        }
        return null;
    }

    /**
     * Class for locking input fields
     */
    public void lockAttackField(){
        healthField.setEditable(false);
        powerField.setEditable(false);
        dexterityField.setEditable(false);
        intelligenceField.setEditable(false);
        healthField.setPromptText("Connected");
        powerField.setPromptText("Connected");
        dexterityField.setPromptText("Connected");
        intelligenceField.setPromptText("Connected");
    }

    /**
     * Method unlock fighter field
     */
    public void unlockFighterField(){
        healthField.setEditable(true);
        powerField.setEditable(true);
        dexterityField.setEditable(true);
        intelligenceField.setEditable(true);
        healthField.setPromptText("Handling");
        powerField.setPromptText("Weight");
        dexterityField.setPromptText("Dexterity");
        intelligenceField.setPromptText("Intelligence");
    }

    /**
     * Method clears data in fields
     */
    private void clearData(){
        healthField.setText("");
        powerField.setText("");
        dexterityField.setText("");
        intelligenceField.setText("");
    }

    /**
     * Method Creates UI and returns it as group
     * @return group representing ui of dialog
     */
    private Node createUI(){
        Group group = new Group();

        healthField = new TextField();
        healthField.setPromptText("Handling");
        healthField.setAlignment(Pos.CENTER);

        powerField = new TextField();
        powerField.setPromptText("Weight");
        powerField.setAlignment(Pos.CENTER);

        dexterityField = new TextField();
        dexterityField.setPromptText("Dexterity");
        dexterityField.setAlignment(Pos.CENTER);

        intelligenceField = new TextField();
        intelligenceField.setPromptText("Intelligence");
        intelligenceField.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(healthField, powerField, dexterityField, intelligenceField);
        vBox.setAlignment(Pos.CENTER);
        vBox.setFillWidth(true);

        group.getChildren().add(vBox);

        return group;
    }
}
