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
import java.util.List;
import java.util.ResourceBundle;

public class SesionController implements Initializable {

    ObservableList<Sesion> listObservableSesions = FXCollections.observableArrayList();

    String tituloFilm;

    @FXML
    Text sesionTitle;

    @FXML
    private TableView<Sesion> tableViewSesiones;

    @FXML
    private TableColumn<Sesion, String> tableColumnTitleCinema;

    @FXML
    private TableColumn<Sesion, String> tableColumnSesion;

    @FXML
    private TableColumn<Sesion, String> tableColumnFecha;

    @FXML
    private TableColumn<Sesion, String> tableColumnLocalidad;

    @FXML
    private Circle btnCerrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    void añadirSesiones(){
        tableColumnTitleCinema.setCellValueFactory(new PropertyValueFactory("nomCine"));
        tableColumnSesion.setCellValueFactory(new PropertyValueFactory("sesionOrdre"));
        tableColumnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tableColumnLocalidad.setCellValueFactory(new PropertyValueFactory("localidad"));

        tableViewSesiones.setItems(listObservableSesions);
        sesionTitle.setText(tituloFilm);
    }

    public void recibeInfoSesiones(String tituloFilm, ObservableList<Sesion> listObservableSesions) {
        this.listObservableSesions = listObservableSesions;
        this.tituloFilm = tituloFilm;

        añadirSesiones();
    }

    public void handlerMouseEvent(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btnCerrar){
//            System.exit(0);
            Stage stage = (Stage) btnCerrar.getScene().getWindow();
//            tableViewSesiones.getItems().clear();
            stage.close();
        }
    }
}
