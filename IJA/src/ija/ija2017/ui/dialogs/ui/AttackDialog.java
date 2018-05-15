package ija.ija2017.ui.dialogs.ui;

import ija.ija2017.ui.dialogs.data.DataAttackDialog;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * Class for Attack Dialog
 */
public class AttackDialog {
    private Dialog<DataAttackDialog> attackDialog;
    private DialogPane dialogPane;
    private TextField attackField;

    /**
     * Attack dialog constructor, creates new dialog,
     * sets title and sets converter
     */
    public AttackDialog(){
        attackDialog = new Dialog<>();
        attackDialog.setTitle("Attack Input");
        dialogPane = attackDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.APPLY);

        Node ui = createUI();
        dialogPane.setContent(ui);

        attackDialog.setResultConverter((ButtonType button) -> {
            if(attackField.getText().isEmpty())return null;
            if(button == ButtonType.APPLY){
                return new DataAttackDialog(Double.parseDouble(attackField.getText()));
            }
            return null;
        });
    }

    /**
     * Method shows dialog and waits, it unlocks all input  fields
     * @return entered data to dialog
     */
    public DataAttackDialog showAndWait(){
        attackField.clear();
        Optional<DataAttackDialog> newData = attackDialog.showAndWait();
        unlockAttackField();
        if(newData.isPresent()){
            return newData.get();
        }
        return null;
    }

    /**
     * Method locks input field
     */
    public void lockAttackField(){
        attackField.setEditable(false);
        attackField.setPromptText("Connected");
    }

    /**
     * Method unlocks input field
     */
    public void unlockAttackField(){
        attackField.setEditable(true);
        attackField.setPromptText("AttackPower");
    }

    /**
     * Method clears input field
     */
    private void clearData(){
        attackField.setText("");
    }

    /**
     * Method creates UI, it sets description and sets its alignment
     * @return group representing dialog
     */
    private Node createUI(){
        Group group = new Group();

        attackField = new TextField();
        attackField.setPromptText("AttackPower");
        attackField.setAlignment(Pos.CENTER);
        group.getChildren().add(attackField);

        return group;
    }
}
