package ija.ija2017.Data;
/**
 * This Class implements DataAttack,
 * dataFighter have four values, health, power, dexterity and intelligence.
 */
public class DataFighter extends AbstractData {
    public double Health;
    public double Power;
    public double Dexterity;
    public double Intelligence;

    /**
     * This method is constructor of Class DataFighter,  it assigns to its values
     * @param health represent healthiness of the fighter
     * @param power this value represents power of the fighter
     * @param dexterity this value represents dexterity of the fighter
     * @param intelligence this value represents inteligence of the fighter
     */
    public DataFighter (double health, double power, double dexterity, double intelligence){
        Health = health;
        Power = power;
        Dexterity = dexterity;
        Intelligence = intelligence;
        this.dataType = DataType.fighter;
    }

    /**
     * This method is constructor of the class DataFighter,
     * it sets health, power, dexterity and intelligence to 0
     */
    public DataFighter() {
        Health = 0;
        Power = 0;
        Dexterity = 0;
        Intelligence = 0;
        this.dataType = DataType.fighter;
    }

}
