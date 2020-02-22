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

    ObservableList<Film> listObservableFilms = FXCollections.observableArrayList();

    String tituloCiclo;

    @FXML
    private Circle btnCerrar;

    @FXML
    private Text cicloTitle;

    @FXML
    private TableView<Film> tableViewPeliculas;

    @FXML
    private TableColumn<Film, String> tableColumnTitleFilm;

    @FXML
    private TableColumn<Film, String> tableColumnDirectorFilm;

    @FXML
    private TableColumn<Film, String> tableColumnEstrenoFilm;

    @FXML
    private TableColumn<Film, String> tableColumnIdiomaFilm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void recibeInfoSesiones(String tituloCiclo, ObservableList<Film> listObservableFilms) {
        this.listObservableFilms = listObservableFilms;
        this.tituloCiclo = tituloCiclo;

        añadirPeliculas();
    }

    private void añadirPeliculas() {
        tableColumnDirectorFilm.setCellValueFactory(new PropertyValueFactory("direccio"));
        tableColumnTitleFilm.setCellValueFactory(new PropertyValueFactory("titol"));
        tableColumnIdiomaFilm.setCellValueFactory(new PropertyValueFactory("idioma"));
        tableColumnEstrenoFilm.setCellValueFactory(new PropertyValueFactory("any"));

        tableViewPeliculas.setItems(listObservableFilms);
        cicloTitle.setText(tituloCiclo);
    }

    public void handlerMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnCerrar) {
//            System.exit(0);
            Stage stage = (Stage) btnCerrar.getScene().getWindow();
//            tableViewSesiones.getItems().clear();
            stage.close();
        }
    }
}
