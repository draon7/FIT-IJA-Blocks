package ija.ija2017.blok;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

import java.util.ArrayList;

public abstract class AbstractBlockUI extends AbstractBlock {
    private double positionX;
    private double positionY;
    private ArrayList<Circle> portList = new ArrayList<Circle>();
    private ArrayList<Path> portPathList = new ArrayList<Path>();
    private Group block;
    private Pane parent;

    public Pane getParent() {return parent;}
    public void setParent(Pane parent) {this.parent = parent;}



    /**
     * getter of InputPorts circles
     * @return list of InputPorts circles
     */
    public ArrayList<Circle> getPortList() {
        return portList;
    }
    public void addPortList(Circle circle){portList.add(circle);}
    public void setPortList(ArrayList<Circle> portList) {
        this.portList = portList;
    }
    public ArrayList<Path> getPortPathList() {
        return portPathList;
    }
    public void addPortPathList(Path path){portPathList.add(path);}
    public void setPortPathList(ArrayList<Path> portList) {
        this.portPathList = portList;
    }
    public Group getBlock() {
        return block;
    }
    public void setBlock(Group block) {
        this.block = block;
    }
    public double getPositionX() {
        return positionX;
    }
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }
    public double getPositionY() {
        return positionY;
    }
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void reloadBlock(){
        parent.getChildren().add(block);
        block.setManaged(false);
        block.relocate(positionX, positionY);
    }
    public void setHandlers(){
        block.getChildren().forEach(node -> {
            if(node instanceof Circle){

            }
        });
    }
}
