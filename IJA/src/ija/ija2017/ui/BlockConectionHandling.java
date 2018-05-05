package ija.ija2017.ui;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.scheme.Scheme;

import java.util.ArrayList;

public class BlockConectionHandling {
    private static BlockConectionHandling self = new BlockConectionHandling();
    public static BlockConectionHandling getSelf(){
        return self;
    }
    private ArrayList<Scheme> schemes = new ArrayList<Scheme>();

    private static InputPort inputPort;
    private static OutputPort outputPort;

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