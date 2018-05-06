package ija.ija2017.ui;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.blok.AbstractBlock;
import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.BlockHealing;
import ija.ija2017.blok.IBlock;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.scheme.Scheme;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Optional;

import ija.ija2017.Data.AbstractData.DataType;

public class BlockConectionHandling {
    private static BlockConectionHandling self = new BlockConectionHandling();
    public static BlockConectionHandling getSelf(){
        return self;
    }
    private static Scheme activeScheme;
    private static ArrayList<Scheme> schemes = new ArrayList<Scheme>();

    private static boolean calculated = false;

    public static ArrayList<Scheme> getSchemes() {return schemes;}
    public static void setSchemes(ArrayList<Scheme> schemes) {BlockConectionHandling.schemes = schemes;}


    private static AnchorPane mainView;

    public static void setMainView(AnchorPane mainView){BlockConectionHandling.mainView = mainView;}
    public static AnchorPane getMainView() {return mainView;}

    private static InputPort inputPort;
    private static OutputPort outputPort;

    public static void createBlock(String s){
        switch (s){
            case("attack"):{
                activeScheme.addBlock(new BlockAttackUI(activeScheme.getView()));
                break;
            }
            case("defense"):{
                activeScheme.addBlock(new BlockDefenseUI(activeScheme.getView()));
                break;
            }
            case("healing"):{
                activeScheme.addBlock(new BlockHealingUI(activeScheme.getView()));
                break;
            }
            case("training"):{
                activeScheme.addBlock(new BlockTrainingUI(activeScheme.getView()));
                break;
            }
            case("upgrade"):{
                activeScheme.addBlock(new BlockWeaponUpgradeUI(activeScheme.getView()));
                break;
            }
        }

    }

    public static void addScheme(String id){
        Scheme newScheme = new Scheme(id);
        System.out.println(getMainView());
        getMainView().getChildren().add(newScheme.getView());
        AnchorPane.setTopAnchor(newScheme.getView(), 0d);
        AnchorPane.setRightAnchor(newScheme.getView(), 0d);
        AnchorPane.setBottomAnchor(newScheme.getView(), 0d);
        AnchorPane.setLeftAnchor(newScheme.getView(), 0d);
        getSchemes().add(newScheme);
        changeScheme(id);
    }
    public static void changeScheme(String id){
        for (Scheme scheme : getSchemes()) {
            if(scheme.getName() == id){
                System.out.println(scheme);
                activeScheme = scheme;
                scheme.getView().toFront();
                return;
            }
        }
    }

    public static boolean calculateScheme(){
        TextInputDialog dialog = new TextInputDialog("Val");
        dialog.setTitle("Wee");
        dialog.setHeaderText("Wee Header Wee");
        dialog.showAndWait();

        for(IBlock block : activeScheme.getBlockList()){
            for(InputPort inputPort : block.getInputPorts()){
                if(inputPort.getConnection() == null){
                    switch(inputPort.getDataType()){
                        case attack:{
                            dialog.setContentText("Attack: ");
                            Optional<String> result = dialog.showAndWait();
                            System.out.println(result);
                            break;
                        }
                        case fighter:{
                            dialog.setContentText("Fighter: ");
                            Optional<String> result = dialog.showAndWait();
                            System.out.println(result);
                            break;
                        }
                        case weapon:{
                            dialog.setContentText("Weapon: ");
                            Optional<String> result = dialog.showAndWait();
                            System.out.println(result);
                            break;
                        }
                    }
                }
            }
        }
        if(activeScheme.calculateOrder()) {
            calculated = true;
            return true;
        }
        else{
            calculated = false;
            return false;
        }
    }
    public static boolean runScheme(){
        if(calculated == false) return false;
        activeScheme.calculate();
        return true;
    }
    public static boolean stepScheme(){
        if(calculated == false) return false;
        activeScheme.calculateOne();
        return true;
    }

    public static boolean setInput(InputPort port){
        inputPort = port;
            if(tryConnect()){
                return true;
        }
        return false;
    }
    public static InputPort getInput(){return inputPort;}
    public static void removeInput(){inputPort = null;}
    public static boolean setOutput(OutputPort port){outputPort = port; if(tryConnect()){return true;} return false;}
    public static OutputPort getOutput(){return outputPort;}
    public static void removeOutput(){outputPort = null;}

    private static boolean tryConnect(){
        calculated = false;
        if(inputPort != null && outputPort != null){
            if(inputPort.getConnection() != null){activeScheme.disconnectPort(inputPort);}
            if(outputPort.getConnection() != null){activeScheme.disconnectPort(outputPort);}
            activeScheme.connectPorts(inputPort, outputPort);
            removeInput();
            removeOutput();
            return true;
        }
        return false;
    }
    public static void disconnect(AbstractPort port) {
        calculated = false;
        if (port instanceof OutputPort) {
            if (((OutputPort) port).getConnection() != null) {
                activeScheme.disconnectPort(port);
            }
        }
        if (port instanceof InputPort) {
            if (((InputPort) port).getConnection() != null) {
                activeScheme.disconnectPort(port);
            }
        }
    }
}