package ija.ija2017.Data;
/**
* This Class implements DataAttack,
* dataWeapon have two values, height and handeling.
*/
public class DataWeapon extends AbstractData {
    public double Handeling;
    public double Weight;

    /**
     * This method is constructor of DataWeapon, it assigns to its values
     * @param handeling represents handeling of the weapon
     * @param weight represents weight of the weapon
     */
    public DataWeapon (double handeling, double weight) {
        Handeling = handeling;
        Weight = weight;
        this.dataType = DataType.weapon;
    }

    /**
     * This metod is constructor of DataWeapon, it asigns zero to Handeling and weight
     */
    public DataWeapon() {
        Handeling = 0;
        Weight = 0;
        this.dataType = DataType.weapon;
    }
}
