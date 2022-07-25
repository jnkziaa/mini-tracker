package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public DatePicker dpDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Callback<DatePicker, DateCell> blockedDates = dp -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                LocalDate today = LocalDate.now();
                setDisable(empty || item.compareTo(today) < -3);
            }

        };

        dpDate.setDayCellFactory(blockedDates);
    }

}



