package ija.ija2017.Data;

public class DataAttack extends AbstractData {
    public double AttackPower;

    public DataAttack(double attackPower){
        this.dataType = DataType.attack;
        AttackPower = attackPower;
    }
    public DataAttack(){
        this.dataType = DataType.attack;
        AttackPower = 0;
    }
}
