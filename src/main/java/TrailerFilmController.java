import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;

public class TrailerFilmController implements Initializable {

    String trailer;

    @FXML
    private WebView webViewTrailer;

    @FXML
    private Text textNoVideo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textNoVideo.setVisible(false);
    }

    private void añadirUrl(){
        String urlTrailer = "https://www.youtube.com/embed/" + trailer;
        webViewTrailer.getEngine().load(urlTrailer);
    }

    public void recibeUrlTrailer(String trailer) {
        this.trailer = trailer;

        if(!trailer.equals("--")){
            añadirUrl();
        } else {
            textNoVideo.setVisible(true);
        }
    }
}
