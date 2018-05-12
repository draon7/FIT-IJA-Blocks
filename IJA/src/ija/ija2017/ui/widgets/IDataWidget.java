package ija.ija2017.ui.widgets;

import ija.ija2017.Data.AbstractData;
import javafx.scene.shape.MoveTo;

public interface IDataWidget {
    public void show(AbstractData data, MoveTo pos);
    public void hide();
}
