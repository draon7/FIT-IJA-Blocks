package ija.ija2017.port;

public class InputPort extends AbstractPort {
    private OutputPort connection;

    public InputPort(DataType dataType, java.lang.Double value) {
        this.connection = connection;
        this.dataType = dataType;
        setValue(value);
    }
    private void updateValue(){
        if (connection != null) {
            setValue(connection.getValue());
        }
    }
    public Double getValue () {
        updateValue();
        return this.value;
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
}
