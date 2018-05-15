package ija.ija2017.ui;

import ija.ija2017.blok.BlockDefense;
import ija.ija2017.port.AbstractPort;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
/**
 * Class for creation of defense block
 */
public class BlockDefenseUI extends BlockDefense implements BlockUI {
    /**
     * Creates DefenseUI
     * @param pane pane parent
     */
    public BlockDefenseUI(Pane pane){
        setParent(pane);
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        BlockCreateUI.CreateBlockUI(this);
        BlockCreateUI.CreatePortPathUI(this);
        BlockHandlers.AddHandlers(this);
    }
}
