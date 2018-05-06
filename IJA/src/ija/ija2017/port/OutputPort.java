package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;

/**
 * Class inplement abstract class AbstractPort
 */
public class OutputPort extends AbstractPort {
    private InputPort connection;

    /**
     * Constructor sets connection to null a nd data to given value
     * @param data value to set port with
     */
    public OutputPort(AbstractData data) {
        this.connection = null;
        this.data = data;
    }

    /**
     * Method sets connection to given OutputPort
     * @param connection OutputPort to connect with
     */
    public void setConnection(InputPort connection) {
        this.connection = connection;
    }

    /**
     * Method gets InputPort connected to
     * @return connected InputPort
     */
    public InputPort getConnection() {
        return connection;
    }
}
