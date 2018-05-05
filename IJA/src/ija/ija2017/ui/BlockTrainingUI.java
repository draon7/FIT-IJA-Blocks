package ija.ija2017.ui;

import ija.ija2017.blok.BlockTraining;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class BlockTrainingUI extends BlockTraining implements BlockUI {
    private double positionX;
    private double positionY;
    private BlockTraining blockCore;
    private Group block;
    private Pane parent;

    public double getPositionX() {
        return positionX;
    }
    public void setPositionX(double positionX) {
        this.positionX = positionX;
        block.relocate(this.positionX, this.positionY);
    }
    public double getPositionY() {
        return positionY;
    }
    public void setPositionY(double positionY) {
        this.positionY = positionY;
        block.relocate(this.positionX, this.positionY);
    }


    public BlockTrainingUI(Pane pane){
        parent = pane;
        blockCore = new BlockTraining();
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        block = BlockCreateUi.CreateBlockTrainingUI();
        AddHandlers(block);
        parent.getChildren().add(block);

    }

    public void AddHandlers(Group group){
        group.setOnMousePressed(event -> {
            positionX = event.getSceneX();
            positionY = event.getSceneY();
        });
        group.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - positionX + block.getLayoutBounds().getMinX();
            double deltaY = event.getSceneY() - positionY;
            double newX = deltaX + block.getLayoutX();
            double newY = deltaY + block.getLayoutY();

            block.relocate(newX, newY);
            positionX = event.getSceneX();
            positionY = event.getSceneY();
        });
    }
}
