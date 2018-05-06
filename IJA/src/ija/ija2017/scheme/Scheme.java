package ija.ija2017.scheme;

import ija.ija2017.blok.IBlock;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is reassembling schemes showed on ui it implements
 * calculation methods and connection of blocks
 */
public class Scheme {
    private String name;
    private List<IBlock> blockList = new ArrayList<IBlock>();
    private List<IBlock> orderBlockList = new ArrayList<IBlock>();

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
    private Pane view;

    public Pane getView() {return view;}
    public void setView(Pane view) {this.view = view;}


    /**
     * Method adds block to scheme
     * @param block to be added
     */
    public void addBlock (IBlock block) {
        blockList.add(block);
    }

    /**
     * Calculate order of computation
     * @return true if possible otherwise false
     */
    public boolean calculateOrder () {
        boolean found;
        while (blockList.size() != orderBlockList.size()) {
            found = false;
            for (IBlock block : blockList) {
                if (block.canStart()) {
                    if (!orderBlockList.contains(block)) {
                        orderBlockList.add(block);
                        block.setReady();
                        found = true;
                    }
                }
            }
            if (!found) {
                orderBlockList.clear();
                return false;
            }
        }
        return true;
    }

    /**
     * Method calculates whole scheme,
     * Needs run calculate order before otherwise undefined
     */
    public void calculate() {
        for(IBlock block : orderBlockList) block.calculate();
    }

    /**
     * Method calculates one step, can be used repeatedly for more steps
     */
    public void calculateOne (){
        if (!orderBlockList.isEmpty()) {
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


    /**
     * gets name of the scheme
     * @return name of scheme
     */
    public String getName() {
        return name;
    }
}
