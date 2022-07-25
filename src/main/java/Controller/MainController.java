package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

public class MainController extends DatePicker {

    @FXML
    public DatePicker datePicker;


    @FXML
    public void getDate(ActionEvent event){
        LocalDate date = datePicker.getValue();
        System.out.println(date);
    }


}