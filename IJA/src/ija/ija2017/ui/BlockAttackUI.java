package ija.ija2017.ui;

import ija.ija2017.blok.BlockAttack;
import javafx.scene.layout.Pane;

public class BlockAttackUI extends BlockAttack implements BlockUI{

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