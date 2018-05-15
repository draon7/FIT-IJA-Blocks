package ija.ija2017.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application{
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Class start app and GUI
     * @param stage parameters of window
     * @throws Exception error
     */
    @Override
    public void start(Stage stage) throws  Exception{
        stage.setTitle("BlockyView");
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));

        Scene scene = new Scene(root, 500, 500);

       stage.setMinHeight(500);
       stage.setMinWidth(1000);

        stage.setScene(scene);
        stage.show();
    }
}
