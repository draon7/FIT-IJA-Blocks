package ija.ija2017.Data;

public class DataFighter extends AbstractData {
    public double Health;
    public double Power;
    public double Dexterity;
    public double Intelligence;
    public DataFighter (double health, double power, double dexterity, double intelligence){
        Health = health;
        Power = power;
        Dexterity = dexterity;
        Intelligence = intelligence;
        this.dataType = DataType.fighter;
    }
    public DataFighter() {
        Health = 0;
        Power = 0;
        Dexterity = 0;
        Intelligence = 0;
        this.dataType = DataType.fighter;
    }

}
