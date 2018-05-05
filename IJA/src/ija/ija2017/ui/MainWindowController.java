package ija.ija2017.ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

    @FXML private AnchorPane mainViewPane;

    @FXML
    protected void createBlockAttack(MouseEvent e){
        BlockAttackUI blockAttack = new BlockAttackUI(mainViewPane);
    }
    @FXML
    protected void createBlockDefense(MouseEvent e){
        BlockDefenseUI blockDefense = new BlockDefenseUI(mainViewPane);
    }
    @FXML
    protected void createBlockHealing(MouseEvent e){
        BlockHealingUI blockHealing = new BlockHealingUI(mainViewPane);
    }
    @FXML
    protected void createBlockTraining(MouseEvent e){
        BlockTrainingUI blockTraining = new BlockTrainingUI(mainViewPane);
    }
    @FXML
    protected void createBlockWeaponUpgrade(MouseEvent e){
        BlockWeaponUpgradeUI blockWeaponUpgrade = new BlockWeaponUpgradeUI(mainViewPane);
    }
}
