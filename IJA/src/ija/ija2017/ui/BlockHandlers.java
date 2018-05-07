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

        MoveTo startPoint = new MoveTo();
        MoveTo endPoint = new MoveTo();

        for (InputPort inPort : inputPorts){
            OutputPort outPort = inPort.getConnection();
            if(outPort != null){
                Path path = outPort.getPath();
                if(path.getElements().size() < 2){continue;}
                double startX = ((MoveTo)path.getElements().get(0)).getX();
                double startY = ((MoveTo)path.getElements().get(0)).getY();
                CubicCurveTo cubicCurveTo = ((CubicCurveTo)path.getElements().get(1));

                startPoint.setX(startX);
                startPoint.setY(startY);
                endPoint.setX(cubicCurveTo.getX()+deltaX);
                endPoint.setY(cubicCurveTo.getY()+deltaY);

                calculateOutputCurve(cubicCurveTo, startPoint, endPoint, blockReference.getBlock().getLayoutBounds().getMaxY());
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

                startPoint.setX(startX);
                startPoint.setY(startY);
                endPoint.setX(cubicCurveTo.getX()+deltaX);
                endPoint.setY(cubicCurveTo.getY()+deltaY);

                calculateInputCurve(cubicCurveTo, startPoint, endPoint, blockReference.getBlock().getLayoutBounds().getMaxY());
            }
        }

        blockReference.getBlock().relocate(newX, newY);
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());

        double blockWidth = blockReference.getBlock().getLayoutBounds().getMaxX();

        paths.forEach(path -> {
            if(path.getElements().isEmpty())return;
            MoveTo start = (MoveTo)path.getElements().get(0);
            start.setX(start.getX()+deltaX);
            start.setY(start.getY()+deltaY);

            CubicCurveTo cubicCurveTo = ((CubicCurveTo)path.getElements().get(1));
            endPoint.setX(cubicCurveTo.getX());
            endPoint.setY(cubicCurveTo.getY());


            double blockX = blockReference.getBlock().getLayoutX();
            if(start.getX() < (blockX + (blockWidth/2))){
                System.out.println("InPort");
                calculateInputCurve(cubicCurveTo, start, endPoint, blockReference.getBlock().getLayoutBounds().getMaxY());
            }else {
                System.out.println("OutPort");
                calculateOutputCurve(cubicCurveTo, start, endPoint, blockReference.getBlock().getLayoutBounds().getMaxY());
            }
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
    static void hanlePortDragged(MouseEvent e, AbstractBlockUI blockReference, AbstractPort port){
        Path path;
        path = port.getPath();

        if(path.getElements().size() < 2){System.out.println("No elements in path"); return;} //Prevent NullPointer Errors

        Group g = blockReference.getBlock();
        double currentX = e.getX() + g.getLayoutX();
        double currentY = e.getY() + g.getLayoutY();
        double blockX = g.getLayoutX();
        double blockY = g.getLayoutY();
        double blockHeight = g.getLayoutBounds().getMaxY();
        double blockWidth = (g.getLayoutBounds().getMinX()+g.getLayoutBounds().getMaxX());
        g = null;

        MoveTo start = ((MoveTo)path.getElements().get(0));
        CubicCurveTo curve = ((CubicCurveTo)path.getElements().get(1));

        if(port instanceof InputPort) {
            path.setStroke(strokeHoverColor);
            path.toFront();
            curve.setX(currentX);
            curve.setY(currentY);

            if(currentX < blockX){
                curve.setControlX1(currentX - (currentX - blockX)/2);
                curve.setControlX2(currentX - (currentX - blockX)/2);
                curve.setControlY1(blockY + (blockHeight*1/2));
                curve.setControlY2(currentY);
            }else{

                curve.setControlX1(blockX - (currentX - blockX));
                curve.setControlX2(currentX + (currentX - blockX));

                double deltaY = (blockY + (blockHeight*1/2)) - currentY;

                //+ (blockReference.getBlock().getLayoutBounds().getMaxY()*5/4) => block position - 1/4 of block width
                if((currentY > (blockY - (blockHeight*1/4)) && (currentY < (blockY + (blockHeight*5/4))))){
                    if(currentY < blockY + (blockHeight/2)){//Upper half
                        if(deltaY < 75){deltaY = 75;}
                        curve.setControlY1(blockY + (blockHeight*1/2) - deltaY);
                        curve.setControlY2(currentY - deltaY);
                    }else{//Lower half
                        if(deltaY > -75){deltaY = -75;}
                        curve.setControlY1(blockY + (blockHeight*1/2) - deltaY);
                        curve.setControlY2(currentY - deltaY);
                    }
                    //+ (blockReference.getBlock().getLayoutBounds().getMaxY()*5/4) => block position + 5/4 of block width
                }else{
                    curve.setControlY1(currentY);
                    curve.setControlY2(blockY + (blockWidth/2));
                    if(Math.abs(blockX - currentX) < 35){
                        curve.setControlX1(blockX - 35);
                        curve.setControlX2(currentX + 35);
                    }
                }
            }
        }
        else if(port instanceof OutputPort){
            path.setStroke(strokeHoverColor);
            path.toFront();
            curve.setX(currentX);
            curve.setY(currentY);
            if(currentX > blockX + blockWidth){
                curve.setControlX1(currentX - (currentX - (blockX + blockWidth))/2);
                curve.setControlX2(currentX - (currentX - (blockX + blockWidth))/2);
                curve.setControlY1(blockY + (blockHeight*1/2));
                curve.setControlY2(currentY);

            }else{
                curve.setControlX1(blockX + blockWidth - (currentX - (blockX + blockWidth)));
                curve.setControlX2(currentX + (currentX - (blockX + blockWidth)));

                double deltaY = (blockY + (blockHeight*1/2)) - currentY;

                //+ (blockReference.getBlock().getLayoutBounds().getMaxY()*5/4) => block position - 1/4 of block width
                if((currentY > (blockY - (blockHeight*1/4)) && (currentY < (blockY + (blockHeight*5/4))))){
                    if(currentY < blockY + (blockHeight/2)){//Upper half
                        if(deltaY < 75){deltaY = 75;}
                        curve.setControlY1(blockY + (blockHeight*1/2) - deltaY);
                        curve.setControlY2(currentY - deltaY);
                    }else{//Lower half
                        if(deltaY > -75){deltaY = -75;}
                        curve.setControlY1(blockY + (blockHeight*1/2) - deltaY);
                        curve.setControlY2(currentY - deltaY);
                    }
                    //+ (blockReference.getBlock().getLayoutBounds().getMaxY()*5/4) => block position + 5/4 of block width
                }else{
                    curve.setControlY1(currentY);
                    curve.setControlY2(blockY + (blockWidth/2));
                    if(Math.abs(blockX + blockWidth - currentX) < 35){
                        curve.setControlX1(blockX + blockWidth + 35);
                        curve.setControlX2(currentX - 35);
                    }
                }
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

    private static void calculateOutputCurve(CubicCurveTo curve, MoveTo start, MoveTo end, double blockHeight){
        System.out.println("Start: " + start + "|End: " + end + " |height: " + blockHeight);

        double startX = start.getX();
        double startY = start.getY();
        double endX = end.getX();
        double endY = end.getY();

        curve.setX(endX);
        curve.setY(endY);


        if(start.getX() < end.getX()){
            curve.setControlX1(startX - (startX - endX)*2/3 );
            curve.setControlX2(endX + (startX - endX)*2/3 );
            curve.setControlY1(startY);
            curve.setControlY2(endY);
        }else{
            curve.setControlX1(startX + (startX - endX));
            curve.setControlX2(endX - (startX - endX));
            double deltaY = (startY - endY);

            if(startY < endY){//Under
                curve.setControlY1(endY);
                curve.setControlY2(startY);
            }else{//Above
                curve.setControlY1(endY);
                curve.setControlY2(startY);
            }

        }
    }
    private static void calculateInputCurve(CubicCurveTo curve, MoveTo start, MoveTo end, double blockHeight){
        System.out.println("Start: " + start + "|End: " + end + " |height: " + blockHeight);

        double startX = start.getX();
        double startY = start.getY();
        double endX = end.getX();
        double endY = end.getY();

        curve.setX(endX);
        curve.setY(endY);


        if(start.getX() > end.getX()){
            curve.setControlX1(startX - (startX - endX)*2/3 );
            curve.setControlX2(endX + (startX - endX)*2/3 );
            curve.setControlY1(startY);
            curve.setControlY2(endY);
        }else{
            curve.setControlX1(startX + (startX - endX));
            curve.setControlX2(endX - (startX - endX));
            double deltaY = (startY - endY);

            if(startY < endY){//Under
                curve.setControlY1(endY);
                curve.setControlY2(startY);
            }else{//Above
                curve.setControlY1(endY);
                curve.setControlY2(startY);
            }

        }
    }

}
