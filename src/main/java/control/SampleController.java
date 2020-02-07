package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import javax.xml.bind.JAXBException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SampleController implements Initializable {
    ConexionXML conexionXML;

    List<Film> films;
    List<Sesion> sesions;
    List<Cinema> cinemas;
    List<Cicle> cicles;

    ObservableList<String> listObservable =FXCollections.observableArrayList();


    @FXML
    private ListView<String> listViewFilms;

    @FXML
    private Text textTitleFilm;

//    @FXML
//    private ImageView imageFilm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connectedXML();
            loadFilms();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        buttonVerFilm.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Label secondLabel = new Label("I'm a Label on new Window");
//
//                StackPane secondaryLayout = new StackPane();
//                secondaryLayout.getChildren().add(secondLabel);
//
//                Scene secondScene = new Scene(secondaryLayout, 300, 200);
//
//                // New window (Stage)
//                Stage newWindow = new Stage();
//                newWindow.setTitle("Second Stage");
//                newWindow.setScene(secondScene);
//
//                newWindow.show();
//            }
//        });
    }

    private void connectedXML() throws JAXBException, IOException {
        conexionXML = new ConexionXML();
        conexionXML.connectedFilms();
        conexionXML.connectedSessions();
        conexionXML.connectedCinema();
        conexionXML.connectedCicles();

        sesions = conexionXML.getSesions();
        cinemas = conexionXML.getCinemas();
        films = conexionXML.getFilms();
        cicles = conexionXML.getCicles();
    }

    private void loadFilms() throws JAXBException, IOException {
        conexionXML = new ConexionXML();
        conexionXML.connectedFilms();

        films = conexionXML.getFilms();

        List<String> listaTitle = films.stream().map(film -> film.getTitol()).collect(Collectors.toList());

        listObservable.addAll(listaTitle);
        listViewFilms.getItems().addAll(listObservable);
    }

    public void displaySelected(javafx.scene.input.MouseEvent mouseEvent) {
        String film = listViewFilms.getSelectionModel().getSelectedItem();
        if(film==null|| film.isEmpty()){
            textTitleFilm.setText("No has seleccionado ninguna pelicula");
        } else {
            textTitleFilm.setText(film);
        }
    }
}
