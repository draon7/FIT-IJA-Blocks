package ija.ija2017.ui;

import ija.ija2017.blok.BlockWeaponUpgrade;
import ija.ija2017.port.AbstractPort;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
/**
 * Class for creation of weapon upgrade block
 */
public class BlockWeaponUpgradeUI extends BlockWeaponUpgrade implements BlockUI {

    /**
     * Creates WeaponUpgradeUI
     * @param pane pane parent
     */
    public BlockWeaponUpgradeUI(Pane pane){
        setParent(pane);
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        BlockCreateUI.CreateBlockUI(this);
        BlockCreateUI.CreatePortPathUI(this);
        BlockHandlers.AddHandlers(this);
    }
}
