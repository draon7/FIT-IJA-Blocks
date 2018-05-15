package ija.ija2017.ui.dialogs.ui;

import ija.ija2017.ui.dialogs.data.DataWeaponDialog;
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
 * Class representing weapon dialog
 */
public class WeaponDialog {
    private Dialog<DataWeaponDialog> weaponDialog;
    private DialogPane dialogPane;
    private TextField handlingField;
    private TextField weightField;

    /**
     * Constructor for the class, it creates dialog, sets its title
     * and creates result converter
     */
    public WeaponDialog(){
        weaponDialog = new Dialog<>();
        weaponDialog.setTitle("Weapon");
        dialogPane = weaponDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.APPLY);

        Node ui = createUI();
        dialogPane.setContent(ui);

        weaponDialog.setResultConverter((ButtonType button) -> {
            if(handlingField.getText().isEmpty())return null;
            if(weightField.getText().isEmpty())return null;
            if(button == ButtonType.APPLY){
                return new DataWeaponDialog(Double.parseDouble(handlingField.getText()), Double.parseDouble(weightField.getText()));
            }
            return null;
        });
    }

    /**
     * Method shows dialog unlocks fields and waits for input
     * @return inputed data
     */
    public DataWeaponDialog showAndWait(){
        handlingField.clear();
        weightField.clear();
        Optional<DataWeaponDialog> newData = weaponDialog.showAndWait();
        unlockWeaponField();
        if(newData.isPresent()){
            return newData.get();
        }
        return null;
    }

    /**
     * Method lock all input fields
     */
    public void lockAttackField(){
        handlingField.setEditable(false);
        weightField.setEditable(false);
        handlingField.setPromptText("Connected");
        weightField.setPromptText("Connected");
    }

    /**
     * Method unlocks all input fields
     */
    public void unlockWeaponField(){
        handlingField.setEditable(true);
        handlingField.setPromptText("Handling");
        weightField.setEditable(true);
        weightField.setPromptText("Weight");
    }

    /**
     * Method clears all data in dialog
     */
    private void clearData(){
        handlingField.setText("");
        weightField.setText("");
    }

    /**
     * Method creates dialogs UI as group
     * @return group representing dialog
     */
    private Node createUI(){
        Group group = new Group();

        handlingField = new TextField();
        handlingField.setPromptText("Handling");
        handlingField.setAlignment(Pos.CENTER);

        weightField = new TextField();
        weightField.setPromptText("Weight");
        weightField.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(handlingField, weightField);
        vBox.setAlignment(Pos.CENTER);
        vBox.setFillWidth(true);

        group.getChildren().add(vBox);

        return group;
    }
}