package ija.ija2017.scheme;

import ija.ija2017.blok.IBlock;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Scheme {

    public Scheme (String name) {
        AnchorPane pane = new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.color(0.30,0.30,0.30,1), CornerRadii.EMPTY, Insets.EMPTY)));
        setView(pane);
        this.name = name;
    }

    private String name;
    private List<IBlock> blockList = new ArrayList<IBlock>();
    private List<IBlock> orderBlockList = new ArrayList<IBlock>();

    private Pane view;

    public Pane getView() {return view;}
    public void setView(Pane view) {this.view = view;}

    public void addBlock (IBlock block) {
        blockList.add(block);
    }

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
    public void calculate() {
        for(IBlock block : orderBlockList) block.calculate();
    }

    public void calculateOne (){
        if (!orderBlockList.isEmpty()) {
            orderBlockList.get(0).calculate();
            orderBlockList.remove(0);
        }
    }

    public void connectPorts (InputPort inputPort, OutputPort outputPort){
        outputPort.setConnection(inputPort);
        inputPort.setConnection(outputPort);
    }

    public String getName() {
        return name;
    }
}
