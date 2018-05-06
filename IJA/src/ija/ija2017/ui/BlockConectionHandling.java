package ija.ija2017.ui;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.scheme.Scheme;
import javafx.scene.layout.AnchorPane;

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

    public static void addScheme(String id){
        getSchemes().add(new Scheme(id));
        changeScheme(id);
    }
    public static void changeScheme(String id){
        for (Scheme scheme : getSchemes()) {
            if(scheme.getName() == id){
                activeScheme = scheme;
                return;
            }
        }
    }

    public static boolean setInput(InputPort port){inputPort = port; if(tryConnect()){return true;} return false;}
    public static InputPort getInput(){return inputPort;}
    public static void removeInput(){inputPort = null;}
    public static boolean setOutput(OutputPort port){outputPort = port; if(tryConnect()){return true;} return false;}
    public static OutputPort getOutput(){return outputPort;}
    public static void removeOutput(){outputPort = null;}

    private static boolean tryConnect(){
        System.out.println("In : Out - " + inputPort + " : " + outputPort);
        if(inputPort != null && outputPort != null){
            return true;
        }
        return false;
    }
}