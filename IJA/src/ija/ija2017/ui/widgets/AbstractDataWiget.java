package ija.ija2017.ui.widgets;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class AbstractDataWiget {
    Pane parent;
    Group widget = new Group();
    HBox widgetDataContainer = new HBox();
    VBox widgetContainer = new VBox();
    VBox widgetDataLabels = new VBox();
    VBox widgetData = new VBox();
    Label widgetText = new Label("Port data");
    Rectangle widgetBackgroud = new Rectangle(135, 100);

    public AbstractDataWiget(Pane parent){
        this.parent = parent;
        this.parent.getChildren().add(widget);

        widget.toBack();

        widgetBackgroud.setFill(Color.gray(0.25));
        widgetBackgroud.setStroke(Color.gray(0.22));
        widgetText.setTextFill(Color.gray(0.9));

        widgetText.setMinWidth(widgetBackgroud.getWidth());
        widgetText.setMaxWidth(widgetBackgroud.getWidth());
        widgetText.setAlignment(Pos.CENTER);

        widgetDataContainer.setMaxWidth(Double.MAX_VALUE);
        widgetDataContainer.setAlignment(Pos.CENTER);
        widgetDataContainer.getChildren().addAll(widgetDataLabels, widgetData);

        widgetContainer.setSpacing(10);
        widgetContainer.getChildren().addAll(widgetText, widgetDataContainer);
        widget.getChildren().addAll(widgetBackgroud, widgetContainer);
    }

    private Pane getParent(){return  parent;}
    private Group getWidget(){return  widget;}
    private Label getWidgetText(){return  widgetText;}
    private Rectangle getWidgetBackgroud(){return  widgetBackgroud;}
}