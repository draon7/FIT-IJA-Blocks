package ija.ija2017.ui;

import ija.ija2017.blok.BlockWeaponUpgrade;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class BlockWeaponUpgradeUI extends BlockWeaponUpgrade implements BlockUI {
    private double positionX;
    private double positionY;
    private BlockWeaponUpgrade blockCore;
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


    public BlockWeaponUpgradeUI(Pane pane){
        parent = pane;
        blockCore = new BlockWeaponUpgrade();
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        block = BlockCreateUi.CreateBlockWeaponUpgradeUI();
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
