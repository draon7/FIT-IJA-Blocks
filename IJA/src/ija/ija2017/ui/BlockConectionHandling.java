package ija.ija2017.ui;

import ija.ija2017.blok.AbstractBlock;
import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.BlockHealing;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.scheme.Scheme;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class BlockConectionHandling {
    private static BlockConectionHandling self = new BlockConectionHandling();
    public static BlockConectionHandling getSelf(){
        return self;
    }
    private static Scheme activeScheme;
    private static ArrayList<Scheme> schemes = new ArrayList<Scheme>();

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
                new BlockAttackUI(activeScheme.getView());
                break;
            }
            case("defense"):{
                new BlockDefenseUI(activeScheme.getView());
                break;
            }
            case("healing"):{
                new BlockHealingUI(activeScheme.getView());
                break;
            }
            case("training"):{
                new BlockTrainingUI(activeScheme.getView());
                break;
            }
            case("upgrade"):{
                new BlockWeaponUpgradeUI(activeScheme.getView());
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