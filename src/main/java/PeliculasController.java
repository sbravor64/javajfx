import control.Film;
import control.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PeliculasController implements Initializable {

    ObservableList<Sesion> listObservableFilms = FXCollections.observableArrayList();

    String tituloCiclo;



    @FXML
    private Text cicloTitle;

    @FXML
    private TableView<Sesion> tableViewPeliculas;

    @FXML
    private TableColumn<Sesion, String> tableColumnTitleFilm;

    @FXML
    private TableColumn<Sesion, String> tableColumnFecha;

    @FXML
    private TableColumn<Sesion, String> tableColumnTitleCine;

    @FXML
    private TableColumn<Sesion, String> tableColumnLocalidad;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void recibeInfoSesiones(String tituloCiclo, ObservableList<Sesion> listObservableFilms) {
        this.listObservableFilms = listObservableFilms;
        this.tituloCiclo = tituloCiclo;

        añadirPeliculas();
    }

    private void añadirPeliculas() {
        tableColumnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tableColumnTitleFilm.setCellValueFactory(new PropertyValueFactory("titulo"));
        tableColumnTitleCine.setCellValueFactory(new PropertyValueFactory("nomCine"));
        tableColumnLocalidad.setCellValueFactory(new PropertyValueFactory("localidad"));

        tableViewPeliculas.setItems(listObservableFilms);
        cicloTitle.setText(tituloCiclo);
    }

}
