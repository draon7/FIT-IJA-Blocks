package ija.ija2017.blok;

import ija.ija2017.Data.DataFighter;
import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataWeapon;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import ija.ija2017.ui.BlockCreateUi;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * This Block Is impementation of AbstractBlockUI reassembling attack of the fighter with weapon.
 */
public class BlockAttack extends AbstractBlockUI {
    private InputPort fighter = new InputPort(new DataFighter());
    private InputPort weapon = new InputPort(new DataWeapon());
    private OutputPort attack = new OutputPort(new DataAttack());
    private OutputPort weaponOutput = new OutputPort(new DataWeapon());
    private OutputPort fighterOutput = new OutputPort(new DataFighter());

    /**
     * constructor initialize inputPorts and outputPorts
     */
    public BlockAttack() {
        inputPorts.add(fighter);
        inputPorts.add(weapon);
        outputPorts.add(attack);
        outputPorts.add(weaponOutput);
        outputPorts.add(fighterOutput);
    }

    public void calculate(){
        DataFighter dataFighter = (DataFighter)this.fighter.getValue();
        DataFighter dataFighterOutput = (DataFighter)this.fighterOutput.getValue();
        DataWeapon dataWeapon = (DataWeapon) this.weapon.getValue();
        DataWeapon dataWeaponOutput = (DataWeapon)this.weaponOutput.getValue();
        DataAttack dataAttack = (DataAttack)this.attack.getValue();


        dataFighterOutput.Health = dataFighter.Health;
        dataFighterOutput.Dexterity = dataFighter.Dexterity;
        dataFighterOutput.Intelligence = dataFighter.Intelligence;
        dataFighterOutput.Power = dataFighter.Power;

        dataWeaponOutput.Handeling = dataWeapon.Handeling;
        dataWeaponOutput.Weight = dataWeapon.Weight;


        dataAttack.AttackPower = dataFighter.Power*dataWeapon.Weight+dataFighter.Dexterity *dataWeapon.Handeling;
    }
    public boolean canStart(){
        return (fighter.isReady() && weapon.isReady());
    }

    public void setReady() {
        attack.setReady();
        fighter.setReady();
        weapon.setReady();
    }

/*
    double mouseX;
    double mouseY;*/

    /*public void createUI(Pane pane){
        Group block = (Group) BlockCreateUi.CreateBlockAttackUI();
        pane.getChildren().add(block);

        block.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            }
        });
        block.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getSceneX()-mouseX+block.getLayoutBounds().getMinX();
                double deltaY = event.getSceneY()-mouseY;
                double newX = deltaX + block.getLayoutX();
                double newY = deltaY + block.getLayoutY();

                System.out.println("Relocate X : Y -> " + newX + " : " + newY);
                block.relocate(newX, newY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            }
        });

        return;
    }*/


}
