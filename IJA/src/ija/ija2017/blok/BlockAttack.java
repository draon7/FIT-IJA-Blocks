package ija.ija2017.blok;

import ija.ija2017.Data.DataFighter;
import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataWeapon;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;


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

        dataWeaponOutput.Handling = dataWeapon.Handling;
        dataWeaponOutput.Weight = dataWeapon.Weight;


        dataAttack.AttackPower = dataFighter.Power*dataWeapon.Weight+dataFighter.Dexterity *dataWeapon.Handling;
    }
    public boolean canStart(){
        return (fighter.isReady() && weapon.isReady());
    }

    public void setReady() {
        attack.setReady();
        weaponOutput.setReady();
        fighterOutput.setReady();
    }
}
