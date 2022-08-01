package Controller;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class APIConnector {

    private final String urlString;
    private final String regex = ".*\": null(,)?\\r\\n";
    private String removeNull = "";
    private JSONObject getAPI;
    private JSONObject getAirlinName;
    private JSONObject getFlightNum;
    private JSONArray dataObject;
    private String names[];
    private HashMap<String, String> getAirlineList = new HashMap<>();


    public APIConnector(String urlString) throws MalformedURLException {
        this.urlString = urlString;
    }

    public JSONObject getJSONArray(){
        HashMap<String, String> getAirlineList = new HashMap<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(conn + "?");
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode + "?");

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    removeNull = scanner.nextLine();
                    informationString.append(removeNull.replaceAll("null", "0"));

                }
               // System.out.println(informationString);
                scanner.close();

                String actualData = informationString.substring(informationString.indexOf("["), informationString.lastIndexOf("]")+1);
                JSONParser parse = new JSONParser();

}