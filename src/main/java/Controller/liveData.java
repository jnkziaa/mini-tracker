package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class liveData implements Initializable {
     @FXML
     private WebView webView;

     @FXML
     private WebEngine webEngine;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        loadPage();
    }
    //URL url2 = liveData.class.getResource("/View/liveData.fxml");
      //  webEngine.load(url2.toString());

    public void loadPage() {

        File file = new File("googleMapEmbed.html");
        webEngine.load(file.toURI().toString());

    }
}
