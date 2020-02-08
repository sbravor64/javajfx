package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SampleController implements Initializable {
    ConexionXML conexionXML;
    List<String> images;
    String url = "http://www.gencat.cat/llengua/cinema/";

    List<Film> films;
    List<Sesion> sesions;
    List<Cinema> cinemas;
    List<Cicle> cicles;

    private double x=0, y=0;

    ObservableList<String> listObservable =FXCollections.observableArrayList();

    @FXML
    private ListView<String> listViewFilms;

    @FXML
    private Text textTitleFilm;

    @FXML
    private ImageView imageFilm;

    @FXML
    private Circle btnCerrar;

    @FXML
    private TabPane tabPane;

    @FXML
    private Text direcctorFilm;

    @FXML
    private Text directorTitle;

    @FXML
    private Text añoFilm;

    @FXML
    private Text añoTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connectedXML();
            loadFilms();
            makeDragable();
            opaqueInfoMovie();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        images = films.stream().map(film -> film.getImage()).collect(Collectors.toList());

        listObservable.addAll(listaTitle);
        listViewFilms.getItems().addAll(listObservable);
    }

    public void displaySelected(javafx.scene.input.MouseEvent mouseEvent) {
        String filmTitle = listViewFilms.getSelectionModel().getSelectedItem();
        String direccion;

        if(filmTitle==null|| filmTitle.isEmpty()){
            textTitleFilm.setText("No has seleccionado ninguna pelicula");
        } else {
            visibleInfoMovie();
            textTitleFilm.setText(filmTitle);
            for (Film f: films) {
                if(f.getTitol().equals(filmTitle)){
                    String urlImage=url+f.getImage();
                    Image imageMovie = new Image(urlImage);
                    imageFilm.setImage(imageMovie);
                    direcctorFilm.setText(f.getDireccio());
                    añoFilm.setText(String.valueOf(f.getAny()));
                }
            }
        }
    }

    public void handlerMouseEvent(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btnCerrar){
//            System.exit(0);
            Stage stage = (Stage) btnCerrar.getScene().getWindow();
            stage.close();
        }
    }

    public void makeDragable(){
        tabPane.setOnMousePressed((event -> {
            x=event.getSceneX();
            y=event.getSceneY();
        }));

        tabPane.setOnMouseDragged((event -> {
            Stage stage = (Stage) btnCerrar.getScene().getWindow();
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);
        }));
    }

    public void visibleInfoMovie(){
        textTitleFilm.setVisible(true);
        direcctorFilm.setVisible(true);
        directorTitle.setVisible(true);
        añoFilm.setVisible(true);
        añoTitle.setVisible(true);
    }

    public void opaqueInfoMovie(){
        textTitleFilm.setVisible(false);
        direcctorFilm.setVisible(false);
        directorTitle.setVisible(false);
        añoFilm.setVisible(false);
        añoTitle.setVisible(false);
    }
}
