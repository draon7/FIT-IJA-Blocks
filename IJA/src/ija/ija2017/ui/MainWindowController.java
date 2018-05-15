package ija.ija2017.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Class with main window controllers
 */
public class MainWindowController {


    @FXML private AnchorPane mainViewPane;
    @FXML private TabPane tabPane;
    @FXML private Tab addScheme;
    @FXML private Button calculateButton;
    @FXML private Button startButton;
    @FXML private Button stepButton;
    @FXML private Button deleteSchemeButton;
    @FXML private MenuItem saveButton;
    @FXML private MenuItem readButton;
    @FXML private HBox menuBox;

    Alert alert = new Alert(Alert.AlertType.ERROR);

    private boolean initialized = false;
    private void setInitialized(boolean value){initialized = value;}

    @FXML
    /**
     * Initialize window
     */
    public void initialize(){
        BlockConnectionHandling.initialize(mainViewPane);
        addTab(null, 1);
        AnchorPane.setBottomAnchor(calculateButton,10d);
        AnchorPane.setBottomAnchor(startButton,10d);
        AnchorPane.setBottomAnchor(stepButton,10d);
        AnchorPane.setTopAnchor(deleteSchemeButton,10d);
        AnchorPane.setRightAnchor(deleteSchemeButton,10d);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        calculateButton.toFront();
        startButton.toFront();
        stepButton.toFront();
        deleteSchemeButton.toFront();
        setInitialized(true);
        alert.setTitle("Invalid scheme");
        alert.setHeaderText("There is mistake in your scheme!");
    }

    /**
     * Method saves scheme
     */
    @FXML
    protected void saveScheme(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Blocky Files", "*.blockies"));
            File selectedFile = fileChooser.showSaveDialog(mainViewPane.getScene().getWindow());
            if (selectedFile != null) {
                BlockConnectionHandling.saveScheme(selectedFile);
                tabPane.getSelectionModel().getSelectedItem().setText(selectedFile.getName().replace(".blockies", ""));
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Method reads saved scheme
     */
    @FXML
    protected void readScheme(){
        try{FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Blocky Files", "*.blockies"));
            File selectedFile = fileChooser.showOpenDialog(mainViewPane.getScene().getWindow());
            if (selectedFile != null) {
                //tabPane.getSelectionModel().select(addScheme);
                addTab(selectedFile.getName().replace(".blockies", ""));
                BlockConnectionHandling.readScheme(selectedFile);
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Method on click Block Attack, creates block
     * @param e mouse event
     */
    @FXML
    protected void createBlockAttack(MouseEvent e){
        BlockConnectionHandling.createBlock("attack");
        //BlockAttackUI blockAttack = new BlockAttackUI(mainViewPane);
    }

    /**
     * Method on click Block Defense, creates block
     * @param e mouse event
     */
    @FXML
    protected void createBlockDefense(MouseEvent e){
        BlockConnectionHandling.createBlock("defense");
        //BlockDefenseUI blockDefense = new BlockDefenseUI(mainViewPane);
    }

    /**
     * Method on click Block Healing, creates block
     * @param e mouse event
     */
    @FXML
    protected void createBlockHealing(MouseEvent e){
        BlockConnectionHandling.createBlock("healing");
        //BlockHealingUI blockHealing = new BlockHealingUI(mainViewPane);
    }

    /**
     * Method on click Block Training, creates block
     * @param e mouse event
     */
    @FXML
    protected void createBlockTraining(MouseEvent e){
        BlockConnectionHandling.createBlock("training");
        //BlockTrainingUI blockTraining = new BlockTrainingUI(mainViewPane);
    }

    /**
     * Method on click Block Weapon Upgrade, creates block
     * @param e mouse event
     */
    @FXML
    protected void createBlockWeaponUpgrade(MouseEvent e){
        BlockConnectionHandling.createBlock("upgrade");
        //BlockWeaponUpgradeUI blockWeaponUpgrade = new BlockWeaponUpgradeUI(mainViewPane);
    }

    /**
     * Method on click calculate scheme, calculates order of scheme
     */
    @FXML
    protected void calculateScheme(){
        if(BlockConnectionHandling.calculateScheme()){
            startButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            startButton.setDisable(false);
            stepButton.setDisable(false);
        }else{
            startButton.setTextFill(Color.RED);
            stepButton.setTextFill(Color.RED);
            startButton.setDisable(true);
            stepButton.setDisable(true);
            alert.showAndWait();
        }
    }

    /**
     * Method on click check, calculates whole result
     */
    @FXML
    protected void runScheme(){
        if(BlockConnectionHandling.runScheme()){
            startButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        }else{
            startButton.setTextFill(Color.RED);
            stepButton.setTextFill(Color.RED);
            startButton.setDisable(true);
            stepButton.setDisable(true);
            alert.showAndWait();
        }
    }

    /**
     * Method on click step, calculates one step
     */
    @FXML
    protected void stepScheme(){
        if(BlockConnectionHandling.stepScheme()) {
            startButton.setTextFill(Color.color(0.15,0.15,0.15,1));
            stepButton.setTextFill(Color.color(0.15,0.15,0.15,1));
        }else{
            startButton.setTextFill(Color.RED);
            stepButton.setTextFill(Color.RED);
            startButton.setDisable(true);
            stepButton.setDisable(true);
            alert.showAndWait();
        }
    }
    /**
     * Method to select scheme from tabs
     */
    int tabNameIndex = 1;
    @FXML
    protected void changeScheme(){
        if (initialized == false) {return;}
        int index = 0;
        if(tabPane.getSelectionModel().getSelectedItem().isSelected()){
            index = tabPane.getSelectionModel().getSelectedIndex();
            BlockConnectionHandling.changeScheme(tabPane.getSelectionModel().getSelectedItem().getText());
        }
        else {return;}
        if(tabPane.getTabs().indexOf(addScheme) == index){
            /*index += 1;
            addTab(null, index);*/
            tabNameIndex++;
            addTab(null, tabNameIndex);
        }
        calculateButton.toFront();
        startButton.toFront();
        stepButton.toFront();
        deleteSchemeButton.toFront();
    }

    /**
     * Method to delete scheme
     */
    @FXML
    protected void deleteScheme(){
        //BlockConnectionHandling.deleteScheme(tabPane.getSelectionModel().getSelectedItem().getText());
        BlockConnectionHandling.deleteScheme();
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        tabPane.getTabs().remove(tabIndex);

        //Will select addTab - adds a new indexed tab
        if(tabIndex == 0){tabPane.getSelectionModel().select(0);}
        else{tabPane.getSelectionModel().select(tabIndex-1);}
        BlockConnectionHandling.changeScheme(tabPane.getSelectionModel().getSelectedItem().getText());

        calculateButton.toFront();
        startButton.toFront();
        stepButton.toFront();
        deleteSchemeButton.toFront();
    }

    /**
     * method to add tab to tab list
     * @param s name of tab item
     * @param index index to add to end of string
     */
    private void addTab(String s, int index){
        if(s == null || s.equals("")){s = "newScheme";}
        Tab tab = new Tab(s + index);
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                changeScheme();
            }
        });
        BlockConnectionHandling.addScheme(tab.getText());
        tabPane.getTabs().add(tabPane.getTabs().size()-1, tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size()-2);//Starts with 0 -> -1 - 1
    }

    /**
     * add Tab to end of tab list
     * @param s name of tab
     */
    private void addTab(String s){
        if(s == "" || s == null){s = "newScheme";}
        Tab tab = new Tab(s);
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                changeScheme();
            }
        });
        BlockConnectionHandling.addScheme(tab.getText());
        tabPane.getTabs().add(tabPane.getTabs().size()-1 ,tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size()-2);//Starts with 0 -> -1 - 1
    }

    /**
     * Method renames scheme
     */
    @FXML
    protected void renameScheme(){
        TextInputDialog dialog = new TextInputDialog("new name");
        dialog.setTitle("rename");
        dialog.setContentText("enter new name");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
            String newName = result.get();
            BlockConnectionHandling.renameScheme(newName);
            tabPane.getSelectionModel().getSelectedItem().setText(newName);
        }
    }

    /**
     * Method adds new scheme
     */
    @FXML
    protected void addNew(){
        tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
    }
}
