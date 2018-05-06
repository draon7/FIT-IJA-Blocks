package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;

public class InputPort extends AbstractPort {
    private OutputPort connection;

    public InputPort(AbstractData data) {
        this.connection = null;
        this.data = data;
    }
    private void updateValue(){
        if (connection != null) {
            setData(connection.getValue());
        }
    }
    public AbstractData getValue () {
        updateValue();
        return this.data;
    }

    private void updateReady() {
        if (connection == null || connection.isReady()) {
            this.setReady();
        }
    }
    public boolean isReady() {
        this.updateReady();
        return this.isReady;
    }

    public void setConnection(OutputPort connection) {
        this.connection = connection;
    }
    public OutputPort getConnection() {return this.connection;}
}
