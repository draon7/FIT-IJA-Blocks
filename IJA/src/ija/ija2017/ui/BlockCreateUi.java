package ija.ija2017.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public final class BlockCreateUi {
    private BlockCreateUi(){}

    public static Group CreateBlockAttackUI(){
        System.out.println("Creating BlockAttack UI");

        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));

        Group block = new Group();
        block.getChildren().add(rect);

        block.setManaged(false);

        Circle portInNode1 = new Circle();
        Circle portInNode2 = new Circle();
        Circle portOutNode1 = new Circle();
        Circle portOutNode2 = new Circle();
        Circle portOutNode3 = new Circle();

        portInNode1.setFill(Color.ORANGE);
        portInNode2.setFill(Color.ORANGE);
        portOutNode1.setFill(Color.ORANGE);
        portOutNode2.setFill(Color.ORANGE);
        portOutNode3.setFill(Color.ORANGE);

        portInNode1.setRadius(5);
        portInNode2.setRadius(5);
        portOutNode1.setRadius(5);
        portOutNode2.setRadius(5);
        portOutNode3.setRadius(5);

        portInNode1.setCenterX(rect.getX());
        portInNode1.setCenterY(rect.getY()+(rect.getHeight()*1/3));
        portInNode2.setCenterX(rect.getX());
        portInNode2.setCenterY(rect.getY()+(rect.getHeight()*2/3));

        portOutNode1.setCenterX(rect.getX()+rect.getWidth());
        portOutNode1.setCenterY(rect.getY()+(rect.getHeight()*1/4));
        portOutNode2.setCenterX(rect.getX()+rect.getWidth());
        portOutNode2.setCenterY(rect.getY()+(rect.getHeight()*2/4));
        portOutNode3.setCenterX(rect.getX()+rect.getWidth());
        portOutNode3.setCenterY(rect.getY()+(rect.getHeight()*3/4));

        block.getChildren().add(portInNode1);
        block.getChildren().add(portInNode2);
        block.getChildren().add(portOutNode1);
        block.getChildren().add(portOutNode2);
        block.getChildren().add(portOutNode3);
        System.out.println("BlockAttack UI Created");
        return block;
    }
    public static Group CreateBlockDefenseUI(){
        System.out.println("Creating BlockDefense UI");

        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));

        Group block = new Group();
        block.getChildren().add(rect);

        block.setManaged(false);

        Circle portInNode1 = new Circle();
        Circle portInNode2 = new Circle();
        Circle portOutNode1 = new Circle();

        portInNode1.setFill(Color.ORANGE);
        portInNode2.setFill(Color.ORANGE);
        portOutNode1.setFill(Color.ORANGE);

        portInNode1.setRadius(5);
        portInNode2.setRadius(5);
        portOutNode1.setRadius(5);

        portInNode1.setCenterX(rect.getX());
        portInNode1.setCenterY(rect.getY()+(rect.getHeight()*1/3));
        portInNode2.setCenterX(rect.getX());
        portInNode2.setCenterY(rect.getY()+(rect.getHeight()*2/3));

        portOutNode1.setCenterX(rect.getX()+rect.getWidth());
        portOutNode1.setCenterY(rect.getY()+(rect.getHeight()/2));

        block.getChildren().add(portInNode1);
        block.getChildren().add(portInNode2);
        block.getChildren().add(portOutNode1);
        System.out.println("BlockDefense UI Created");
        return block;
    }
    public static Group CreateBlockHealingUI(){
        System.out.println("Creating BlockDefense UI");

        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));

        Group block = new Group();
        block.getChildren().add(rect);

        block.setManaged(false);

        Circle portInNode1 = new Circle();
        Circle portOutNode1 = new Circle();

        portInNode1.setFill(Color.ORANGE);
        portOutNode1.setFill(Color.ORANGE);

        portInNode1.setRadius(5);
        portOutNode1.setRadius(5);

        portInNode1.setCenterX(rect.getX());
        portInNode1.setCenterY(rect.getY()+(rect.getHeight()/2));

        portOutNode1.setCenterX(rect.getX()+rect.getWidth());
        portOutNode1.setCenterY(rect.getY()+(rect.getHeight()/2));

        block.getChildren().add(portInNode1);
        block.getChildren().add(portOutNode1);
        System.out.println("BlockDefense UI Created");
        return block;
    }
    public static Group CreateBlockTrainingUI(){
        System.out.println("Creating BlockDefense UI");

        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));

        Group block = new Group();
        block.getChildren().add(rect);

        block.setManaged(false);

        Circle portInNode1 = new Circle();
        Circle portOutNode1 = new Circle();

        portInNode1.setFill(Color.ORANGE);
        portOutNode1.setFill(Color.ORANGE);

        portInNode1.setRadius(5);
        portOutNode1.setRadius(5);

        portInNode1.setCenterX(rect.getX());
        portInNode1.setCenterY(rect.getY()+(rect.getHeight()/2));
        portOutNode1.setCenterX(rect.getX()+rect.getWidth());
        portOutNode1.setCenterY(rect.getY()+(rect.getHeight()/2));

        block.getChildren().add(portInNode1);
        block.getChildren().add(portOutNode1);
        System.out.println("BlockDefense UI Created");
        return block;
    }
    public static Group CreateBlockWeaponUpgradeUI(){
        System.out.println("Creating BlockDefense UI");

        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));

        Group block = new Group();
        block.getChildren().add(rect);

        block.setManaged(false);

        Circle portInNode1 = new Circle();
        Circle portInNode2 = new Circle();
        Circle portOutNode1 = new Circle();
        Circle portOutNode2 = new Circle();

        portInNode1.setFill(Color.ORANGE);
        portInNode2.setFill(Color.ORANGE);
        portOutNode1.setFill(Color.ORANGE);
        portOutNode2.setFill(Color.ORANGE);

        portInNode1.setRadius(5);
        portInNode2.setRadius(5);
        portOutNode1.setRadius(5);
        portOutNode2.setRadius(5);

        portInNode1.setCenterX(rect.getX());
        portInNode1.setCenterY(rect.getY()+(rect.getHeight()*1/3));
        portInNode2.setCenterX(rect.getX());
        portInNode2.setCenterY(rect.getY()+(rect.getHeight()*2/3));

        portOutNode1.setCenterX(rect.getX()+rect.getWidth());
        portOutNode1.setCenterY(rect.getY()+(rect.getHeight()*1/3));
        portOutNode2.setCenterX(rect.getX()+rect.getWidth());
        portOutNode2.setCenterY(rect.getY()+(rect.getHeight()*2/3));

        block.getChildren().add(portInNode1);
        block.getChildren().add(portInNode2);
        block.getChildren().add(portOutNode1);
        block.getChildren().add(portOutNode2);
        System.out.println("BlockDefense UI Created");
        return block;
    }
}
