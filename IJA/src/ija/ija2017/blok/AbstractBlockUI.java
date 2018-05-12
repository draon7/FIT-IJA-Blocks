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
    public void setLastPositionX(double lastPositionX) {
        this.lastPositionX = lastPositionX;
    }
    public void setLastPositionY(double lastPositionY) {
        this.lastPositionY = lastPositionY;
    }

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
