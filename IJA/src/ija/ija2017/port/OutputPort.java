package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;

public class OutputPort extends AbstractPort {
    private InputPort connection;

    public OutputPort(AbstractData data) {
        this.connection = null;
        this.data = data;
    }
    public void setConnection(InputPort connection) {
        this.connection = connection;
    }
}
