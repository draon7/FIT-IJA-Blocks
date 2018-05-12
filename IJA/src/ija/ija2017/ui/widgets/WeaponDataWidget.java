package ija.ija2017.ui.widgets;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.Data.DataFighter;
import ija.ija2017.Data.DataWeapon;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;

public class WeaponDataWidget extends AbstractDataWiget implements IDataWidget{
    Label labelHandling = new Label("Handling:  ");
    Label labelWeight = new Label("Weight:  ");

    Label dataHandling = new Label("0");
    Label dataWeight = new Label("0");

    public WeaponDataWidget(Pane parent){
        super(parent);
        widgetBackgroud.setHeight(65);

        labelHandling.setTextFill(Color.gray(0.9));
        labelWeight.setTextFill(Color.gray(0.9));
        dataHandling.setTextFill(Color.gray(0.9));
        dataWeight.setTextFill(Color.gray(0.9));

        widgetText.setMinWidth(widgetBackgroud.getWidth());
        widgetText.setMaxWidth(widgetBackgroud.getWidth());
        widgetText.setText("Port 'weapon' data");
        widgetDataLabels.getChildren().addAll(labelHandling, labelWeight);
        widgetData.getChildren().addAll(dataHandling, dataWeight);
    }
    public void show(AbstractData data, MoveTo pos){
        dataHandling.setText(Double.toString(((DataWeapon)data).Handling));
        dataWeight.setText(Double.toString(((DataWeapon)data).Weight));
        widget.setLayoutX(pos.getX());
        widget.setLayoutY(pos.getY());
        widget.toFront();
    }

    public void hide(){widget.toBack();}
}
