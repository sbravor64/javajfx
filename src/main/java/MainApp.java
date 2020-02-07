import control.ConexionXML;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, JAXBException {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("BIBLIOTECA FILMS");
        Scene scene = new Scene(root, 800,500);
        stage.setScene(scene);
        stage.show();
    }
}
