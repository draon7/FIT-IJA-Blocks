package ija.ija2017.ui.dialogs.data;

/**
 * Class for saving dialog values.
 */
public class DataAttackDialog {
    double attackPower;

    /**
     * Constructor of the class, sets attackPower
     * @param attackPower attackPower
     */
    public DataAttackDialog(Double attackPower){
        this.attackPower = attackPower;
    }

    /**
     * Getter of attackPower
     * @return attacPower value
     */
    public double getAttackPower(){return attackPower;}
}
