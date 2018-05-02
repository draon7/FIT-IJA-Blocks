package ija.ija2017.Data;

public class DataWeapon extends AbstractData {
    public double Handeling;
    public double Weight;

    public DataWeapon (double handeling, double weight) {
        Handeling = handeling;
        Weight = weight;
        this.dataType = DataType.weapon;
    }
    public DataWeapon() {
        Handeling = 0;
        Weight = 0;
        this.dataType = DataType.weapon;
    }
}
