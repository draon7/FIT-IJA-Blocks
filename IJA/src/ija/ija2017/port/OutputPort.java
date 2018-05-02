package ija.ija2017.port;

public class OutputPort extends AbstractPort {
    private InputPort connection;

    public OutputPort(DataType dataType, java.lang.Double value) {
        this.connection = connection;
        this.dataType = dataType;
        setValue(value);
    }
    public void setConnection(InputPort connection) {
        this.connection = connection;
    }
}
