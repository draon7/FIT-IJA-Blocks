package ija.ija2017.blok;

import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataFighter;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.scene.layout.Pane;

public class BlockDefense extends AbstractBlockUI {

    private InputPort fighter = new InputPort(new DataFighter());
    private InputPort attack = new InputPort(new DataAttack());
    private OutputPort fighterAttacked = new OutputPort(new DataFighter());

    public BlockDefense() {
        inputPorts.add(fighter);
        inputPorts.add(attack);
        outputPorts.add(fighterAttacked);
    }

    public void calculate(){
        DataFighter dataFighter = (DataFighter)this.fighter.getValue();
        DataAttack dataAttack = (DataAttack) this.attack.getValue();
        DataFighter dataFighterAttacked = (DataFighter)this.fighterAttacked.getValue();

        dataFighterAttacked.Health = dataFighter.Health-dataAttack.AttackPower;
        dataFighterAttacked.Dexterity = dataFighter.Dexterity;
        dataFighterAttacked.Intelligence = dataFighter.Intelligence;
        dataFighterAttacked.Power = dataFighter.Power;
    }
    public boolean canStart(){
        return (fighter.isReady() && attack.isReady());
    }

    public void setReady() {
        fighterAttacked.setReady();
    }

    public void createUI(Pane pane){}
}
