package ija.ija2017.scheme;

import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.IBlock;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.ui.BlockColors;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is reassembling schemes showed on ui it implements
 * calculation methods and connection of blocks
 */
public class Scheme implements Serializable {
    private String name;
    private Rectangle previousBlock;
    private Pane view;
    private List<IBlock> blockList = new ArrayList<IBlock>();
    private List<IBlock> orderBlockList = new ArrayList<IBlock>();

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(name);
        stream.writeObject(blockList);
        stream.writeObject(orderBlockList);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        blockList = (List<IBlock>) stream.readObject();
        orderBlockList = (List<IBlock>) stream.readObject();
        AnchorPane pane = new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.color(0.30,0.30,0.30,1), CornerRadii.EMPTY, Insets.EMPTY)));
        setView(pane);
    }

    public Rectangle getPreviousBlock(){return previousBlock;}

    public List<IBlock> getBlockList() {return blockList;}
    public void setBlockList(List<IBlock> blockList) {this.blockList = blockList;}

    /**
     * Constructor sets name of scheme
     * @param name name of the scheme
     */
    public Scheme (String name) {
        AnchorPane pane = new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.color(0.30,0.30,0.30,1), CornerRadii.EMPTY, Insets.EMPTY)));
        setView(pane);
        this.name = name;
    }

    /**
     * sets name of the scheme
     * @param name name of the scheme
     */
    public void setName(String name) {this.name = name;}

    /**
     * gets name of the scheme
     * @return name of scheme
     */
    public String getName() {
        return name;
    }

    public Pane getView() {return view;}
    public void setView(Pane view) {this.view = view;}


    /**
     * Method adds block to scheme
     * @param block to be added
     */
    public void addBlock (IBlock block) {
        blockList.add(block);
    }
    public void removeBlock (IBlock block) {blockList.remove(block);orderBlockList.remove(block);}

    /**
     * Calculate order of computation
     * @return true if possible otherwise false
     */
    public boolean calculateOrder () {
        boolean found;
        orderBlockList.clear();
        while (blockList.size() != orderBlockList.size()) {
            found = false;
            for (IBlock block : blockList) {
                if (block.canStart()) {
                    if (!orderBlockList.contains(block)) {
                        orderBlockList.add(block);
                        block.setReady();
                        found = true;
                        System.out.println("Found");
                    }
                }
            }
            if (!found) {
                System.out.println("NotFound");
                return false;
            }
        }
        System.out.println();
        return true;
    }

    /**
     * Method calculates whole scheme,
     * Needs run calculate order before otherwise undefined
     */
    public void calculate() {
        for(IBlock block : orderBlockList) {System.out.println(block);block.calculate();}
    }

    /**
     * Method calculates one step, can be used repeatedly for more steps
     */
    public void calculateOne (){
        if (!orderBlockList.isEmpty()) {
            if(previousBlock != null){
                previousBlock.setFill(BlockColors.blockFillColor);
                previousBlock.setStroke(BlockColors.blocStrokeColor);
            }
            previousBlock = ((Rectangle)((Group)((AbstractBlockUI)orderBlockList.get(0)).getBlock()).getChildren().get(0));
            previousBlock.setFill(BlockColors.blockFillHoverColor);
            previousBlock.setStroke(BlockColors.blocStrokeHoverColor);

            orderBlockList.get(0).calculate();
            orderBlockList.remove(0);
        }
    }

    /**
     * Method connect two ports one InputPort and one OutputPort
     * @param inputPort InputPort to connect
     * @param outputPort OutputPort to connect
     */
    public void connectPorts (InputPort inputPort, OutputPort outputPort){
        outputPort.setConnection(inputPort);
        inputPort.setConnection(outputPort);
    }

    /**
     * Method disconnect port
     * @param port port to disconnect
     */
    public void disconnectPort (AbstractPort port){
        if (port instanceof InputPort) {
            port.getPath().getElements().clear();
            OutputPort outputPort = ((InputPort) port).getConnection();
            if (outputPort == null)
                return;
            outputPort.getPath().getElements().clear();
            outputPort.setConnection(null);
            ((InputPort) port).setConnection(null);
        }
        else {
            InputPort inputPort = ((OutputPort)port).getConnection();
            port.getPath().getElements().clear();
            if (inputPort == null)
                return;
            inputPort.getPath().getElements().clear();
            inputPort.setConnection(null);
            ((OutputPort) port).setConnection(null);
        }
    }
}
