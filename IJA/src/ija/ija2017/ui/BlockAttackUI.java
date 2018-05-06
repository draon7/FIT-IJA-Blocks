package ija.ija2017.ui;

import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.BlockAttack;
import ija.ija2017.port.AbstractPort;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class BlockAttackUI extends BlockAttack implements BlockUI {
    private Pane parent;


    public BlockAttackUI(Pane pane){
        parent = pane;
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        setBlock(BlockCreateUI.CreateBlockAttackUI());
        getBlock().getChildren().forEach(node -> {
            if(node instanceof Circle){
                addPortList((Circle)node);
            }
        });
        parent.getChildren().add(getBlock());
        getInputPorts().forEach(p -> {
            parent.getChildren().add(p.getPath());
            addPortPathList(p.getPath());
        });
        getOutputPorts().forEach(p -> {
            parent.getChildren().add(p.getPath());
            addPortPathList(p.getPath());
        });
        AddHandlers(getBlock());
    }


    public void AddHandlers(Group group){
        group.getChildren().get(0).setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                parent.getChildren().remove(getBlock());
                for(Path path : getPortPathList()){parent.getChildren().remove(path);}
            }
            BlockHandlers.handleMoveStart(event, this);
        });
        group.getChildren().get(0).setOnMouseDragged(event -> {
            BlockHandlers.handleMoveDrag(event, this, getPortPathList());
        });
        int index = 0;
        for(Circle circle : getPortList()){
            Path pathStorage;
            AbstractPort port;
            if(index < getInputPorts().size()){
                port = getInputPorts().get(index);
            }else{
                port = getOutputPorts().get(index-getInputPorts().size());
            }
            pathStorage = port.getPath();
            circle.setOnDragDetected(event -> {
                circle.startFullDrag();
            });
            circle.setOnMousePressed(event -> {
                event.setDragDetect(true);
                group.setMouseTransparent(true);
                BlockHandlers.handlePortClicked(event, port, circle, pathStorage, 2,1);
            });
            circle.setOnMouseReleased(event -> {
                group.setMouseTransparent(false);
                BlockHandlers.hanlePortReleased(port, circle, pathStorage);
            });
            circle.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                BlockHandlers.hanlePortDragged(event, this, pathStorage);

            });
            circle.setOnMouseDragEntered(e->BlockHandlers.handlePortDragEntered(port ,circle));
            circle.setOnMouseDragExited(e->BlockHandlers.handlePortDragExited(port, circle));
            circle.setOnMouseDragReleased(e->BlockHandlers.handlePortDragReleased(e, port, pathStorage));

            circle.setOnMouseEntered(event -> BlockHandlers.handlePortEntered(pathStorage));
            circle.setOnMouseExited(event -> {
                if(!(event.isPrimaryButtonDown())){
                    BlockHandlers.handlePortExited(pathStorage);
                }
            });
            //pathStorage.setOnMouseDragged(event -> BlockHandlers.hanlePortDragged(event, this, pathStorage));
            pathStorage.setOnMouseEntered(event -> BlockHandlers.handlePathEntered(pathStorage));
            pathStorage.setOnMouseExited(event -> BlockHandlers.handlePathExited(pathStorage));
            index++;
        }
    }
}