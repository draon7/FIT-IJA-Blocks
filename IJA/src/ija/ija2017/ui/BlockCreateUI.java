package ija.ija2017.ui;

import ija.ija2017.blok.*;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * Class with methods for creating Block UI
 */
public final class BlockCreateUI {

    /**
     * UI constructor
     * @param blockReference reference to Block to create
     */
    public static void CreateBlockUI(AbstractBlockUI blockReference){

        Rectangle rect = createRectangle();

        Group block = new Group();
        block.getChildren().add(rect);

        Label text = createText("Block", rect);

        if(blockReference instanceof BlockAttackUI){
            text = createText("Attack", rect);}
        else if(blockReference instanceof BlockDefenseUI){
            text = createText("Defense", rect);}
        else if(blockReference instanceof BlockHealingUI){
            text = createText("Healing", rect);}
        else if(blockReference instanceof BlockTrainingUI){
            text = createText("Training", rect);}
        else if(blockReference instanceof BlockWeaponUpgradeUI){
            text = createText("WeaponUpgrade", rect);}

        block.getChildren().add(text);

        block.setManaged(false);

        blockReference.setBlock(block);
    }

    /**
     * Creates ports in Block and declares paths for ports
     * @param blockReference block to create path around
     */
    public static void CreatePortPathUI(AbstractBlockUI blockReference){
        Pane parent = blockReference.getParent();
        Group block = blockReference.getBlock();
        List<InputPort> inputPorts = blockReference.getInputPorts();
        List<OutputPort> outputPorts = blockReference.getOutputPorts();

        Rectangle blockRect = null;
        float portCount = 0;
        float index = 0;
        Color portColor = Color.WHITE;

        for(Node n : block.getChildren()){
            if (n instanceof Rectangle){
                blockRect = (Rectangle) n;
            }
        }
        portCount = inputPorts.size()+1;
        for(InputPort p : inputPorts){
            index++;
            switch (p.getDataType()){
                case attack:{
                    portColor = BlockColors.portColorAttack;
                    break;
                }
                case fighter:{
                    portColor = BlockColors.portColorFighter;
                    break;
                }
                case weapon:{
                    portColor = BlockColors.portColorWeapon;
                    break;
                }
            }
            p.setPortCircle(BlockCreateUI.createCircle(0, (index/portCount), blockRect, portColor));
            block.getChildren().add(p.getPortCircle());
            blockReference.addPortList(p.getPortCircle());
        }
        index = 0;
        portCount = outputPorts.size()+1;
        for(OutputPort p : outputPorts){
            index++;
            switch (p.getDataType()){
                case attack:{
                    portColor = BlockColors.portColorAttack;
                    break;
                }
                case fighter:{
                    portColor = BlockColors.portColorFighter;
                    break;
                }
                case weapon:{
                    portColor = BlockColors.portColorWeapon;
                    break;
                }
            }
            p.setPortCircle(BlockCreateUI.createCircle(1, (index/portCount), blockRect, portColor));
            block.getChildren().add(p.getPortCircle());
            blockReference.addPortList(p.getPortCircle());
        }
        parent.getChildren().add(block);

        inputPorts.forEach(p -> {
            parent.getChildren().add(p.getPath());
            blockReference.addPortPathList(p.getPath());
        });
        outputPorts.forEach(p -> {
            parent.getChildren().add(p.getPath());
            blockReference.addPortPathList(p.getPath());
        });
    }

    /**
     * Creates paths when reloaded
     * @param blockReference reference to block
     */
    public static void ReloadPaths(AbstractBlockUI blockReference){
        MoveTo start;
        MoveTo end;
        for(InputPort inPort : blockReference.getInputPorts()){
            OutputPort outPort = inPort.getConnection();
            if(outPort != null){
                Path pathOut = outPort.getPath();

                if(pathOut.getElements().size() > 1){continue;}
                start = new MoveTo();
                end = new MoveTo();
                end.setX(outPort.getPortCircle().getCenterX()+outPort.getPortCircle().getParent().getLayoutX());
                end.setY(outPort.getPortCircle().getCenterY()+outPort.getPortCircle().getParent().getLayoutY());

                Path path = inPort.getPath();
                Circle circle = inPort.getPortCircle();

                path.getElements().clear();
                start.setX(circle.getCenterX()+circle.getParent().getLayoutX());
                start.setY(circle.getCenterY()+circle.getParent().getLayoutY());
                path.getElements().add(start);

                System.out.println("In Port Y: " + start.getY());
                System.out.println("In Port X: " + start.getX());

                CubicCurveTo curve = new CubicCurveTo();
                BlockHandlers.calculateInputCurve(curve, start, end);
                path.getElements().add(curve);

            }
        }
        for(OutputPort outPort : blockReference.getOutputPorts()){
            InputPort inPort = outPort.getConnection();
            if(inPort != null){
                Path pathIn = inPort.getPath();

                if(pathIn.getElements().size() > 1){continue;}
                start = new MoveTo();
                end = new MoveTo();
                end.setX(inPort.getPortCircle().getCenterX()+inPort.getPortCircle().getParent().getLayoutX());
                end.setY(inPort.getPortCircle().getCenterY()+inPort.getPortCircle().getParent().getLayoutY());

                Path path = outPort.getPath();
                Circle circle = outPort.getPortCircle();

                path.getElements().clear();
                start.setX(circle.getCenterX()+circle.getParent().getLayoutX());
                start.setY(circle.getCenterY()+circle.getParent().getLayoutY());
                path.getElements().add(start);


                CubicCurveTo curve = new CubicCurveTo();
                BlockHandlers.calculateOutputCurve(curve, start, end);
                path.getElements().add(curve);
                path.toBack();

            }
        }
    }

    /**
     * Creates rectangle representing block
     * @return rectangle around block
     */
    public static Rectangle createRectangle(){
        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));
        rect.setStroke(Color.color(0.1,0.1,0.1,1));
        return rect;
    }

    /**
     * Method creates text description in block
     * @param s string to wrire
     * @param rect rectangle to fit text in
     * @return Label for block
     */
    public static Label createText(String s, Rectangle rect){
        Label text = new Label(s);
        text.setFont(Font.font("System", FontWeight.BOLD, 12));
        text.setTextFill(Color.color(0.8,0.8,0.8,1));
        text.setMinWidth(rect.getWidth());
        text.setMaxWidth(rect.getWidth());
        text.setAlignment(Pos.CENTER);
        text.setMouseTransparent(true);
        return text;
    }

    /**
     * Method creates circle for port representation
     * @param widthModifier position in rectangle
     * @param heightModifier position in rectangle
     * @param rect rectangle to create circle in
     * @param portColor colour of the circle
     * @return circle
     */
    public static Circle createCircle(float widthModifier, float heightModifier, Rectangle rect, Color portColor){
        return new Circle(rect.getX()+(rect.getWidth()*widthModifier), rect.getY()+(rect.getHeight()*heightModifier), BlockColors.circleRadius, portColor);//Color.ORANGE
    }
}
