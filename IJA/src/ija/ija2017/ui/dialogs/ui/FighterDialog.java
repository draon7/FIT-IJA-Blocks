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

public class FighterDialog {
    private Dialog<DataFighterDialog> weaponDialog;
    private DialogPane dialogPane;
    private TextField healthField;
    private TextField powerField;
    private TextField dexterityField;
    private TextField intelligenceField;

    public FighterDialog(){
        weaponDialog = new Dialog<>();
        weaponDialog.setTitle("Weapon");
        dialogPane = weaponDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.APPLY);

        Node ui = createUI();
        dialogPane.setContent(ui);

        weaponDialog.setResultConverter((ButtonType button) -> {
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

    public DataFighterDialog showAndWait(){
        Optional<DataFighterDialog> newData = weaponDialog.showAndWait();
        unlockAttackField();
        if(newData.isPresent()){
            return newData.get();
        }
        return null;
    }

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
    public void unlockAttackField(){
        healthField.setEditable(true);
        powerField.setEditable(true);
        dexterityField.setEditable(true);
        intelligenceField.setEditable(true);
        healthField.setPromptText("Handling");
        powerField.setPromptText("Weight");
        dexterityField.setPromptText("Dexterity");
        intelligenceField.setPromptText("Intelligence");
    }

    private void clearData(){
        healthField.setText("");
        powerField.setText("");
        dexterityField.setText("");
        intelligenceField.setText("");
    }

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
