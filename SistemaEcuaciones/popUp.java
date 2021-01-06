package FXJava.SistemaEcuaciones;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class popUp {
    public static void display(double[] respuestas, String[] nombresVariables) {
        Label[] labels = new Label[respuestas.length];

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Respuestas");
        window.setMinWidth(250);
        window.setMinHeight(50 * respuestas.length);

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        for (int i = 0; i < respuestas.length; i++) {
            labels[i] = new Label(nombresVariables[i] + " = " + respuestas[i]);
            layout.getChildren().add(labels[i]);
        }



        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }
}
