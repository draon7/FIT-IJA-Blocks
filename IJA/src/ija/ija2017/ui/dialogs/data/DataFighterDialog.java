package ija.ija2017.ui.dialogs.data;

/**
 * Class for saving FighterData
 */
public class DataFighterDialog {
    double health;
    double power;
    double dexterity;
    double intelligence;

    /**
     * Constructor of FighterDataDialog class,
     * needs fighter data
     * @param health health of fighter
     * @param power power of fighter
     * @param dexterity dexterity of fighter
     * @param intelligence intelligence of fighter
     */
    public DataFighterDialog(double health, double power, double dexterity, double intelligence){
        this.health = health;
        this.power = power;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    /**
     * getter of health
     * @return health
     */
    public double getHealth(){return health;}

    /**
     * getter of power
     * @return power
     */
    public double getPower(){return power;}

    /**
     * getter of dexterity
     * @return dexterity
     */
    public double getDexterity(){return dexterity;}

    /**
     * getter of intelligence
     * @return intelligence
     */
    public double getIntelligence(){return intelligence;}

}
