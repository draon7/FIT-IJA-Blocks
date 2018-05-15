package ija.ija2017.ui;

import ija.ija2017.blok.BlockAttack;
import javafx.scene.layout.Pane;

/**
 * Class for creation of attack block
 */
public class BlockAttackUI extends BlockAttack implements BlockUI{

    /**
     * Creates AttackUI
     * @param pane pane parent
     */
    public BlockAttackUI(Pane pane){
        setParent(pane);
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        BlockCreateUI.CreateBlockUI(this);
        BlockCreateUI.CreatePortPathUI(this);
        BlockHandlers.AddHandlers(this);
    }
}