package ija.ija2017.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MainWindowController {

    public MainWindowController(){
        BlockConectionHandling.setMainView(mainViewPane);
    }

    @FXML private AnchorPane mainViewPane;
    @FXML private TabPane tabPane;
    @FXML private Tab addScheme;

    @FXML
    protected void createBlockAttack(MouseEvent e){
        BlockAttackUI blockAttack = new BlockAttackUI(mainViewPane);
    }
    @FXML
    protected void createBlockDefense(MouseEvent e){
        BlockDefenseUI blockDefense = new BlockDefenseUI(mainViewPane);
    }
    @FXML
    protected void createBlockHealing(MouseEvent e){
        BlockHealingUI blockHealing = new BlockHealingUI(mainViewPane);
    }
    @FXML
    protected void createBlockTraining(MouseEvent e){
        BlockTrainingUI blockTraining = new BlockTrainingUI(mainViewPane);
    }
    @FXML
    protected void createBlockWeaponUpgrade(MouseEvent e){
        BlockWeaponUpgradeUI blockWeaponUpgrade = new BlockWeaponUpgradeUI(mainViewPane);
    }
    @FXML
    protected void changeScheme(){
        int index = 0;
        if(tabPane.getSelectionModel().getSelectedItem().isSelected()){
            index = tabPane.getSelectionModel().getSelectedIndex();
            BlockConectionHandling.changeScheme(tabPane.getSelectionModel().getSelectedItem().getText());
        }
        if(tabPane.getTabs().indexOf(addScheme) == index){
            index += 1;
            Tab tab = new Tab("newScheme" + index);
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    changeScheme();
                }
            });
            tabPane.getTabs().add(tab);
            tabPane.getTabs().remove(addScheme);
            tabPane.getTabs().add(addScheme);
            BlockConectionHandling.addScheme(tab.getText());
        }
    }
}
