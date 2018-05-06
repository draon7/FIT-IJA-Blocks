package ija.ija2017.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class MainWindowController {


    @FXML private AnchorPane mainViewPane;
    @FXML private TabPane tabPane;
    @FXML private Tab addScheme;
    @FXML private Button calculateButton;
    @FXML private Button startButton;
    @FXML private Button stepButton;

    private boolean initialized = false;
    private void setInitialized(boolean value){initialized = value;}

    @FXML
    public void initialize(){
        BlockConectionHandling.setMainView(mainViewPane);
        addTab(null, 1);
        AnchorPane.setBottomAnchor(calculateButton,10d);
        AnchorPane.setBottomAnchor(startButton,10d);
        AnchorPane.setBottomAnchor(stepButton,10d);
        calculateButton.toFront();
        startButton.toFront();
        stepButton.toFront();
        setInitialized(true);
    }

    @FXML
    protected void createBlockAttack(MouseEvent e){
        BlockConectionHandling.createBlock("attack");
        //BlockAttackUI blockAttack = new BlockAttackUI(mainViewPane);
    }
    @FXML
    protected void createBlockDefense(MouseEvent e){
        BlockConectionHandling.createBlock("defense");
        //BlockDefenseUI blockDefense = new BlockDefenseUI(mainViewPane);
    }
    @FXML
    protected void createBlockHealing(MouseEvent e){
        BlockConectionHandling.createBlock("healing");
        //BlockHealingUI blockHealing = new BlockHealingUI(mainViewPane);
    }
    @FXML
    protected void createBlockTraining(MouseEvent e){
        BlockConectionHandling.createBlock("training");
        //BlockTrainingUI blockTraining = new BlockTrainingUI(mainViewPane);
    }
    @FXML
    protected void createBlockWeaponUpgrade(MouseEvent e){
        BlockConectionHandling.createBlock("upgrade");
        //BlockWeaponUpgradeUI blockWeaponUpgrade = new BlockWeaponUpgradeUI(mainViewPane);
    }
    @FXML
    protected void calculateScheme(){
        if(BlockConectionHandling.calculateScheme()){
            startButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        }else{
            startButton.setTextFill(Color.RED);
            stepButton.setTextFill(Color.RED);
        }
    }
    @FXML
    protected void runScheme(){
        if(BlockConectionHandling.runScheme()){
            startButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        }else{
            startButton.setTextFill(Color.RED);
            stepButton.setTextFill(Color.RED);
        }
    }
    @FXML
    protected void stepScheme(){
        if(BlockConectionHandling.stepScheme()) {
            startButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        }else{
            startButton.setTextFill(Color.RED);
            stepButton.setTextFill(Color.RED);
        }
    }
    @FXML
    protected void changeScheme(){
        if (initialized == false) {return;}
        int index = 0;
        if(tabPane.getSelectionModel().getSelectedItem().isSelected()){
            System.out.println("Selected tab: " + tabPane.getSelectionModel().getSelectedItem().getText());
            index = tabPane.getSelectionModel().getSelectedIndex();
            BlockConectionHandling.changeScheme(tabPane.getSelectionModel().getSelectedItem().getText());
        }
        else {return;}
        if(tabPane.getTabs().indexOf(addScheme) == index){
            index += 1;
            addTab(null, index);
        }
        calculateButton.toFront();
        startButton.toFront();
        stepButton.toFront();
    }

    private void addTab(String s, int index){
        if(s == "" || s == null){s = "newScheme";}
        Tab tab = new Tab(s + index);
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                changeScheme();
            }
        });
        BlockConectionHandling.addScheme(tab.getText());
        tabPane.getTabs().add(tab);
        tabPane.getTabs().remove(addScheme);
        tabPane.getTabs().add(addScheme);
    }
}
