package ija.ija2017.ui.dialogs.data;

/**
 * Class for saving DataWeapon
 */
public class DataWeaponDialog {

    /**
     * Constructor of the class DataWeaponDialog,
     * needs weapon data
     * @param handeling handeling of weapon
     * @param weight weight of weapon
     */
    public DataWeaponDialog(double handeling, double weight){
        this.handeling = handeling;
        this.weight = weight;
    }
    double handeling;
    double weight;

    /**
     * getter for handeling
     * @return handeling
     */
    public double getHandeling(){return handeling;}

    /**
     * getter of weight
     * @return weight
     */
    public double getWeight(){return weight;}
}
