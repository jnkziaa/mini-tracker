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
                dataObject = (JSONArray) parse.parse(actualData);


                JSONObject aviationData = (JSONObject) dataObject.get(0);




                return aviationData;

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJSONObject(String query){
        try {
            URL url = new URL(urlString + query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();

                return (JSONObject) parse.parse(String.valueOf(informationString));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getFullApi(){

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
                JSONArray dataObject = (JSONArray) parse.parse(actualData);


                JSONObject aviationData = (JSONObject) dataObject.get(0);

                for(int i = 0; i < 100; i++){
                    //Loops through the API and stores the flight# and airline name in key/value pairs.
                    getAPI = (JSONObject) dataObject.get(i);
                    getAirlinName = (JSONObject) getAPI.get("airline");
                    getFlightNum = (JSONObject) getAPI.get("flight");
                    getAirlineList.put(getFlightNum.get("number").toString(), getAirlinName.get("name").toString());
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("In fullAPI method!");
        System.out.println(getAirlineList);
    }

    public void getAirline(TextField airline, TextField flight){
        String flights = flight.getText();
        String namesTest;
        String flightNum;

        ArrayList<String> names = new ArrayList<>();

        if(flights.isEmpty()) {
            for (Object o : getAirlineList.values()) {
                if (!names.contains(o.toString()))
                    names.add(o.toString());
            }
        }else{
            names.clear();
            for(Map.Entry<String, String> entry : getAirlineList.entrySet()){
                namesTest = entry.getValue().toString();
                flightNum = entry.getKey().toString();
                System.out.println(namesTest);
                if(flightNum.equals(flights)){
                    names.clear();
                    names.add(entry.getValue());
                    break;
                }else if(flightNum.contains(flights)){
                    names.add(entry.getValue());
                }
            }
        }
        TextFields.bindAutoCompletion(airline,names);

    }

    public void getFlights(TextField airline, TextField flight){

        String flights = flight.getText();
        String airlines = airline.getText();
        String namesTest;
        String flightNum;

        ArrayList<String> nums = new ArrayList<>();

        if(airlines.isEmpty()) {
            for (Object o : getAirlineList.keySet()) {
                if (!nums.contains(o.toString()))
                    nums.add(o.toString());
            }

        }else{
            nums.clear();
            for(Map.Entry<String, String> entry : getAirlineList.entrySet()){
                namesTest = entry.getValue().toString();
                flightNum = entry.getKey().toString();

                if(namesTest.equals(airlines)){
                    nums.clear();
                    nums.add(entry.getKey());
                    break;
                }else if(namesTest.contains(airlines)){
                    nums.clear();
                    nums.add(entry.getKey());
                }
            }
        }
        TextFields.bindAutoCompletion(flight,nums);
    }


}