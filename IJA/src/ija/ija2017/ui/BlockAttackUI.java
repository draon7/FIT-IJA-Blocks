package ija.ija2017.ui;

import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.BlockAttack;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class BlockAttackUI extends BlockAttack implements BlockUI {
    private BlockAttack blockCore;
    private Pane parent;
    private Path pathIn1;


    public BlockAttackUI(Pane pane){
        parent = pane;
        blockCore = new BlockAttack();
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        setBlock(BlockCreateUi.CreateBlockAttackUI());
        getBlock().getChildren().forEach(node -> {
            if(node instanceof Circle){
                addPortList((Circle)node);
                addPortPathList(new Path());
            }
        });
        parent.getChildren().add(getBlock());
        pathIn1 = new Path();
        pathIn1.setStrokeWidth(3);
        pathIn1.setStroke(Color.DARKGRAY);
        AddHandlers(getBlock());
        parent.getChildren().add(pathIn1);
        pathIn1.toBack();
    }


    public void AddHandlers(Group group){
        group.getChildren().get(0).setOnMousePressed(event -> {
            BlockHandlers.handleMoveStart(event, this);
        });
        group.getChildren().get(0).setOnMouseDragged(event -> {
            BlockHandlers.handleMoveDrag(event, this);
        });
        int index = 0;
        for(Circle circle : getPortList()){
            Path pathStorage = getPortPathList().get(index);
            circle.setOnMousePressed(event -> {
                BlockHandlers.handlePortClicked(event, circle, pathStorage, 2, 1);
            });
            circle.setOnMouseReleased(event -> {
                BlockHandlers.hanlePortReleased(pathStorage);
            });
            circle.setOnMouseDragged(event -> {
                BlockHandlers.hanlePortDragged(event, this, pathStorage);
            });
            circle.setOnMouseDragOver(event -> {
                BlockHandlers.handlePortDragOver(pathStorage);
            });
            circle.setOnMouseEntered(event -> {
                BlockHandlers.handlePortExited(pathStorage);
            });
            circle.setOnMouseExited(event -> {
                if(!(event.isPrimaryButtonDown())){
                    BlockHandlers.handlePortEntered(pathStorage);
                }
            });
            pathStorage.setOnMouseEntered(event -> {
                BlockHandlers.handlePathEntered(pathStorage);
            });
            pathStorage.setOnMouseExited(event -> {
                BlockHandlers.handlePathExited(pathStorage);
            });
        }
    }
}