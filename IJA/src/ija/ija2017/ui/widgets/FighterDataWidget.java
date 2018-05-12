package ija.ija2017.ui.widgets;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.Data.DataFighter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;

public class FighterDataWidget extends AbstractDataWiget implements IDataWidget{
    Label labelHealth = new Label("Health:  ");
    Label labelPower = new Label("Power:  ");
    Label labelDexterity = new Label("Dexterity:  ");
    Label labelIntelligence = new Label("Intelligence:  ");

    Label dataHealth = new Label("0");
    Label dataPower = new Label("0");
    Label dataDexterity = new Label("0");
    Label dataIntelligence = new Label("0");

    public FighterDataWidget(Pane parent){
        super(parent);
        widgetBackgroud.setHeight(98);

        labelHealth.setTextFill(Color.gray(0.9));
        labelPower.setTextFill(Color.gray(0.9));
        labelDexterity.setTextFill(Color.gray(0.9));
        labelIntelligence.setTextFill(Color.gray(0.9));
        dataHealth.setTextFill(Color.gray(0.9));
        dataPower.setTextFill(Color.gray(0.9));
        dataDexterity.setTextFill(Color.gray(0.9));
        dataIntelligence.setTextFill(Color.gray(0.9));

        widgetText.setMinWidth(widgetBackgroud.getWidth());
        widgetText.setMaxWidth(widgetBackgroud.getWidth());
        widgetText.setText("Port 'fighter' data");
        widgetDataLabels.getChildren().addAll(labelHealth, labelPower, labelDexterity, labelIntelligence);
        widgetData.getChildren().addAll(dataHealth, dataPower, dataDexterity, dataIntelligence);
    }
    public void show(AbstractData data, MoveTo pos){
        dataHealth.setText(Double.toString(((DataFighter)data).Health));
        dataPower.setText(Double.toString(((DataFighter)data).Power));
        dataDexterity.setText(Double.toString(((DataFighter)data).Dexterity));
        dataIntelligence.setText(Double.toString(((DataFighter)data).Intelligence));
        widget.setLayoutX(pos.getX());
        widget.setLayoutY(pos.getY());
        widget.toFront();
    }

    public void hide(){widget.toBack();}
}
