package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.ui.BlockHandlers;
import javafx.scene.shape.Path;

/**
 * Abstract Class AbstractPort implements methods common for InputPort and OutputPort
 */
public abstract class AbstractPort {
    public AbstractPort(){
        path = new Path();
        path.setStrokeWidth(2);
        path.setOnMouseEntered(event -> BlockHandlers.handlePathEntered(path));
        path.setOnMouseEntered(event -> BlockHandlers.handlePathExited(path));
    }
    protected AbstractData data;
    public boolean isReady;

    /**
     * Method gets data in Port
     * @return data in port
     */
    public AbstractData getValue(){
        return data;
    }

    /**
     * Method sets data in port
     * @param value is value to save in port
     */
    public void setData(AbstractData value) {
        this.data = value;
    }

    /**
     * Method returns if port is ready to calculate
     * @return true if ready otherwise false
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Method sets port ready to calculate
     */
    public void setReady() {
        this.isReady = true;
    }

    /**
     * method returns dataType of port
     * @return DataType enum of type of the data
     */
    public AbstractData.DataType getDataType() {
        return data.dataType;
    }

    public Path path;

    /**
     * Method gets path rendered from port
     * @return Path path
     */
    public Path getPath() {return path;}

    /**
     * Method sets path rendered from port
     * @param path is path to be saved
     */
    public void setPath(Path path) {this.path = path;}



}
