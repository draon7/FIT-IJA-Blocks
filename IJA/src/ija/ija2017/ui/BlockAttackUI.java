package ija.ija2017.ui;

import ija.ija2017.blok.BlockAttack;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class BlockAttackUI extends BlockAttack implements BlockUI {
    private double positionX;
    private double positionY;
    private BlockAttack blockCore;
    private Group block;
    private Pane parent;
    private Path pathIn1;

    public double getPositionX() {
        return positionX;
    }
    public void setPositionX(double positionX) {
        this.positionX = positionX;
        block.relocate(this.positionX, this.positionY);
    }
    public double getPositionY() {
        return positionY;
    }
    public void setPositionY(double positionY) {
        this.positionY = positionY;
        block.relocate(this.positionX, this.positionY);
    }


    public BlockAttackUI(Pane pane){
        parent = pane;
        blockCore = new BlockAttack();
        CreateBlockUI();
    }

    public void CreateBlockUI(){
        block = BlockCreateUi.CreateBlockAttackUI();
        parent.getChildren().add(block);
        pathIn1 = new Path();
        AddHandlers(block);
        parent.getChildren().add(pathIn1);
        pathIn1.toBack();

    }


    public void AddHandlers(Group group){
        group.getChildren().get(0).setOnMousePressed(event -> {
            positionX = event.getSceneX();
            positionY = event.getSceneY();
        });
        group.getChildren().get(0).setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - positionX + block.getLayoutBounds().getMinX();
            double deltaY = event.getSceneY() - positionY;
            double newX = deltaX + block.getLayoutX();
            double newY = deltaY + block.getLayoutY();

            block.relocate(newX, newY);
            positionX = event.getSceneX();
            positionY = event.getSceneY();
        });
        group.getChildren().get(1).setOnMousePressed(event -> {
            System.out.println(pathIn1.getElements());
            pathIn1.getElements().clear();
            System.out.println(pathIn1.getElements());
            MoveTo start = new MoveTo();
            start.setX(group.getLayoutX());
            //start.setY(group.getChildren().get(1).getLayoutY());
            start.setY(group.getLayoutY()+(group.getLayoutBounds().getMaxY()*1/3));
            pathIn1.getElements().add(start);
            CubicCurveTo curve = new CubicCurveTo();
            curve.setX(start.getX());
            curve.setY(start.getY());
            curve.setControlX1(start.getX());
            curve.setControlY1(start.getY());
            curve.setControlX2(start.getX());
            curve.setControlY2(start.getY());
            pathIn1.getElements().add(curve);
            pathIn1.toFront();
        });
        group.getChildren().get(1).setOnMouseReleased(event -> {
            pathIn1.toBack();
            System.out.println("MouseReleased");
        });
        group.getChildren().get(1).setOnMouseDragged(event -> {
            pathIn1.setStroke(Color.YELLOW);
            double currentX = event.getX()+group.getLayoutX();
            double currentY = event.getY()+group.getLayoutY();
            ((CubicCurveTo)pathIn1.getElements().get(1)).setX(currentX);
            ((CubicCurveTo)pathIn1.getElements().get(1)).setY(currentY);
            if(currentX < group.getLayoutX()){
                ((CubicCurveTo)pathIn1.getElements().get(1)).setControlX1(currentX - (currentX - group.getLayoutX())/2);
                ((CubicCurveTo)pathIn1.getElements().get(1)).setControlX2(currentX - (currentX - group.getLayoutX())/2);
                ((CubicCurveTo)pathIn1.getElements().get(1)).setControlY1(group.getLayoutY()+(group.getLayoutBounds().getMaxY()*1/3));
                ((CubicCurveTo)pathIn1.getElements().get(1)).setControlY2(currentY);

            }else{
                ((CubicCurveTo)pathIn1.getElements().get(1)).setControlX1(group.getLayoutX() - (currentX - group.getLayoutX()));
                ((CubicCurveTo)pathIn1.getElements().get(1)).setControlX2(currentX + (currentX - group.getLayoutX()));

                double deltaY = (group.getLayoutY()+(group.getLayoutBounds().getMaxY()*1/3)) - currentY;
                System.out.println(deltaY);
                if(currentY < (group.getLayoutY()+(group.getLayoutBounds().getMaxY()*1/3))){
                    if(deltaY < 75){deltaY = 75;}
                    ((CubicCurveTo)pathIn1.getElements().get(1)).setControlY1(group.getLayoutY() + (group.getLayoutBounds().getMaxY()*1/3) - deltaY);
                    ((CubicCurveTo)pathIn1.getElements().get(1)).setControlY2(currentY - deltaY);
                }else{
                    if(deltaY > -75){deltaY = -75;}
                    ((CubicCurveTo)pathIn1.getElements().get(1)).setControlY1(group.getLayoutY() + (group.getLayoutBounds().getMaxY()*1/3) - deltaY);
                    ((CubicCurveTo)pathIn1.getElements().get(1)).setControlY2(currentY - deltaY);
                }
            }

            System.out.println((pathIn1.getElements()));
        });
        group.getChildren().get(1).setOnMouseDragOver(event -> {
            pathIn1.setStroke(Color.BLACK);
            System.out.println("DragOver");
        });
        group.getChildren().get(1).setOnMouseEntered(event -> {
            pathIn1.setStroke(Color.YELLOW);
            pathIn1.toFront();
            System.out.println("EnteredCircle");
        });
        group.getChildren().get(1).setOnMouseExited(event -> {
            if(!(event.isPrimaryButtonDown())){
                pathIn1.setStroke(Color.BLACK);
                pathIn1.toBack();
            }
        });
        pathIn1.setOnMouseEntered(event -> {
            pathIn1.setStroke(Color.YELLOW);
            pathIn1.toFront();
        });
        pathIn1.setOnMouseExited(event -> {
            pathIn1.setStroke(Color.BLACK);
            pathIn1.toBack();
        });
    }
}