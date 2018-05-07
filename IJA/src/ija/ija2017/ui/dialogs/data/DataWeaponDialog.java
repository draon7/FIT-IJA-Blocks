package ija.ija2017.ui.dialogs.data;

public class DataWeaponDialog {
    public DataWeaponDialog(double handeling, double weight){
        this.handeling = handeling;
        this.weight = weight;
    }
    double handeling;
    double weight;

    public double getHandeling(){return handeling;}
    public double getWeight(){return weight;}
}
