import control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HomeController implements Initializable {

    String tituloFilm;
    String tituloCiclo;

    ConexionXML conexionXML;
    List<String> images;
    String url = "http://www.gencat.cat/llengua/cinema/";

    List<Film> films;
    List<Sesion> sesions;
    List<Cinema> cinemas;
    List<Cicle> cicles;

    private double x=0, y=0;

    ObservableList<Sesion> listObservableSesionsEnvio =FXCollections.observableArrayList();
//    ObservableList<Film> listObservableFilmsEnvio = FXCollections.observableArrayList();

    ObservableList<String> listObservableFilms =FXCollections.observableArrayList();
    ObservableList<String> listObservableCicles =FXCollections.observableArrayList();


    // atributos de la pelicula
    @FXML
    private ListView<String> listViewFilms;
    @FXML
    private Text textTitleFilm;
    @FXML
    private ImageView imageFilm;
    @FXML
    private Text direcctorFilm;
    @FXML
    private Text directorTitle;
    @FXML
    private Text añoFilm;
    @FXML
    private Text añoTitle;
    @FXML
    private Text sinopsiTitle;
    @FXML
    private TextArea sinopsiFilm1;
    @FXML
    private TextField textFieldPelicula;
    @FXML
    private Button buttonBuscar;

    // atributos del ciclo
    @FXML
    private ListView<String> listViewCiclos;
    @FXML
    private Text textTitleCiclo;
    @FXML
    private Text infoCiclo;
    @FXML
    private ImageView imageCiclo;


    @FXML
    private TabPane tabPane;
    @FXML
    private Pane pane;

    //diagrama 1y2
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<?,?> barChart;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis categoryAxis;


    @FXML
    private Button buttonSesion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sinopsiFilm1.setWrapText(true);
            pane.setVisible(false);
            connectedXML();
            loadFilms();
            loadCiclos();
            opaqueInfo();
            rellenardiagrama1();
            rellenarDiagrama2();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    private void rellenardiagrama1(){
        ObservableList<PieChart.Data> dataCharts = FXCollections.observableArrayList();

        List<Integer> años = films.stream()
                .map(film -> film.getAny())
                .filter(i -> i > 0 && i < 3000).distinct()
                .sorted(Comparator.comparingInt(integer -> integer))
                .collect(Collectors.toList());

        for (Integer i: años) {
            long numResultat= films.stream()
                    .filter(film1 -> film1.getAny() == i)
                    .count();
            dataCharts.add(new PieChart.Data(i.toString(), numResultat));
        }

        pieChart.setData(dataCharts);
        pieChart.setLegendSide(Side.LEFT);

        final Label label = new Label();
        pane.getChildren().add(label);
        label.setFont(Font.font("SanSerif", FontWeight.BLACK, 20));

        pieChart.getData().stream().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.ANY, e->{
                int intValue = (int) data.getPieValue();
                pane.setVisible(true);
                if(intValue==1){
                    label.setText(intValue + " pelicula");
                }else {
                    label.setText(intValue + " peliculas");
                }
            });
        });
    }

    private void rellenarDiagrama2(){
        XYChart.Series dataCharts = new XYChart.Series<>();

        List<String> provincias = cinemas.stream()
                .filter(cinema -> !cinema.getProvincia().equals("--"))
                .map(cinema -> cinema.getProvincia())
                .collect(Collectors.toList());

        provincias.forEach(pro -> {
            long numResultat= cinemas.stream().filter(cinema -> cinema.getProvincia().equals(pro)).count();
            dataCharts.getData().add(new XYChart.Data(pro, numResultat));
        });
        barChart.getData().add(dataCharts);
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

    private void loadFilms() {

        List<String> listaTitle = films.stream().map(film -> film.getTitol()).collect(Collectors.toList());
        images = films.stream().map(film -> film.getImage()).collect(Collectors.toList());

        listObservableFilms.addAll(listaTitle);
        listViewFilms.getItems().addAll(listObservableFilms);
    }

    private void loadCiclos(){
        List<String> listaTitle = cicles.stream().map(sesion -> sesion.getNombre()).collect(Collectors.toList());
        listObservableCicles.addAll(listaTitle);
        listViewCiclos.getItems().addAll(listObservableCicles);
    }

    public void displaySelected(javafx.scene.input.MouseEvent mouseEvent) {

        if(mouseEvent.getSource() == listViewFilms){
            String filmTitle = listViewFilms.getSelectionModel().getSelectedItem();
            List<Sesion> listaSesionesFilm;
            if(filmTitle==null|| filmTitle.isEmpty()){
                textTitleFilm.setText("No has seleccionado ninguna pelicula");
            } else {
                visibleInfoMovie();
                textTitleFilm.setText(filmTitle);
                for (Film f: films) {
                    if(f.getTitol().equals(filmTitle)){
                        imageFilm.setImage(new Image(url+f.getImage()));
                        direcctorFilm.setText(f.getDireccio());
                        añoFilm.setText(String.valueOf(f.getAny()));
                        sinopsiFilm1.setText(f.getSinopsi());

                        listObservableSesionsEnvio.clear();

                        //atributos que envio a la nueva ventana (sesiones)
                        tituloFilm = f.getTitol();
                        listaSesionesFilm = sesions.stream().filter(sesion -> f.getIdFilm() == sesion.getIdFilm()).collect(Collectors.toList());
                        listObservableSesionsEnvio.addAll(listaSesionesFilm);

                        listaSesionesFilm.clear();
                    }
                }
            }
        } else if(mouseEvent.getSource() == listViewCiclos){
            textTitleCiclo.setVisible(true);
            infoCiclo.setVisible(true);
            String cicleTitle = listViewCiclos.getSelectionModel().getSelectedItem();
            textTitleCiclo.setText(cicleTitle);

            List<Sesion> listaFilmsCicle = new ArrayList<>();

            for (Cicle c: cicles) {
                if(c.getNombre().equals(cicleTitle)){
                    infoCiclo.setText(c.getInfo());
                    imageCiclo.setImage(new Image(url+c.getImg()));

                    listObservableSesionsEnvio.clear();

                    tituloCiclo = c.getNombre();

                    sesions.forEach(sesion -> {
                        if(sesion.getCicleID()==c.getIdCiclo()){
                            films.forEach(film -> {
                                if(film.getIdFilm() == sesion.getIdFilm()){
                                    listaFilmsCicle.add(sesion);
                                }
                            });
                        }
                    });

                    listaFilmsCicle.forEach(System.out::println);

                    listObservableSesionsEnvio.addAll(listaFilmsCicle);
                    listaFilmsCicle.clear();

                }
            }
        }
    }

    public void PageSesion(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        Parent root = loader.load(getClass().getResource("sesion.fxml").openStream());

        SesionController sesionController = loader.getController();
        sesionController.recibeInfoSesiones(tituloFilm, listObservableSesionsEnvio);

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public void PageFilmsCiclo(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        Parent root = loader.load(getClass().getResource("peliculas.fxml").openStream());

        PeliculasController peliculasController = loader.getController();
        peliculasController.recibeInfoSesiones(tituloCiclo, listObservableSesionsEnvio);

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public void visibleInfoMovie(){
        textTitleFilm.setVisible(true);
        direcctorFilm.setVisible(true);
        directorTitle.setVisible(true);
        añoFilm.setVisible(true);
        añoTitle.setVisible(true);
        sinopsiFilm1.setVisible(true);
        sinopsiTitle.setVisible(true);

    }

    public void opaqueInfo(){
        textTitleFilm.setVisible(false);
        direcctorFilm.setVisible(false);
        directorTitle.setVisible(false);
        añoFilm.setVisible(false);
        añoTitle.setVisible(false);
        textTitleCiclo.setVisible(false);
        infoCiclo.setVisible(false);
        sinopsiTitle.setVisible(false);
        sinopsiFilm1.setVisible(false);
    }

    public void buscador(MouseEvent mouseEvent) {
        listObservableFilms.clear();
        String titulo = textFieldPelicula.getText();

        List<String> listaTitle = films.stream().filter(film -> film.getTitol().toLowerCase().contains(titulo)).map(film -> film.getTitol()).collect(Collectors.toList());

        listObservableFilms.addAll(listaTitle);
        listViewFilms.getItems().clear();
        listViewFilms.getItems().addAll(listObservableFilms);

    }
}
