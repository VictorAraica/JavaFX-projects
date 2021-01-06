package FXJava.SistemaEcuaciones;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
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

public class menu extends Application {

    String[] possibleNumberOfVariables = {"1","2","3","4","5","6","7","8","9","10"};
    String[] nombresVariables = {"X", "Y", "Z", "K", "R", "T", "G", "Q", "P", "L", "A"};
    double[][] matriz;
    Button calcularButton;
    TextField[][] inputs;
    int nroVariables;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Siestema de Ecuaciones");

        // Menu scene
        GridPane gridMenu = new GridPane();
        gridMenu.setPadding(new Insets(10,10,10,10));
        gridMenu.setVgap(10);
        gridMenu.setHgap(10);
        gridMenu.setAlignment(Pos.CENTER);

        Label labelVariables = new Label("Nro de variables:");
        labelVariables.setFont(new Font(25));
        gridMenu.add(labelVariables, 0, 0);

        ChoiceBox<String> choiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(possibleNumberOfVariables));
        choiceBox.setValue("2");
        choiceBox.setMaxSize(50, 30);
        choiceBox.setMinSize(50, 30);
        gridMenu.add(choiceBox, 1, 0);

        Button aceptarButton = new Button("Aceptar");
        gridMenu.add(aceptarButton, 0, 1, 2 ,1);
        GridPane.setHalignment(aceptarButton, HPos.CENTER);

        aceptarButton.setOnAction(e -> {
            nroVariables = Integer.parseInt(choiceBox.getValue());
            stage.setScene(displayVariablesScene(nroVariables));
        });

        Scene menuScene = new Scene(gridMenu, 800, 700);
        stage.setScene(menuScene);
        stage.show();
    }

    public Scene displayVariablesScene(int numeroDeVariables) {
        inputs = new TextField[numeroDeVariables][numeroDeVariables + 1];
        matriz = new double[numeroDeVariables][numeroDeVariables + 1];

        GridPane variablesGrid = new GridPane();
        variablesGrid.setPadding(new Insets(10,10,10,10));
        variablesGrid.setVgap(20);
        variablesGrid.setHgap(15);
        variablesGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < numeroDeVariables; i++) {
            for (int j = 0; j <= numeroDeVariables; j++) {

                inputs[i][j] = new TextField();
                inputs[i][j].setPrefColumnCount(3);
                inputs[i][j].setPromptText(nombresVariables[j]);

                int finalI = i;
                int finalJ = j;
                inputs[i][j].textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("([-])?\\d{0,7}([\\.]\\d{0,4})?")) {
                            inputs[finalI][finalJ].setText(oldValue);
                        }
                    }
                });


                if (j == numeroDeVariables) {
                    variablesGrid.add(new Label("="), j, i);
                    variablesGrid.add(inputs[i][j], j + 1, i);
                } else {
                    variablesGrid.add(inputs[i][j], j, i);
                }
            }
        }

        calcularButton = new Button("Calcular");
        variablesGrid.add(calcularButton, 0, numeroDeVariables + 1, numeroDeVariables + 1,1);
        GridPane.setHalignment(calcularButton, HPos.CENTER);

        calcularButton.setOnAction(e -> {
            for (int i = 0; i < numeroDeVariables; i++) {
                for (int j = 0; j < numeroDeVariables + 1; j++) {
                    if (!(inputs[i][j].getText().equals(""))) {
                        matriz[i][j] = Integer.parseInt(inputs[i][j].getText());
                    } else {
                        matriz[i][j] = 0;
                    }
                }
            }

            popUp.display(calcular(matriz), nombresVariables);
        });

        return new Scene(variablesGrid, 800, 700);
    }


    public static double[] calcular(double[][] matriz) {
        double[][] newMatriz = new double[matriz.length - 1][matriz[0].length - 1];
        double[] respuestas;
        double[] nuevasRespuestas = new double[matriz.length];
        double temp = 0;

        for (int i = 1; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length - 1; j++) {
                newMatriz[i-1][j] = (matriz[0][0] * matriz[i][j + 1]) - (matriz[i][0] * matriz[0][j + 1]);
            }
        }

        if (newMatriz[0].length > 2) {
            respuestas = calcular(newMatriz);
            for (int i = 0; i < respuestas.length; i++) {
                nuevasRespuestas[i + 1] = respuestas[i];
                temp = temp + (matriz[0][i + 1] * respuestas[i]);
            }
            nuevasRespuestas[0] = (matriz[0][matriz[0].length - 1] - temp) / matriz[0][0];
            return nuevasRespuestas;
        } else {
            return new double[] {(matriz[0][2] - (matriz[0][1] * (newMatriz[0][1] / newMatriz[0][0]))) / matriz[0][0], newMatriz[0][1] / newMatriz[0][0]};
        }
    }
}