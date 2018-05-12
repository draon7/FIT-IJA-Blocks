package ija.ija2017.blok;

import ija.ija2017.ui.BlockCreateUI;
import ija.ija2017.ui.BlockHandlers;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractBlockUI extends AbstractBlock {
    private double positionX;
    private double positionY;
    private double lastPositionX;
    private double lastPositionY;
    private ArrayList<Circle> portList = new ArrayList<Circle>();
    private ArrayList<Path> portPathList = new ArrayList<Path>();
    private Group block;
    private Pane parent;

    public AbstractBlockUI(){}

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeDouble(positionX);
        stream.writeDouble(positionY);
        stream.writeDouble(lastPositionX);
        stream.writeDouble(lastPositionY);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        positionX = stream.readDouble();
        positionY = stream.readDouble();
        lastPositionX = stream.readDouble();
        lastPositionY = stream.readDouble();
        portList = new ArrayList<Circle>();
        portPathList = new ArrayList<Path>();
    }

    public Pane getParent() {return parent;}
    public void setParent(Pane parent) {this.parent = parent;}



    /**
     * getter of InputPorts circles
     * @return list of InputPorts circles
     */
    public ArrayList<Circle> getPortList() {
        return portList;
    }

    /**
     * Method adds circle to list of circles
     * @param circle port circle
     */
    public void addPortList(Circle circle){portList.add(circle);}

    /**
     * setter for list of circles
     * @param portList
     */
    public void setPortList(ArrayList<Circle> portList) {
        this.portList = portList;
    }

    /**
     * getter for list of paths
     * @return
     */
    public ArrayList<Path> getPortPathList() {
        return portPathList;
    }

    /**
     * Method adds path to list of paths from this block
     * @param path path to add
     */
    public void addPortPathList(Path path){portPathList.add(path);}

    /**
     * sets list of paths
     * @param portList list to set
     */
    public void setPortPathList(ArrayList<Path> portList) {
        this.portPathList = portList;
    }

    /**
     * gets goup representing block
     * @return group representing block
     */
    public Group getBlock() {
        return block;
    }

    /**
     * setter for group representing object
     * @param block group representing object
     */
    public void setBlock(Group block) {
        this.block = block;
    }

    /**
     * getter for X position of block
     * @return X position of the block
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * setter for X position of the block
     * @param positionX X position of the block
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * getter for Y position of block
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
    public void setLastPositionX(double lastPositionX) {
        this.lastPositionX = lastPositionX;
    }
    
    public void setLastPositionY(double lastPositionY) {
        this.lastPositionY = lastPositionY;
    }

    /**
     * Method to call when scheme is reloaded
     */
    public void reloadBlock(Pane parent){
        setParent(parent);
        BlockCreateUI.CreateBlockUI(this);
        BlockCreateUI.CreatePortPathUI(this);
        BlockHandlers.AddHandlers(this);
        System.out.println("X: " + positionX + " |Y: " + positionY);
        System.out.println("LastX: " + lastPositionX + " |LastY: " + lastPositionY);
        block.relocate(lastPositionX, lastPositionY);
    }
    public void reloadConnectionUI(){
        BlockCreateUI.ReloadPaths(this);
    }
}
