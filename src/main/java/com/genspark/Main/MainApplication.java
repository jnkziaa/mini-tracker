package com.genspark.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/second.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle("Airline Tracker v0.1");
        stage.setScene(scene);
        stage.show();


    }


    //HELLO JOHN FROM MARTIN

    public static void main(String[] args) {
        launch();
    }
}