package ija.ija2017.Data;

import java.io.Serializable;

/**
 * This abstract Class implemets data and methods common for all data classes.
 */
public abstract class AbstractData implements Serializable {
    public enum DataType {fighter, weapon, attack}
    public DataType dataType;
}
