package ija.ija2017.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MainWindowController {


    @FXML private AnchorPane mainViewPane;
    @FXML private TabPane tabPane;
    @FXML private Tab addScheme;

    private boolean initialized = false;
    private void setInitialized(boolean value){initialized = value;}

    @FXML
    public void initialize(){
        BlockConectionHandling.setMainView(mainViewPane);
        addTab(null, 1);
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
