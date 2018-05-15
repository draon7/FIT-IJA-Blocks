package ija.ija2017.blok;

import ija.ija2017.ui.BlockCreateUI;
import ija.ija2017.ui.BlockHandlers;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for UI creation of block
 */
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

    /**
     * Method for serialization
     * @param stream to write to
     * @throws IOException IO error
     */
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeDouble(positionX);
        stream.writeDouble(positionY);
        stream.writeDouble(lastPositionX);
        stream.writeDouble(lastPositionY);
    }

    /**
     * Method for deserialization
     * @param stream stream to read from
     * @throws IOException IO error
     * @throws ClassNotFoundException class does not exist
     */
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        positionX = stream.readDouble();
        positionY = stream.readDouble();
        lastPositionX = stream.readDouble();
        lastPositionY = stream.readDouble();
        portList = new ArrayList<Circle>();
        portPathList = new ArrayList<Path>();
    }

    /**
     * Pane parent getter
     * @return pane parent
     */
    public Pane getParent() {return parent;}

    /**
     * Pane parent setter
     * @param parent pane parent
     */
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
     * @param portList list of ports
     */
    public void setPortList(ArrayList<Circle> portList) {
        this.portList = portList;
    }

    /**
     * getter for list of paths
     * @return list of paths
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

    /**
     * Last X position setter
     * @param lastPositionX X position of block
     */
    public void setLastPositionX(double lastPositionX) {
        this.lastPositionX = lastPositionX;
    }

    /**
     * Last Y position setter
     * @param lastPositionY Y position of the block
     */
    public void setLastPositionY(double lastPositionY) {
        this.lastPositionY = lastPositionY;
    }

    /**
     * Method to call when scheme is reloaded
     * @param parent parent pane
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

    /**
     * Method for reloading Paths from block
     */
    public void reloadConnectionUI(){
        BlockCreateUI.ReloadPaths(this);
    }
}
