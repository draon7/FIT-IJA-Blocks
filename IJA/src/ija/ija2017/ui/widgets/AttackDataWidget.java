package ija.ija2017.ui.widgets;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataFighter;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;

public class AttackDataWidget extends AbstractDataWiget implements IDataWidget {
    Label labelAttackPower = new Label("Attack Power:  ");

    Label dataAttackPower = new Label("0");

    public AttackDataWidget(Pane parent){
        super(parent);
        widgetBackgroud.setHeight(48);

        labelAttackPower.setTextFill(Color.gray(0.9));
        dataAttackPower.setTextFill(Color.gray(0.9));
        widgetText.setMinWidth(widgetBackgroud.getWidth());
        widgetText.setMaxWidth(widgetBackgroud.getWidth());
        widgetText.setText("Port 'attack' data");
        widgetDataLabels.getChildren().addAll(labelAttackPower);
        widgetData.getChildren().addAll(dataAttackPower);
    }
    public void show(AbstractData data, MoveTo pos){
        dataAttackPower.setText(Double.toString(((DataAttack)data).AttackPower));
        widget.setLayoutX(pos.getX());
        widget.setLayoutY(pos.getY());
        widget.toFront();
    }

    public void hide(){widget.toBack();}
}
