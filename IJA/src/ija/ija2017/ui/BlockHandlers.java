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
                BlockHandlers.handlePortClicked(event, port, circle, pathStorage, 2,1);
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
                endPoint.setX(cubicCurveTo.getX()+deltaX);
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
    static void handlePortClicked(MouseEvent e, AbstractPort port, Circle circle, Path path, int numberOfPorts, int portIndex){
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
    public static void handlePortDragReleased(MouseEvent e, AbstractPort port, Path path){}
    private static void deletePath(Path p){p.getElements().clear();}

    public static void handlePortEntered(AbstractPort port, Path path)  {
        path.setStroke(BlockColors.strokeHoverColor); path.toFront();
        BlockConnectionHandling.showPortData(port);
    }
    public static void handlePortExited(Path path)   {
        path.setStroke(BlockColors.strokeColor); path.toBack();
        BlockConnectionHandling.hidePortData();
    }

    public static void handlePathEntered(Path path)  {path.setStroke(BlockColors.strokeHoverColor);  path.toFront();}
    public static void handlePathExited(Path path)   {path.setStroke(BlockColors.strokeColor);   path.toBack();}

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
