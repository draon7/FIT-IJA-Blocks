package ija.ija2017.blok;

import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataFighter;
import ija.ija2017.Data.DataWeapon;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

public class BlockWeaponUpgrade extends AbstractBlock {
    private InputPort fighter = new InputPort(new DataFighter());
    private InputPort weapon = new InputPort(new DataWeapon());
    private OutputPort fighterOutput = new OutputPort(new DataFighter());
    private OutputPort weaponUpgraded = new OutputPort(new DataAttack());

    public BlockWeaponUpgrade() {
        inputPorts.add(fighter);
        inputPorts.add(weapon);
        outputPorts.add(fighterOutput);
        outputPorts.add(weaponUpgraded);
    }

    public void calculate(){
        DataFighter dataFighter = (DataFighter)this.fighter.getValue();
        DataFighter dataFighterOutput = (DataFighter)this.fighterOutput.getValue();
        DataWeapon dataWeapon = (DataWeapon)this.weapon.getValue();
        DataWeapon dataWeaponUpgraded = (DataWeapon)this.weaponUpgraded.getValue();


        dataFighterOutput.Health = dataFighter.Health;
        dataFighterOutput.Dexterity = dataFighter.Dexterity *1.05;
        dataFighterOutput.Intelligence = dataFighter.Intelligence *1.05;
        dataFighterOutput.Power = dataFighter.Power;

        dataWeaponUpgraded.Handeling = dataWeapon.Handeling*1.1;
        dataWeaponUpgraded.Weight = dataWeapon.Weight*1.1;
    }
    public boolean canStart(){
        return (fighter.isReady() && weapon.isReady());
    }

    public void setReady() {
        fighter.setReady();
        weaponUpgraded.setReady();
    }
}
