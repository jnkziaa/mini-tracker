package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public DatePicker dpDate;
    @FXML
    private TextField airlines;
    @FXML
    private TextField flightNumbers;
    private String accessKey = "167215d3e8acedeb1c16141680a33e74";


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

    @FXML
    public void getAirlineData(ActionEvent event) throws MalformedURLException {
        StringBuilder strBuild = new StringBuilder(airlines.getText());
        String flightNumberData = flightNumbers.getText();
        if(strBuild.toString().contains(" ")){
            int spaceFiller = strBuild.lastIndexOf( " ");
            strBuild.deleteCharAt(spaceFiller);
            strBuild.insert(spaceFiller, "+");
        }

        String newString = String.format("?airline_name=%s&flight_number=%s&access_key=%s", strBuild.toString(), flightNumberData, accessKey);

        APIConnector apiConnector = new APIConnector("http://api.aviationstack.com/v1/flights"+newString);
        apiConnector.getJSONArray();

    }



}



