package ija.ija2017.port;

public abstract class AbstractPort {
    public enum DataType {size, angle, surface}
    DataType dataType;
    protected java.lang.Double value;
    protected boolean isReady;

    public Double getValue(){
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isReady() {
        return isReady;
    }
    public void setReady() {
        this.isReady = true;
    }

    public DataType getDataType() {
        return dataType;
    }
}
