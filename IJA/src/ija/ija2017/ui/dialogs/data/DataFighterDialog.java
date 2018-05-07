package ija.ija2017.ui.dialogs.data;

public class DataFighterDialog {
    double health;
    double power;
    double dexterity;
    double intelligence;

    public DataFighterDialog(double health, double power, double dexterity, double intelligence){
        this.health = health;
        this.power = power;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    public double getHealth(){return health;}
    public double getPower(){return power;}
    public double getDexterity(){return dexterity;}
    public double getIntelligence(){return intelligence;}

}
