package FXJava.Buscaminas;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ventana extends Application {

    Juego partida;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String[] dificultades = {"Fácil", "Intermedio", "Difícil", "Personalizado"};
        Font font = new Font(20);

        stage.setTitle("Buscaminas");


        GridPane menuGrid = new GridPane();
        menuGrid.setPadding(new Insets(10,10,10,10));
        menuGrid.setVgap(10);
        menuGrid.setHgap(10);
        menuGrid.setAlignment(Pos.CENTER);

        Label dificultadLabel = new Label("Dificultad: ");
        dificultadLabel.setFont(font);
        menuGrid.add(dificultadLabel, 0, 0);

        ChoiceBox<String> dificultadesChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(dificultades));
        dificultadesChoiceBox.setValue(dificultades[0]);
        dificultadesChoiceBox.setMaxSize(110, 30);
        dificultadesChoiceBox.setMinSize(110, 30);
        menuGrid.add(dificultadesChoiceBox, 1, 0,3,1);

        Button aceptarButton = new Button("Aceptar");
        aceptarButton.setFont(new Font(18));
        menuGrid.add(aceptarButton, 0, 3, 5 ,1);
        GridPane.setHalignment(aceptarButton, HPos.CENTER);

        Label tamañoLabel = new Label("Tamaño: ");
        tamañoLabel.setFont(font);

        TextField tamañoHTextField = new TextField();
        tamañoHTextField.setPromptText("H");
        tamañoHTextField.setPrefWidth(40);

        Label xLabel = new Label("x");
        xLabel.setFont(font);

        TextField tamañoVTextField = new TextField();
        tamañoVTextField.setPromptText("V");
        tamañoVTextField.setPrefWidth(40);

        Label nroMinasLabel = new Label("Nro de minas: ");
        nroMinasLabel.setFont(font);

        TextField nrominasTextField = new TextField();
        nrominasTextField.setPromptText("Nro minas");
        nrominasTextField.setPrefWidth(100);

        dificultadesChoiceBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.equals("Personalizado")) {
                menuGrid.add(tamañoLabel, 0, 1);
                menuGrid.add(tamañoHTextField, 1, 1);
                menuGrid.add(xLabel, 2, 1);
                menuGrid.add(tamañoVTextField, 3, 1);
                menuGrid.add(nroMinasLabel, 0, 2);
                menuGrid.add(nrominasTextField, 1, 2,3,1);
            } else if (oldValue.equals("Personalizado")) {
                menuGrid.getChildren().removeAll(tamañoLabel, tamañoHTextField, xLabel, tamañoVTextField, nroMinasLabel, nrominasTextField);
            }
        });

        aceptarButton.setOnAction(e -> {
            int alto;
            int ancho;
            int minas;
            switch (dificultadesChoiceBox.getValue()) {
                case "Fácil":

                    alto = 8;
                    ancho = 8;
                    minas = 10;
                    break;

                case "Intermedio":

                    alto = 16;
                    ancho = 16;
                    minas = 40;
                    break;

                case "Difícil":

                    alto = 16;
                    ancho = 30;
                    minas = 90;
                    break;

                default:

                    alto = Integer.parseInt(tamañoVTextField.getText());
                    ancho = Integer.parseInt(tamañoHTextField.getText());
                    minas = Integer.parseInt(nrominasTextField.getText());
                    break;
            }

            if (alto < 50 && ancho < 50 && minas < ancho * alto) {
                partida = new Juego(alto, ancho, minas);
                Tablero tablero = new Tablero();
                stage.setScene(tablero.displayTablero(partida));
            }
        });

        Scene menuScene = new Scene(menuGrid, 900, 800);
        stage.setScene(menuScene);
        stage.show();

    }
}
