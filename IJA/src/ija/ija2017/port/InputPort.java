package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;

/**
 * Class InputPort is implementation of abstract class Abstract port
 */
public class InputPort extends AbstractPort {
    private OutputPort connection;

    /**
     * Constructor initialize port connection to null and data to data
     * @param data value of data
     */
    public InputPort(AbstractData data) {
        this.connection = null;
        this.data = data;
    }

    /**
     * Method updates data in port from connected OutputPort
     */
    private void updateValue(){
        if (connection != null) {
            setData(connection.getValue());
        }
    }

    /**
     * Method gets value in port
     * @return data in port
     */
    public AbstractData getValue () {
        updateValue();
        return this.data;
    }

    /**
     * Method pdates readiness of the port, of conected port is ready sets
     * port ready
     */
    private void updateReady() {
        if (connection == null || connection.isReady()) {
            this.setReady();
        }
    }

    /**
     * Method return if the port is ready or not
     * @return true if is ready otherwise false
     */
    public boolean isReady() {
        this.updateReady();
        return this.isReady;
    }

    /**
     * Method sets connection to given OutputPort
     * @param connection OutputPort to connect to
     */
    public void setConnection(OutputPort connection) {
        this.connection = connection;
    }
}
