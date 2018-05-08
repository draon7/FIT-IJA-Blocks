package ija.ija2017.Data;

/**
 * This Class implements AbstractData, dataAttack have one value, AttackPower.
 */
public class DataAttack extends AbstractData {
    public double AttackPower;

    /**
     * This method is constructor of class DataAttack, it assigns to its values
     * @param attackPower is value of the attack
     */
    public DataAttack(double attackPower){
        this.dataType = DataType.attack;
        AttackPower = attackPower;
    }

    /**
     * This method is constructor of class DataAttack
     * it sets AttackPower to 0
     */
    public DataAttack(){
        this.dataType = DataType.attack;
        AttackPower = 0;
    }

    public double getAttackPower(){return  AttackPower;}
}
