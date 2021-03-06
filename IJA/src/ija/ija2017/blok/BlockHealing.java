package ija.ija2017.blok;

import ija.ija2017.Data.DataFighter;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.scene.layout.Pane;

/**
 * This Block Is impementation of AbstractBlockUI reassembling healing of the fighter.
 */
public class BlockHealing extends AbstractBlockUI {
    private InputPort fighter = new InputPort(new DataFighter());
    private OutputPort fighterHealed = new OutputPort(new DataFighter());

    /**
     * constructor initialize inputPorts and outputPorts
     */
    public BlockHealing() {
        inputPorts.add(fighter);
        outputPorts.add(fighterHealed);
    }

    public void calculate(){
        DataFighter dataFighter = (DataFighter)this.fighter.getValue();
        DataFighter dataFighterHealed = (DataFighter)this.fighterHealed.getValue();

        dataFighterHealed.Health = dataFighter.Health*1.2;
        dataFighterHealed.Dexterity = dataFighter.Dexterity;
        dataFighterHealed.Intelligence = dataFighter.Intelligence;
        dataFighterHealed.Power = dataFighter.Power;
    }
    public boolean canStart(){
        return (fighter.isReady());
    }

    public void setReady() {
        fighterHealed.setReady();
    }

    public void createUI(Pane pane){}
}
