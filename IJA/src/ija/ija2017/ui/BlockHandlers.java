package ija.ija2017.ui;

import ija.ija2017.blok.AbstractBlockUI;
import ija.ija2017.blok.IBlock;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class BlockHandlers {
    private final static Color strokeColor = Color.color(0.15,0.15,0.15,1);
    private final static Color strokeHoverColor = Color.YELLOW;

    static void handleMoveStart(MouseEvent e, AbstractBlockUI blockReference){
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());
    }
    static void handleMoveDrag(MouseEvent e, AbstractBlockUI blockReference){
        double deltaX = e.getSceneX() - blockReference.getPositionX() + blockReference.getBlock().getLayoutBounds().getMinX();
        double deltaY = e.getSceneY() - blockReference.getPositionY();
        double newX = deltaX + blockReference.getBlock().getLayoutX();
        double newY = deltaY + blockReference.getBlock().getLayoutY();

        blockReference.getBlock().relocate(newX, newY);
        blockReference.setPositionX(e.getSceneX());
        blockReference.setPositionY(e.getSceneY());
    }
    static void handlePortClicked(MouseEvent e, /*AbstractBlockUI blockReference,*/ Circle circle, Path path, int numberOfPorts, int portIndex){
        path.getElements().clear();
        MoveTo start = new MoveTo();
        start.setX(circle.getCenterX()+circle.getParent().getLayoutX());
        start.setY(circle.getCenterY()+circle.getParent().getLayoutY());
        System.out.println();
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
    }
    static void hanlePortReleased(Path path){
        path.toBack();
        path.setStroke(Color.BLACK);
    }
    static void hanlePortDragged(MouseEvent e, AbstractBlockUI blockReference, Path path){
        path.setStroke(Color.YELLOW);
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
            System.out.println(deltaY);
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
    public static void handlePortDragOver(Path path) {path.setStroke(strokeColor);}
    public static void handlePathEntered(Path path)  {path.setStroke(strokeHoverColor);  path.toFront();}
    public static void handlePathExited(Path path)   {path.setStroke(strokeColor);   path.toBack();}
    public static void handlePortEntered(Path path)  {path.setStroke(strokeHoverColor);  path.toFront();}
    public static void handlePortExited(Path path)   {path.setStroke(strokeColor);   path.toBack();}

}
