package ija.ija2017.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public final class BlockCreateUI {
    private BlockCreateUI(){}

    public static Group CreateBlockAttackUI(){
        System.out.println("Creating BlockAttack UI");

        Rectangle rect = createRectangle();

        Group block = new Group();
        block.getChildren().add(rect);

        Label text = createText("Attack", rect);

        block.getChildren().add(text);

        block.setManaged(false);

        Circle circle = createCircle(0f, 1/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(0f,2/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,1/4f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,2/4f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,3/4f, rect);
        block.getChildren().add(circle);

        return block;
    }
    public static Group CreateBlockDefenseUI(){
        System.out.println("Creating BlockDefense UI");

        Rectangle rect = createRectangle();

        Group block = new Group();
        block.getChildren().add(rect);

        Label text = createText("Defense", rect);

        block.getChildren().add(text);

        block.setManaged(false);

        Circle circle = createCircle(0f, 1/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(0f,2/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,1/2f, rect);
        block.getChildren().add(circle);

        return block;
    }
    public static Group CreateBlockHealingUI(){
        System.out.println("Creating BlockHealing UI");

        Rectangle rect = createRectangle();

        Group block = new Group();
        block.getChildren().add(rect);

        Label text = createText("Healing", rect);

        block.getChildren().add(text);

        block.setManaged(false);

        Circle circle = createCircle(0f, 1/2f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,1/2f, rect);
        block.getChildren().add(circle);

        return block;
    }
    public static Group CreateBlockTrainingUI(){
        System.out.println("Creating BlockTraining UI");

        Rectangle rect = createRectangle();

        Group block = new Group();
        block.getChildren().add(rect);

        Label text = createText("Training", rect);

        block.getChildren().add(text);

        block.setManaged(false);

        Circle circle = createCircle(0f, 1/2f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,1/2f, rect);
        block.getChildren().add(circle);

        return block;
    }
    public static Group CreateBlockWeaponUpgradeUI(){
        System.out.println("Creating BlockWeaponUpgrade UI");

        Rectangle rect = createRectangle();

        Group block = new Group();
        block.getChildren().add(rect);

        Label text = createText("Weapon Upgrade", rect);

        block.getChildren().add(text);

        block.setManaged(false);

        Circle circle = createCircle(0f, 1/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(0f,2/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,1/3f, rect);
        block.getChildren().add(circle);
        circle = createCircle(1f,2/3f, rect);
        block.getChildren().add(circle);

        return block;
    }

    private static Rectangle createRectangle(){
        Rectangle rect = new Rectangle();
        rect.setHeight(75);
        rect.setWidth(125);
        rect.setFill(Color.gray(0.2));
        rect.setStroke(Color.color(0.1,0.1,0.1,1));
        return rect;
    }
    private static Label createText(String s, Rectangle rect){
        Label text = new Label(s);
        text.setFont(Font.font("System", FontWeight.BOLD, 12));
        text.setTextFill(Color.color(0.8,0.8,0.8,1));
        text.setMinWidth(rect.getWidth());
        text.setMaxWidth(rect.getWidth());
        text.setAlignment(Pos.CENTER);
        text.setMouseTransparent(true);
        return text;
    }
    private static Circle createCircle(float widthModifier, float heightModifier, Rectangle rect){
        Circle circle = new Circle(rect.getX()+(rect.getWidth()*widthModifier), rect.getY()+(rect.getHeight()*heightModifier),5, Color.ORANGE);
        return circle;
    }
}
