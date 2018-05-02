package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;

public abstract class AbstractPort {
    protected AbstractData data;
    public boolean isReady;

    public AbstractData getValue(){
        return data;
    }

    public void setData(AbstractData value) {
        this.data = value;
    }

    public boolean isReady() {
        return isReady;
    }
    public void setReady() {
        this.isReady = true;
    }

    public AbstractData.DataType getDataType() {
        return data.dataType;
    }


}
