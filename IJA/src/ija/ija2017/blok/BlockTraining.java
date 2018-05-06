package ija.ija2017.blok;

import ija.ija2017.Data.DataFighter;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.scene.layout.Pane;
/**
 * This Block Is impementation of AbstractBlockUI reassembling training fighter.
 */
public class BlockTraining extends AbstractBlockUI {
    private InputPort fighter = new InputPort(new DataFighter());
    private OutputPort fighterTrained = new OutputPort(new DataFighter());

    /**
     * constructor initialize inputPorts and outputPorts
     */
    public BlockTraining() {
        inputPorts.add(fighter);
        outputPorts.add(fighterTrained);
    }

    public void calculate(){
        DataFighter dataFighter = (DataFighter)this.fighter.getValue();
        DataFighter dataFighterHealed = (DataFighter)this.fighterTrained.getValue();

        dataFighterHealed.Health = dataFighter.Health*0.9;
        dataFighterHealed.Dexterity = dataFighter.Dexterity *1.1;
        dataFighterHealed.Intelligence = dataFighter.Intelligence *1.1;
        dataFighterHealed.Power = dataFighter.Power*1.1;
    }
    public boolean canStart(){
        return fighter.isReady();
    }

    public void setReady() {
        fighterTrained.setReady();
    }

    public void createUI(Pane pane){}


}
