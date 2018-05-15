package ija.ija2017.ui;

import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataFighter;
import ija.ija2017.Data.DataWeapon;
import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.IBlock;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.scheme.Scheme;
import ija.ija2017.ui.dialogs.data.DataAttackDialog;
import ija.ija2017.ui.dialogs.data.DataFighterDialog;
import ija.ija2017.ui.dialogs.data.DataWeaponDialog;
import ija.ija2017.ui.dialogs.ui.AttackDialog;
import ija.ija2017.ui.dialogs.ui.FighterDialog;
import ija.ija2017.ui.dialogs.ui.WeaponDialog;
import ija.ija2017.ui.widgets.AttackDataWidget;
import ija.ija2017.ui.widgets.FighterDataWidget;
import ija.ija2017.ui.widgets.WeaponDataWidget;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for handling connection of ports
 */
public class BlockConnectionHandling {
    private static BlockConnectionHandling self = new BlockConnectionHandling();

    private static AttackDataWidget attackDataWidget;
    private static FighterDataWidget fighterDataWidget;
    private static WeaponDataWidget weaponDataWidget;
    private static Button stepButton;
    private static Button runButton;

    private static InputPort inputPort;
    private static OutputPort outputPort;

    private static AnchorPane mainView;
    private static Scheme activeScheme;
    private static ArrayList<Scheme> schemes = new ArrayList<Scheme>();

    private static boolean calculated = false;

    /**
     * getter of schemes
     * @return list of schemes
     */
    public static ArrayList<Scheme> getSchemes() {return schemes;}


    public static void setMainView(AnchorPane mainView){
        BlockConnectionHandling.mainView = mainView;}
    public static AnchorPane getMainView() {return mainView;}

    public static void initialize(AnchorPane view, Button runBut, Button stepBut){
        mainView = view;
        fighterDataWidget = new FighterDataWidget(mainView);
        attackDataWidget = new AttackDataWidget(mainView);
        weaponDataWidget = new WeaponDataWidget(mainView);
        stepButton = stepBut;
        runButton = runBut;
    }

    /**
     * Method that disables buttons and highlighted block after changes in scheme
     */
    public static void disableButtons(){
        if(activeScheme.getPreviousBlock() != null){
            activeScheme.getPreviousBlock().setStroke(BlockColors.blocStrokeColor);
            activeScheme.getPreviousBlock().setFill(BlockColors.blockFillColor);
        }
        runButton.setTextFill(Color.RED);
        stepButton.setTextFill(Color.RED);
        runButton.setDisable(true);
        stepButton.setDisable(true);
    }

    /**
     * Method that enables buttons after checking scheme
     */
    public static void enableButtons(){
        runButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        runButton.setDisable(false);
        stepButton.setDisable(false);
    }

    /**
     * Method for showing data in port
     * @param port port to show
     */
    public static void showPortData(AbstractPort port){
        MoveTo pos = new MoveTo();
        Circle portCircle;
        switch (port.getDataType()){
            case fighter:{
                portCircle = port.getPortCircle();
                pos.setX(portCircle.getParent().getLayoutX() + portCircle.getCenterX() + portCircle.getRadius());
                pos.setY(portCircle.getParent().getLayoutY() + portCircle.getCenterY() + portCircle.getRadius());
                fighterDataWidget.show(port.getValue(), pos);
                break;
            }
            case attack:{
                portCircle = port.getPortCircle();
                pos.setX(portCircle.getParent().getLayoutX() + portCircle.getCenterX() + portCircle.getRadius());
                pos.setY(portCircle.getParent().getLayoutY() + portCircle.getCenterY() + portCircle.getRadius());
                attackDataWidget.show(port.getValue(), pos);
                break;
            }
            case weapon:{
                portCircle = port.getPortCircle();
                pos.setX(portCircle.getParent().getLayoutX() + portCircle.getCenterX() + portCircle.getRadius());
                pos.setY(portCircle.getParent().getLayoutY() + portCircle.getCenterY() + portCircle.getRadius());
                weaponDataWidget.show(port.getValue(), pos);
                break;
            }
        }
    }

    /**
     * Method hides port widget
     */
    public static void hidePortData(){
        fighterDataWidget.hide();
        attackDataWidget.hide();
        weaponDataWidget.hide();
    }

    /**
     * Method creates block
     * @param s type of block
     */
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

    /**
     * Method removes block from scheme
     * @param blockReference reference to block
     */
    public static void removeBlock(IBlock blockReference){
        disableButtons();
        activeScheme.removeBlock(blockReference);
    }

    /**
     * method for saving scheme
     * @param file file to save scheme to
     * @throws IOException IO error
     */
    public static void saveScheme(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        activeScheme.setName(file.getName().replace(".blockies", ""));
        oos.writeObject(activeScheme);
        oos.close();
    }

    /**
     * Reads scheme from file
     * @param file file read from
     * @throws IOException IO error
     */
    public static void readScheme(File file) throws IOException {
        FileInputStream fin= new FileInputStream (file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        BlockConnectionHandling.clearScheme();
        Pane currentView = activeScheme.getView();
        try {
            activeScheme = (Scheme)ois.readObject();
            activeScheme.setView(currentView);
        }catch (Exception e){
            System.out.println(e);
        }
        for(IBlock iBlock : activeScheme.getBlockList()) {
            ((AbstractBlockUI)iBlock).reloadBlock(activeScheme.getView());
        }
        for(IBlock iBlock : activeScheme.getBlockList()) {
            ((AbstractBlockUI)iBlock).reloadConnectionUI();
        }
        fin.close();
    }

    /**
     * Method to add new scheme
     * @param id name of scheme
     */
    public static void addScheme(String id){
        Scheme newScheme = new Scheme(id);
        getMainView().getChildren().add(newScheme.getView());
        AnchorPane.setTopAnchor(newScheme.getView(), 0d);
        AnchorPane.setRightAnchor(newScheme.getView(), 0d);
        AnchorPane.setBottomAnchor(newScheme.getView(), 0d);
        AnchorPane.setLeftAnchor(newScheme.getView(), 0d);
        getSchemes().add(newScheme);
        changeScheme(id);
    }

    /**
     * Method clears scheme
     */
    public static void clearScheme(){
        activeScheme.getView().getChildren().clear();
        activeScheme.getBlockList().clear();
        disableButtons();
    }

    /**
     * Method changes active scheme
     * @param id name of scheme
     */
    public static void changeScheme(String id){
        for (Scheme scheme : getSchemes()) {
            if(scheme.getName().equals(id)){
                activeScheme = scheme;
                scheme.getView().toFront();
                return;
            }
        }
    }

    /**
     * Method renames active scheme
     * @param name name to rename to
     */
    public static void renameScheme(String name){
        activeScheme.setName(name);
    }

    /**
     * Method deletes active scheme
     */
    public static void deleteScheme(){
        clearScheme();
        schemes.remove(activeScheme);
    }

    /**
     * Method calculates scheme order
     * @return true if ok otherwise false
     */
    public static boolean calculateScheme(){
        if(activeScheme.getPreviousBlock() != null){
            activeScheme.getPreviousBlock().setStroke(BlockColors.blocStrokeColor);
            activeScheme.getPreviousBlock().setFill(BlockColors.blockFillColor);
        }
        TextInputDialog dialog = new TextInputDialog("Val");
        dialog.setTitle("Wee");
        dialog.setHeaderText("Wee Header Wee");
        AttackDialog attackDialog = new AttackDialog();
        DataAttackDialog dataAttack;
        FighterDialog fighterDialog = new FighterDialog();
        DataFighterDialog dataFighter;
        WeaponDialog weaponDialog = new WeaponDialog();
        DataWeaponDialog dataWeapon;

        Rectangle blockUI = null;

        for(IBlock block : activeScheme.getBlockList()){
            for(Node n : ((AbstractBlockUI)block).getBlock().getChildren()){
                if(n instanceof Rectangle){
                    blockUI = (Rectangle)n;
                }
            }
            blockUI.setFill(BlockColors.blockFillHoverColor);
            blockUI.setStroke(BlockColors.blocStrokeHoverColor);
            for(InputPort inputPort : block.getInputPorts()){
                if(inputPort.getConnection() == null){
                    switch(inputPort.getDataType()){
                        case attack:{
                            switch (inputPort.getDataType()){
                                case attack:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverAttack);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                                case fighter:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverFighter);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                                case weapon:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverWeapon);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                            }
                            do{
                                dataAttack = attackDialog.showAndWait();
                                System.out.println(dataAttack);
                            }while(dataAttack == null);

                            inputPort.setData(new DataAttack(dataAttack.getAttackPower()));
                            inputPort.setReady();

                            switch (inputPort.getDataType()){
                                case attack:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorAttack);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                                case fighter:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorFighter);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                                case weapon:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorWeapon);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                            }
                            break;
                        }
                        case fighter:{
                            switch (inputPort.getDataType()){
                                case attack:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverAttack);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                                case fighter:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverFighter);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                                case weapon:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverWeapon);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                            }
                            do{
                                dataFighter = fighterDialog.showAndWait();
                                System.out.println(dataFighter);
                            }while(dataFighter == null);

                            inputPort.setData(new DataFighter(
                                    dataFighter.getHealth(),
                                    dataFighter.getPower(),
                                    dataFighter.getDexterity(),
                                    dataFighter.getIntelligence()));
                            inputPort.setReady();

                            switch (inputPort.getDataType()){
                                case attack:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorAttack);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                                case fighter:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorFighter);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                                case weapon:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorWeapon);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                            }
                            break;
                        }
                        case weapon:{
                            switch (inputPort.getDataType()){
                                case attack:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverAttack);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                                case fighter:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverFighter);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                                case weapon:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorHoverWeapon);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadiusHover);
                                    break;
                                }
                            }
                            do{
                                dataWeapon = weaponDialog.showAndWait();
                                System.out.println(dataWeapon);
                            }while(dataWeapon == null);

                            inputPort.setData(new DataWeapon(dataWeapon.getHandeling(), dataWeapon.getWeight()));
                            inputPort.setReady();

                            switch (inputPort.getDataType()){
                                case attack:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorAttack);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                                case fighter:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorFighter);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                                case weapon:{
                                    inputPort.getPortCircle().setFill(BlockColors.portColorWeapon);
                                    inputPort.getPortCircle().setRadius(BlockColors.circleRadius);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }

            blockUI.setFill(BlockColors.blockFillColor);
            blockUI.setStroke(BlockColors.blocStrokeColor);
        }
        calculated = activeScheme.calculateOrder();
        if (calculated == true) {enableButtons();}
        else {disableButtons();}
        return calculated;
    }

    /**
     * Method to calculate whole scheme
     * @return returns true if calculation was successful return false if calculation failed
     */
    public static boolean runScheme(){
        if(calculated == false) return false;
        activeScheme.calculate();
        return true;
    }

    /**
     * Method to move in scheme calculation by only one step
     * @return returns true if calculation was successful return false if calculation failed
     */
    public static boolean stepScheme(){
        if(calculated == false) return false;
        activeScheme.calculateOne();
        return true;
    }

    /**
     * Setts inputPort and tries to connect it
     * @param port inputPort to be added and connected
     * @return returns true if inputPort was connected successfully returns false of connection failed
     */
    public static boolean setInput(InputPort port){
        inputPort = port;
        if(tryConnect()){
            return true;
        }
        return false;
    }

    /**
     * Getter for inputPort
     * @return returns inputPort reference
     */
    public static InputPort getInput(){return inputPort;}

    /**
     * Setts inputPort to null
     */
    public static void removeInput(){inputPort = null;}

    /**
     * Setts outputPort and tries to connect it
     * @param port outputPort to be added and connected
     * @return returns true if outputPort was connected successfully returns false of connection failed
     */
    public static boolean setOutput(OutputPort port){outputPort = port; if(tryConnect()){return true;} return false;}

    /**
     * Getter for outputPort
     * @return returns outputPort reference
     */
    public static OutputPort getOutput(){return outputPort;}

    /**
     * Setts outputPort to null
     */
    public static void removeOutput(){outputPort = null;}

    /**
     * Method that tries to connect port to another port
     * @return returns true if port was successfully connected return false if connection failed
     */
    private static boolean tryConnect(){
        calculated = false;
        if(inputPort != null && outputPort != null){
            if(inputPort.getDataType() != outputPort.getDataType()){return false;}
            if(inputPort.getConnection() != null){activeScheme.disconnectPort(inputPort);}
            if(outputPort.getConnection() != null){activeScheme.disconnectPort(outputPort);}
            activeScheme.connectPorts(inputPort, outputPort);
            removeInput();
            removeOutput();
            disableButtons();
            return true;
        }
        return false;
    }

    /**
     * Method that disconnects ports
     * @param port reference to one of the ports to be disconnected
     */
    public static void disconnect(AbstractPort port) {
        calculated = false;
        disableButtons();
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