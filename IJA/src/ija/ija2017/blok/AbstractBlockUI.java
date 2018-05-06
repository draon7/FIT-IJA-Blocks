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

    /**
     * getter of InputPorts circles
     * @return list of InputPorts circles
     */
    public ArrayList<Circle> getPortList() {
        return portList;
    }

    /**
     * Class adds port to list
     * @param circle port
     */
    public void addPortList(Circle circle){portList.add(circle);}

    /**
     * setter for list of ports
     * @param portList list of ports
     */
    public void setPortList(ArrayList<Circle> portList) {
        this.portList = portList;
    }

    /**
     * getter for all paths from block
     * @return arraz of paths
     */
    public ArrayList<Path> getPortPathList() {
        return portPathList;
    }

    /**
     * Class adds port to list of paths
     * @param path path to be added
     */
    public void addPortPathList(Path path){portPathList.add(path);}

    /**
     * setter for path list
     * @param portList list of paths
     */
    public void setPortPathList(ArrayList<Path> portList) {
        this.portPathList = portList;
    }

    /**
     * getter for object of block
     * @return object of block
     */
    public Group getBlock() {
        return block;
    }

    /**
     * setter for object of block
     * @param block object of block
     */
    public void setBlock(Group block) {
        this.block = block;
    }

    /**
     * getter for X position of Block
     * @return X position of block
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * setter for X position of block
     * @param positionX X position of the block
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * getter for Y position of the block
     * @return Y position of the block
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * setter for Y position of the block
     * @param positionY Y position of the block
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * Class reloads block, when newly acessed
     */
    public void reloadBlock(){
        parent.getChildren().add(block);
        block.setManaged(false);
        block.relocate(positionX, positionY);
    }

    /**
     * Sets handelers
     */
    public void setHandlers(){
        block.getChildren().forEach(node -> {
            if(node instanceof Circle){

            }
        });
    }
}
