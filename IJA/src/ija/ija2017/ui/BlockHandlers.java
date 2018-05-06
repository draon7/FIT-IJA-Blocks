package ija.ija2017.ui;

import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.IBlock;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockHandlers {
    private final static Color strokeColor = Color.color(0.15,0.15,0.15,1);
    private final static Color strokeHoverColor = Color.YELLOW;
    private final static Color circleColor = Color.ORANGE;
    private final static Color circleHoverColor = Color.LIGHTYELLOW;

    static void addMoveRemoveHandles(AbstractBlockUI blockRef){
        blockRef.getBlock().getChildren().get(0).setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                for(AbstractPort port : blockRef.getInputPorts())    {BlockConectionHandling.disconnect(port);}
                for(AbstractPort port : blockRef.getOutputPorts())   {BlockConectionHandling.disconnect(port);}
                blockRef.getParent().getChildren().remove(blockRef.getBlock());
                for(Path path : blockRef.getPortPathList()){blockRef.getParent().getChildren().remove(path);}
            }
            BlockHandlers.handleMoveStart(event, blockRef);
        });
        blockRef.getBlock().getChildren().get(0).setOnMouseDragged(event -> {
            BlockHandlers.handleMoveDrag(event, blockRef, blockRef.getPortPathList());
        });
    }

    static void handleMoveStart(MouseEvent e, AbstractBlockUI blockReference){
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());
    }
    static void handleMoveDrag(MouseEvent e, AbstractBlockUI blockReference, ArrayList<Path> paths){
        List<InputPort> inputPorts = blockReference.getInputPorts();
        List<OutputPort> outputPorts = blockReference.getOutputPorts();

        double deltaX = e.getSceneX() - blockReference.getPositionX();
        double deltaY = e.getSceneY() - blockReference.getPositionY();
        double newX = deltaX + blockReference.getBlock().getLayoutBounds().getMinX() + blockReference.getBlock().getLayoutX();
        double newY = deltaY + blockReference.getBlock().getLayoutBounds().getMinY() + blockReference.getBlock().getLayoutY();

        for (InputPort inPort : inputPorts){
            OutputPort outPort = inPort.getConnection();
            if(outPort != null){
                Path path = outPort.getPath();
                if(path.getElements().size() < 2){continue;}
                double startX = ((MoveTo)path.getElements().get(0)).getX();
                double startY = ((MoveTo)path.getElements().get(0)).getY();
                CubicCurveTo cubicCurveTo = ((CubicCurveTo)path.getElements().get(1));
                cubicCurveTo.setX(cubicCurveTo.getX()+deltaX);
                cubicCurveTo.setY(cubicCurveTo.getY()+deltaY);
                cubicCurveTo.setControlX1(cubicCurveTo.getControlX1()+deltaX/2);
                cubicCurveTo.setControlY1(cubicCurveTo.getControlY1()+deltaY/2);
                cubicCurveTo.setControlX2(cubicCurveTo.getControlX2()+deltaX/2);
                cubicCurveTo.setControlY2(cubicCurveTo.getControlY2()+deltaY/2);
            }
        }
        for (OutputPort outPort : outputPorts){
            InputPort inPort = outPort.getConnection();
            if(inPort != null){
                Path path = inPort.getPath();
                if(path.getElements().size() < 2){continue;}
                double startX = ((MoveTo)path.getElements().get(0)).getX();
                double startY = ((MoveTo)path.getElements().get(0)).getY();
                CubicCurveTo cubicCurveTo = ((CubicCurveTo)path.getElements().get(1));
                cubicCurveTo.setX(cubicCurveTo.getX()+deltaX);
                cubicCurveTo.setY(cubicCurveTo.getY()+deltaY);
                cubicCurveTo.setControlX1(cubicCurveTo.getControlX1()+deltaX/2);
                cubicCurveTo.setControlY1(cubicCurveTo.getControlY1()+deltaY/2);
                cubicCurveTo.setControlX2(cubicCurveTo.getControlX2()+deltaX/2);
                cubicCurveTo.setControlY2(cubicCurveTo.getControlY2()+deltaY/2);
            }
        }

        blockReference.getBlock().relocate(newX, newY);
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());
        paths.forEach(path -> {
            if(path.getElements().isEmpty())return;
            MoveTo start = (MoveTo)path.getElements().get(0);
            start.setX(start.getX()+deltaX);
            start.setY(start.getY()+deltaY);
        });
    }
    static void handlePortClicked(MouseEvent e, AbstractPort port, Circle circle, Path path, int numberOfPorts, int portIndex){
        if(port instanceof InputPort)   {
            if(((InputPort)port).getConnection() != null)  {
                //port.getPath().getElements().clear();
                BlockConectionHandling.disconnect(port);
            }
        }
        else if(port instanceof OutputPort){
            if(((OutputPort)port).getConnection() != null) {
                //port.getPath().getElements().clear();
                BlockConectionHandling.disconnect(port);
            }
        }
        // TODO: 06.05.2018 Disconnect

        path.setMouseTransparent(true);
        path.getElements().clear();
        MoveTo start = new MoveTo();
        start.setX(circle.getCenterX()+circle.getParent().getLayoutX());
        start.setY(circle.getCenterY()+circle.getParent().getLayoutY());
        path.getElements().add(start);
        CubicCurveTo curve = new CubicCurveTo();
        curve.setX(start.getX());
        curve.setY(start.getY());
        curve.setControlX1(start.getX());
        curve.setControlY1(start.getY());
        curve.setControlX2(start.getX());
        curve.setControlY2(start.getY());
        path.getElements().add(curve);
        path.toFront();

        circle.setFill(circleHoverColor);

        //if      (port instanceof InputPort) {BlockConectionHandling.setInput((InputPort) port);}
        //else if (port instanceof OutputPort){BlockConectionHandling.setOutput((OutputPort) port);}
    }
    static void hanlePortReleased(AbstractPort port, Circle circle,Path path) {
        path.setMouseTransparent(false);
        path.toBack();
        path.setStroke(strokeColor);

        circle.setFill(circleColor);

        if(port instanceof InputPort) {
            if(!BlockConectionHandling.setInput((InputPort)port)){
                deletePath(path);
                BlockConectionHandling.removeInput();
                BlockConectionHandling.removeOutput();
                System.out.println("Delete");
            }
        }
        else if(port instanceof OutputPort){
            if(!BlockConectionHandling.setOutput((OutputPort)port)){
                deletePath(path);
                BlockConectionHandling.removeInput();
                BlockConectionHandling.removeOutput();
                System.out.println("Delete");
            }
        }
        else{
            deletePath(path);
        }
        BlockConectionHandling.removeInput();
        BlockConectionHandling.removeOutput();
    }
    static void hanlePortDragged(MouseEvent e, AbstractBlockUI blockReference, Path path){
        if(path.getElements().size() < 2){System.out.println("No elements in path");return;} //Prevent NullPointer Errors
        path.setStroke(strokeHoverColor);
        path.toFront();
        double currentX = e.getX()+blockReference.getBlock().getLayoutX();
        double currentY = e.getY()+blockReference.getBlock().getLayoutY();
        ((CubicCurveTo)path.getElements().get(1)).setX(currentX);
        ((CubicCurveTo)path.getElements().get(1)).setY(currentY);
        if(currentX < blockReference.getBlock().getLayoutX()){
            ((CubicCurveTo)path.getElements().get(1)).setControlX1(currentX - (currentX - blockReference.getBlock().getLayoutX())/2);
            ((CubicCurveTo)path.getElements().get(1)).setControlX2(currentX - (currentX - blockReference.getBlock().getLayoutX())/2);
            ((CubicCurveTo)path.getElements().get(1)).setControlY1(blockReference.getBlock().getLayoutY()+(blockReference.getBlock().getLayoutBounds().getMaxY()*1/3));
            ((CubicCurveTo)path.getElements().get(1)).setControlY2(currentY);

        }else{
            ((CubicCurveTo)path.getElements().get(1)).setControlX1(blockReference.getBlock().getLayoutX() - (currentX - blockReference.getBlock().getLayoutX()));
            ((CubicCurveTo)path.getElements().get(1)).setControlX2(currentX + (currentX - blockReference.getBlock().getLayoutX()));

            double deltaY = (blockReference.getBlock().getLayoutY()+(blockReference.getBlock().getLayoutBounds().getMaxY()*1/3)) - currentY;
            if(currentY < (blockReference.getBlock().getLayoutY()+(blockReference.getBlock().getLayoutBounds().getMaxY()*1/3))){
                if(deltaY < 75){deltaY = 75;}
                ((CubicCurveTo)path.getElements().get(1)).setControlY1(blockReference.getBlock().getLayoutY() + (blockReference.getBlock().getLayoutBounds().getMaxY()*1/3) - deltaY);
                ((CubicCurveTo)path.getElements().get(1)).setControlY2(currentY - deltaY);
            }else{
                if(deltaY > -75){deltaY = -75;}
                ((CubicCurveTo)path.getElements().get(1)).setControlY1(blockReference.getBlock().getLayoutY() + (blockReference.getBlock().getLayoutBounds().getMaxY()*1/3) - deltaY);
                ((CubicCurveTo)path.getElements().get(1)).setControlY2(currentY - deltaY);
            }
        }
    }
    public static void handlePortDragEntered(AbstractPort port, Circle circle) {
        circle.setFill(circleHoverColor);
        if      (port instanceof InputPort) BlockConectionHandling.setInput((InputPort)port);
        else if (port instanceof OutputPort)BlockConectionHandling.setOutput((OutputPort)port);
    }
    public static void handlePortDragExited(AbstractPort port, Circle circle) {
        circle.setFill(circleColor);
        if      (port instanceof InputPort) BlockConectionHandling.removeInput();
        else if (port instanceof OutputPort)BlockConectionHandling.removeOutput();
    }
    public static void handlePortDragReleased(MouseEvent e, AbstractPort port, Path path){}
    private static void deletePath(Path p){p.getElements().clear();}

    public static void handlePortEntered(Path path)  {path.setStroke(strokeHoverColor); path.toFront();}
    public static void handlePortExited(Path path)   {path.setStroke(strokeColor); path.toBack();}

    public static void handlePathEntered(Path path)  {path.setStroke(strokeHoverColor);  path.toFront();}
    public static void handlePathExited(Path path)   {path.setStroke(strokeColor);   path.toBack();}

}
