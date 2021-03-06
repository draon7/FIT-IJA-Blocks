package ija.ija2017.ui;

import ija.ija2017.blok.BlockTraining;
import ija.ija2017.port.AbstractPort;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
/**
 * Class for creation of training block
 */
public class BlockTrainingUI extends BlockTraining implements BlockUI {

    /**
     * Creates TrainingUI
     * @param pane pane parent
     */
    public BlockTrainingUI(Pane pane){
        setParent(pane);
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        BlockCreateUI.CreateBlockUI(this);
        BlockCreateUI.CreatePortPathUI(this);
        BlockHandlers.AddHandlers(this);
    }
}
