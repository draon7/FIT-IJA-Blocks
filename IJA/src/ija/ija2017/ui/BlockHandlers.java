package ija.ija2017.ui;

import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class including block handlers
 */
public class BlockHandlers {

    /**
     * Adds handlers to block
     * @param blockReference reference to block
     */
    public static void AddHandlers(AbstractBlockUI blockReference){
        Group group = blockReference.getBlock();
        List<InputPort> inputPorts = blockReference.getInputPorts();
        List<OutputPort> outputPorts = blockReference.getOutputPorts();

        BlockHandlers.addMoveRemoveHandles(blockReference);

        int index = 0;
        for(Circle circle : blockReference.getPortList()){
            Path pathStorage;
            AbstractPort port;
            if(index < inputPorts.size()){
                port = inputPorts.get(index);
            }else{
                port = outputPorts.get(index-inputPorts.size());
            }
            pathStorage = port.getPath();
            circle.setOnDragDetected(event -> {
                circle.startFullDrag();
            });
            circle.setOnMousePressed(event -> {
                event.setDragDetect(true);
                group.setMouseTransparent(true);
                BlockHandlers.handlePortClicked(event, port, circle, pathStorage);
            });
            circle.setOnMouseReleased(event -> {
                group.setMouseTransparent(false);
                BlockHandlers.hanlePortReleased(port, circle, pathStorage);
            });
            circle.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                BlockHandlers.hanlePortDragged(event, blockReference, port);

            });
            circle.setOnMouseDragEntered(e->BlockHandlers.handlePortDragEntered(port ,circle));
            circle.setOnMouseDragExited(e->BlockHandlers.handlePortDragExited(port, circle));
            circle.setOnMouseDragReleased(e->BlockHandlers.handlePortDragReleased(e, port, pathStorage));

            circle.setOnMouseEntered(event -> BlockHandlers.handlePortEntered(port, pathStorage));
            circle.setOnMouseExited(event -> BlockHandlers.handlePortExited(pathStorage));

            //pathStorage.setOnMouseDragged(event -> BlockHandlers.hanlePortDragged(event, this, pathStorage));
            pathStorage.setOnMouseEntered(event -> BlockHandlers.handlePathEntered(pathStorage));
            pathStorage.setOnMouseExited(event -> BlockHandlers.handlePathExited(pathStorage));
            index++;
        }
    }

    /**
     * Adds mouse handlers to block base
     * @param blockReference reference to block
     */
    static void addMoveRemoveHandles(AbstractBlockUI blockReference){
        blockReference.getBlock().getChildren().get(0).setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                for(AbstractPort port : blockReference.getInputPorts())    {
                    BlockConnectionHandling.disconnect(port);}
                for(AbstractPort port : blockReference.getOutputPorts())   {
                    BlockConnectionHandling.disconnect(port);}
                blockReference.getParent().getChildren().remove(blockReference.getBlock());
                for(Path path : blockReference.getPortPathList()){blockReference.getParent().getChildren().remove(path);}
                BlockConnectionHandling.removeBlock(blockReference);
            }
            BlockHandlers.handleMoveStart(event, blockReference);
        });
        blockReference.getBlock().getChildren().get(0).setOnMouseDragged(event -> {
            BlockHandlers.handleMoveDrag(event, blockReference, blockReference.getPortPathList());
        });
    }

    /**
     * OnMousePressed block handle - resets mouse position
     * @param e MouseEvent
     * @param blockReference reference to block
     */
    static void handleMoveStart(MouseEvent e, AbstractBlockUI blockReference){
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());
    }

    /**
     * OnMouseDragged block handle - moves block and it's ports and paths
     * @param e
     * @param blockReference
     * @param paths
     */
    static void handleMoveDrag(MouseEvent e, AbstractBlockUI blockReference, ArrayList<Path> paths){
        List<InputPort> inputPorts = blockReference.getInputPorts();
        List<OutputPort> outputPorts = blockReference.getOutputPorts();
        double deltaX;
        double deltaY;

        if((e.getSceneX() - blockReference.getPositionX() + blockReference.getBlock().getLayoutBounds().getMinX() + blockReference.getBlock().getLayoutX()) < 0){
            deltaX = 0;
        }else if((blockReference.getParent().getWidth() - (blockReference.getBlock().getLayoutBounds().getMaxX() +  blockReference.getBlock().getLayoutX())) < 0){
            if(e.getSceneX() - blockReference.getPositionX() < 0){deltaX = e.getSceneX() - blockReference.getPositionX();}
            else{deltaX = 0;}
        }
        else{deltaX = e.getSceneX() - blockReference.getPositionX();}
        if((e.getSceneY() - blockReference.getPositionY() + blockReference.getBlock().getLayoutBounds().getMinY() + blockReference.getBlock().getLayoutY()) < 0){
            deltaY = 0;
        }else if((blockReference.getParent().getHeight() - (blockReference.getBlock().getLayoutBounds().getMaxY() + blockReference.getBlock().getLayoutY())) < 0){
            if(e.getSceneY() - blockReference.getPositionY() < 0){deltaY = e.getSceneY() - blockReference.getPositionY();}
            else{deltaY = 0;}
        }
        else{deltaY = e.getSceneY() - blockReference.getPositionY();}

        double newX = deltaX + blockReference.getBlock().getLayoutBounds().getMinX() + blockReference.getBlock().getLayoutX();
        double newY = deltaY + blockReference.getBlock().getLayoutBounds().getMinY() + blockReference.getBlock().getLayoutY();
        if(newX < 0){newX = 0;}
        if(newY < 0){newY = 0;}



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

                calculateOutputCurve(cubicCurveTo, startPoint, endPoint);
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
                endPoint.setX(cubicCurveTo.getX() + deltaX);
                endPoint.setY(cubicCurveTo.getY()+deltaY);

                calculateInputCurve(cubicCurveTo, startPoint, endPoint);
            }
        }

        blockReference.getBlock().relocate(newX, newY);
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());
        blockReference.setLastPositionX(newX);
        blockReference.setLastPositionY(newY);
        System.out.println("New posX: " + newX + " |New posY: " + newY);

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
                calculateInputCurve(cubicCurveTo, start, endPoint);
            }else {
                System.out.println("OutPort");
                calculateOutputCurve(cubicCurveTo, start, endPoint);
            }
        });
    }

    /**
     * OnMouseClicked port handle - handles port disconnection and resets paths and also sets highlight of port and path
     * @param e MouseEvent
     * @param port  port to be handled
     * @param circle portUI representation to be handled
     * @param path path corresponding to port to be handled
     */
    static void handlePortClicked(MouseEvent e, AbstractPort port, Circle circle, Path path){
        if(port instanceof InputPort)   {
            if(((InputPort)port).getConnection() != null)  {
                //port.getPath().getElements().clear();
                BlockConnectionHandling.disconnect(port);
            }
        }
        else if(port instanceof OutputPort){
            if(((OutputPort)port).getConnection() != null) {
                //port.getPath().getElements().clear();
                BlockConnectionHandling.disconnect(port);
            }
        }

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

        switch (port.getDataType()){
            case attack:{
                circle.setFill(BlockColors.portColorHoverAttack);
                circle.setRadius(BlockColors.circleRadiusHover);
                break;
            }
            case fighter:{
                circle.setFill(BlockColors.portColorHoverFighter);
                circle.setRadius(BlockColors.circleRadiusHover);
                break;
            }
            case weapon:{
                circle.setFill(BlockColors.portColorHoverWeapon);
                circle.setRadius(BlockColors.circleRadiusHover);
                break;
            }
        }

        //if      (port instanceof InputPort) {BlockConnectionHandling.setInput((InputPort) port);}
        //else if (port instanceof OutputPort){BlockConnectionHandling.setOutput((OutputPort) port);}
    }

    /**
     * OnMouseReleased port handle - tries to connect port and resets the highlight of port and path
     * @param port port to be handled
     * @param circle portUI to be Highlighted
     * @param path path to be Highlighted
     */
    static void hanlePortReleased(AbstractPort port, Circle circle,Path path) {
        path.setMouseTransparent(false);
        path.toBack();
        path.setStroke(BlockColors.strokeColor);

        switch (port.getDataType()){
            case attack:{
                circle.setFill(BlockColors.portColorAttack);
                circle.setRadius(BlockColors.circleRadius);
                break;
            }
            case fighter:{
                circle.setFill(BlockColors.portColorFighter);
                circle.setRadius(BlockColors.circleRadius);
                break;
            }
            case weapon:{
                circle.setFill(BlockColors.portColorWeapon);
                circle.setRadius(BlockColors.circleRadius);
                break;
            }
        }

        if(port instanceof InputPort) {
            if(!BlockConnectionHandling.setInput((InputPort)port)){
                deletePath(path);
                BlockConnectionHandling.removeInput();
                BlockConnectionHandling.removeOutput();
                System.out.println("Delete");
            }
        }
        else if(port instanceof OutputPort){
            if(!BlockConnectionHandling.setOutput((OutputPort)port)){
                deletePath(path);
                BlockConnectionHandling.removeInput();
                BlockConnectionHandling.removeOutput();
                System.out.println("Delete");
            }
        }
        else{
            deletePath(path);
        }
        BlockConnectionHandling.removeInput();
        BlockConnectionHandling.removeOutput();
    }

    /**
     * OnMouseDragged port handler - moves path with mouse
     * @param e MouseEvent
     * @param blockReference reference to block
     * @param port port to get path reference
     */
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
            path.setStroke(BlockColors.strokeHoverColor);
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
            path.setStroke(BlockColors.strokeHoverColor);
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

    /**
     * OnMouseDragEntered port handler - handles port that is entered during mouse drag
     * @param port port to be handled
     * @param circle portUI to be Highlighted
     */
    public static void handlePortDragEntered(AbstractPort port, Circle circle) {
        switch (port.getDataType()){
            case attack:{
                circle.setFill(BlockColors.portColorHoverAttack);
                circle.setRadius(BlockColors.circleRadiusHover);
                break;
            }
            case fighter:{
                circle.setFill(BlockColors.portColorHoverFighter);
                circle.setRadius(BlockColors.circleRadiusHover);
                break;
            }
            case weapon:{
                circle.setFill(BlockColors.portColorHoverWeapon);
                circle.setRadius(BlockColors.circleRadiusHover);
                break;
            }
        }
        if      (port instanceof InputPort) BlockConnectionHandling.setInput((InputPort)port);
        else if (port instanceof OutputPort) BlockConnectionHandling.setOutput((OutputPort)port);
    }

    /**
     * OnMouseDragExited port handler - handles port that is exited during mouse drag
     * @param port port to be handled
     * @param circle portUI to be reset
     */
    public static void handlePortDragExited(AbstractPort port, Circle circle) {
        switch (port.getDataType()){
        case attack:{
            circle.setFill(BlockColors.portColorAttack);
            circle.setRadius(BlockColors.circleRadius);
            break;
        }
        case fighter:{
            circle.setFill(BlockColors.portColorFighter);
            circle.setRadius(BlockColors.circleRadius);
            break;
        }
        case weapon:{
            circle.setFill(BlockColors.portColorWeapon);
            circle.setRadius(BlockColors.circleRadius);
            break;
        }
    }
        if      (port instanceof InputPort) BlockConnectionHandling.removeInput();
        else if (port instanceof OutputPort) BlockConnectionHandling.removeOutput();
    }
    /**
     * OnMouseDragRelease port handler - handles port where mouse is released during mouse drag
     * @param port port to be handled
     */
    public static void handlePortDragReleased(MouseEvent e, AbstractPort port, Path path){}

    /**
     * Helper function that deletes path of port
     * @param p port that contains path that is to be deleted
     */
    private static void deletePath(Path p){p.getElements().clear();}

    /**
     * OnMouseEntered port handler - shows data view in port and sets highlight to portUI and path that is hovered
     * @param port port from where the data will be displayed
     * @param path path to be Highlighted
     */
    public static void handlePortEntered(AbstractPort port, Path path)  {
        path.setStroke(BlockColors.strokeHoverColor); path.toFront();
        BlockConnectionHandling.showPortData(port);
    }
    /**
     * OnMouseExited port handler - hides data view in port and sets highlight to portUI and path that is hovered
     * @param path path highlight to be reset
     */
    public static void handlePortExited(Path path)   {
        path.setStroke(BlockColors.strokeColor); path.toBack();
        BlockConnectionHandling.hidePortData();
    }

    /**
     * OnMouseEntered path handler - Highlights path that is hovered
     * @param path path to be Highlighted
     */
    public static void handlePathEntered(Path path)  {path.setStroke(BlockColors.strokeHoverColor);  path.toFront();}
    /**
     * OnMouseExited path handler - resets Highlights of path that is exited
     * @param path path highlight to be reset
     */
    public static void handlePathExited(Path path)   {path.setStroke(BlockColors.strokeColor);   path.toBack();}

    /**
     * Helper method for calculating curve between 2 ports, that starts from output port
     * @param curve current curve reference
     * @param start curve start point
     * @param end   curve end point
     */
    public static void calculateOutputCurve(CubicCurveTo curve, MoveTo start, MoveTo end){
        System.out.println("Start: " + start + "|End: " + end);

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
    /**
     * Helper method for calculating curve between 2 ports, that starts from input port
     * @param curve current curve reference
     * @param start curve start point
     * @param end   curve end point
     */
    public static void calculateInputCurve(CubicCurveTo curve, MoveTo start, MoveTo end){
        System.out.println("Start: " + start + "|End: " + end);

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
