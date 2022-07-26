package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private String accessKey = "9cf202df7c52030fcbe351d02d9a1834";

        @FXML
        public DatePicker dpDate;
        @FXML
        private TextField airlines;
        @FXML
        private TextField flightNumbers;
        @FXML
        private Text depAirport;
        @FXML
        private Text arrAirport;
        @FXML
        private TextArea depDatas;
        @FXML
        private TextArea arrDatas;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Callback<DatePicker, DateCell> blockedDates = dp -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                LocalDate today = LocalDate.now();
                setDisable(empty || item.isAfter(today) || item.isBefore(today));
            }

        };

        dpDate.setDayCellFactory(blockedDates);
    }

    @FXML
    public void getAirlineData(ActionEvent event) throws MalformedURLException, ParseException {
        StringBuilder strBuild = new StringBuilder(airlines.getText());
        String flightNumberData = flightNumbers.getText();
        if(strBuild.toString().contains(" ")){
            int spaceFiller = strBuild.lastIndexOf( " ");
            strBuild.replace(spaceFiller, spaceFiller+1, "+");
        }
        System.out.println(strBuild);

        String newString = String.format("?airline_name=%s&flight_number=%s&access_key=%s", strBuild, flightNumberData, accessKey);

        getCurrentInfo(newString);


    }

    private void getCurrentInfo(String url) throws MalformedURLException, ParseException {
        APIConnector apiConnector = new APIConnector("http://api.aviationstack.com/v1/flights"+url);
        JSONObject jsonObject = apiConnector.getJSONArray();
        System.out.println(jsonObject);
        String departureString = jsonObject.get("departure").toString();
        String arrivalString = jsonObject.get("arrival").toString();
        String liveDataString = jsonObject.get("live").toString();
        System.out.println(liveDataString);
        departureField(departureString);
        arrivalField(arrivalString);
        liveData(liveDataString);
    }

    private void arrivalField(String arrivalString) throws ParseException {
        String newArrival = "[" + arrivalString + "]";
        JSONParser parse = new JSONParser();
        JSONArray dataObject = (JSONArray) parse.parse(newArrival);
        JSONObject departureData = (JSONObject) dataObject.get(0);
        arrAirport.setText(departureData.get("airport").toString());
        String timezone = departureData.get("timezone").toString();
        String scheduled = departureData.get("scheduled").toString();
        String estimated = departureData.get("estimated").toString();
        String gate = departureData.get("gate").toString();
        String terminal = departureData.get("terminal").toString();
        String iata = departureData.get("iata").toString();
        String icao = departureData.get("icao").toString();
        arrDatas.setText("\n\n\n\n\nScheduled: " + scheduled +
                "\nEstimated: " + estimated +
                "\nGate: " + gate +
                "\nTerminal:" + terminal);

    }


    private void departureField(String departureString) throws ParseException {
        String newDeparture = "[" + departureString + "]";
        JSONParser parse = new JSONParser();
        JSONArray dataObject = (JSONArray) parse.parse(newDeparture);
        JSONObject departureData = (JSONObject) dataObject.get(0);
        depAirport.setText(departureData.get("airport").toString());
        String timezone = departureData.get("timezone").toString();
        String scheduled = departureData.get("scheduled").toString();
        String estimated = departureData.get("estimated").toString();
        String gate = departureData.get("gate").toString();
        String terminal = departureData.get("terminal").toString();
        String iata = departureData.get("iata").toString();
        String icao = departureData.get("icao").toString();
        depDatas.setText("\n\n\n\n\nScheduled: " + scheduled +
                "\nEstimated: " + estimated +
                "\nGate: " + gate +
                "\nTerminal:" + terminal);

    }

    private void liveData(String liveData) throws ParseException {
        String newLiveData = "[" + liveData + "]";
        //System.out.println(newLiveData);
        JSONParser parse = new JSONParser();
        JSONArray dataObject = (JSONArray) parse.parse(newLiveData);
        //System.out.println(dataObject);
        JSONObject liveFlightData = (JSONObject) dataObject.get(0);
        String lat = liveFlightData.get("latitude").toString();
        String longe = liveFlightData.get("longitude").toString();
        String alti = liveFlightData.get("altitude").toString();
        String speed = liveFlightData.get("speed_horizontal").toString();
        try {
            Writer fileWriter = new FileWriter("src/resources/HTML/googleMapEmbed.html",false);
            fileWriter.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>HTML Google Map HTML Embed</title>\n" +
                    "</head>\n" +
                    "<body style=\"background-color:black\">\n" +
                    "    <div style=\"position:relative; height=250px; weight:450pxs;\">\n" +
                    "        <iframe\n" +
                    "            width=\"450\"\n" +
                    "            height=\"250\"\n" +
                    "            frameborder=\"0\" style=\"border:0\"\n" +
                    "            referrerpolicy=\"no-referrer-when-downgrade\"\n" +
                    "            src=\"https://www.google.com/maps/embed/v1/view?key=AIzaSyDqkeHwRwVib_iIGz1x8-qBLDe_PHeDvSM&center="+ lat + "," + longe + "zoom=12&maptype=satellite\"\n" +
                    "            allowfullscreen>\n" +
                    "        </iframe>\n" +
                    "        <div style=\"position:absolute; z-index:1; left:225px; margin-right:-50%; transform:translate(-50%,-50%); top:125px;\">\n" +
                    "            <img style=\"height:40px;width:40px;border-style:solid;border-width:2px;border-color:white;border-radius:10px;background:rgba(0,0,0,.5)\" src=\"https://upload.wikimedia.org/wikipedia/commons/1/17/Airplane_silhouette_orange.svg\">\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
            fileWriter.close();
        } catch (IOException e) {
        }

    }

}



