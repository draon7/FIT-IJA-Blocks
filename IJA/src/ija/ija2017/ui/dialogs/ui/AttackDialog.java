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

public class AttackDialog {
    private Dialog<DataAttackDialog> attackDialog;
    private DialogPane dialogPane;
    private TextField attackField;

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

    public DataAttackDialog showAndWait(){
        Optional<DataAttackDialog> newData = attackDialog.showAndWait();
        unlockAttackField();
        if(newData.isPresent()){
            return newData.get();
        }
        return null;
    }

    public void lockAttackField(){
        attackField.setEditable(false);
        attackField.setPromptText("Connected");
    }
    public void unlockAttackField(){
        attackField.setEditable(true);
        attackField.setPromptText("AttackPower");
    }

    private void clearData(){
        attackField.setText("");
    }

    private Node createUI(){
        Group group = new Group();

        attackField = new TextField();
        attackField.setPromptText("AttackPower");
        attackField.setAlignment(Pos.CENTER);
        group.getChildren().add(attackField);

        return group;
    }
}
