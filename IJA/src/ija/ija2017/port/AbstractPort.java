package ija.ija2017.port;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.ui.BlockHandlers;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public abstract class AbstractPort {
    public AbstractPort(){
        path = new Path();
        path.setStrokeWidth(2);
        path.setStroke(Color.color(0.15,0.15,0.15,1));
        path.setOnMouseEntered(event -> BlockHandlers.handlePathEntered(path));
        path.setOnMouseEntered(event -> BlockHandlers.handlePathExited(path));
    }
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

    public Path path;
    public Path getPath() {return path;}
    public void setPath(Path path) {this.path = path;}



}
