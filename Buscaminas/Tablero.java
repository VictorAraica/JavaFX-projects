package FXJava.Buscaminas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import static java.lang.Integer.min;

public class Tablero {
    Canvas canvas;
     GraphicsContext gc;
     int size;
     GridPane grid;
     Label minasLabel;
     Color lightGray = Color.rgb(210,210,210);
     Color gray = Color.rgb(170,170,170);
     Color darkGray = Color.rgb(60,60,60);

     Color red = Color.rgb(250,50,50);

     Color revisadaLightGray = Color.rgb(170,170,170);
     Color revisadaGray = Color.rgb(130,130,130);
     Color revisadaDarkGray = Color.rgb(20,20,20);
     Color[] colorNumeros = new Color[]{Color.rgb(0, 0, 255), Color.rgb(0, 175, 0), Color.rgb(255, 0, 0),
            Color.rgb(0, 0, 150),Color.rgb(0, 0, 150),Color.rgb(150, 0, 0),Color.rgb(0, 255, 0),
            Color.rgb(255, 255, 0), Color.rgb(115, 0, 255)};

     Juego partidas;
     Image imagenMina = new Image("file:\\D:\\Programacion\\Java\\Java Basics\\src\\FXJava\\Buscaminas\\mina2.png");

    Button resetButton;



    public Scene displayTablero(Juego partida) {
        partidas = partida;
        size = min(800 / partida.ancho, 600 / partida.alto);
        grid = new GridPane();
        grid.setVgap(30);
        grid.setHgap(30);
        grid.setAlignment(Pos.CENTER);

        minasLabel = new Label(Integer.toString(partidas.minas - partidas.marcadas));
        minasLabel.setFont(new Font(30));
        minasLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(minasLabel, 0.0);
        AnchorPane.setRightAnchor(minasLabel, 0.0);
        minasLabel.setAlignment(Pos.CENTER);
        grid.add(minasLabel,0,0);

        canvas = new Canvas(partida.ancho * size, partida.alto * size);

        gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < partida.alto; i++) {
            for (int j = 0; j < partida.ancho; j++) {
                dibujarCasilla(partida.tablero[i][j] ,j * size, i * size, size);
            }
        }

        canvas.setOnMouseClicked(event -> {
            int x = (int)event.getX() / size;
            int y = (int)event.getY() / size;
            if (event.getButton() == MouseButton.PRIMARY && !partidas.endGame && !partidas.ganador) {
                if (!partidas.tablero[y][x].marcada && !partidas.tablero[y][x].revisada) {
                    clickIzquierdo(x, y);
                }

            } else if (event.getButton() == MouseButton.SECONDARY && !partidas.endGame && !partidas.ganador) {
                if (!partidas.tablero[y][x].marcada && !partidas.tablero[y][x].revisada && partidas.minas - partidas.marcadas > 0) {
                    partidas.tablero[y][x].marcada = true;
                    partidas.marcadas++;
                    dibujarCasilla(partidas.tablero[y][x] ,x * size, y * size, size);
                    minasLabel.setText(Integer.toString(partidas.minas - partidas.marcadas));

                } else if (partidas.tablero[y][x].marcada){
                    partidas.tablero[y][x].marcada = false;
                    dibujarCasilla(partidas.tablero[y][x] ,x * size, y * size, size);
                    partidas.marcadas--;
                    minasLabel.setText(Integer.toString(partidas.minas - partidas.marcadas));
                }

            }
        });

        grid.add(canvas, 0, 1);

        resetButton = new Button("Reiniciar");
        resetButton.setAlignment(Pos.CENTER);
        grid.add(resetButton, 0, 2);
        GridPane.setHalignment(resetButton, HPos.CENTER);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                partidas.reset();
                for (int i = 0; i < partida.alto; i++) {
                    for (int j = 0; j < partida.ancho; j++) {
                        dibujarCasilla(partida.tablero[i][j] ,j * size, i * size, size);
                    }
                }
            }
        });

        return new Scene(grid, 900, 800);

    }

    public void dibujarCasilla(Casilla casilla, double x, double y, int size) {
        if (!casilla.marcada && !casilla.revisada) {
            gc.setFill(gray);
            gc.fillRect(x + (size * 0.1), y + (size * 0.1), size * 0.8, size * 0.8);

            gc.setFill(lightGray);
            gc.fillPolygon(new double[]{x, x + size, x + size - (size * 0.1), x + (size * 0.1), x + (size * 0.1), x}, new double[]{y, y, y + (size * 0.1), y + (size * 0.1), y + size - (size * 0.1), y + size}, 6);

            gc.setFill(darkGray);
            gc.fillPolygon(new double[]{x + size, x + size, x, x + (size * 0.1), x + size - (size * 0.1), x + size - (size * 0.1)}, new double[]{y, y + size, y + size, y + size - (size * 0.1), y + size - (size * 0.1), y + (size * 0.1)}, 6);

        } else if (casilla.marcada) {
            gc.setFill(gray);
            gc.fillRect(x + (size * 0.1), y + (size * 0.1), size * 0.8, size * 0.8);

            gc.setFill(red);
            gc.fillPolygon(new double[]{x + (size * 0.1), x + (size * 0.5), x + (size * 0.9), x + size, x + (size * 0.6), x + size, x + (size * 0.9), x + (size * 0.5), x + (size * 0.1), x, x + (size * 0.4), x},
                    new double[]{y, y + (size * 0.4), y, y + (size * 0.1), y + (size * 0.5), y + (size * 0.9), y + size, y + (size * 0.6), y + size, y + (size * 0.9), y + (size * 0.5), y + (size * 0.1)}, 12);

            gc.setFill(lightGray);
            gc.fillPolygon(new double[]{x, x + size, x + size - (size * 0.1), x + (size * 0.1), x + (size * 0.1), x}, new double[]{y, y, y + (size * 0.1), y + (size * 0.1), y + size - (size * 0.1), y + size}, 6);

            gc.setFill(darkGray);
            gc.fillPolygon(new double[]{x + size, x + size, x, x + (size * 0.1), x + size - (size * 0.1), x + size - (size * 0.1)}, new double[]{y, y + size, y + size, y + size - (size * 0.1), y + size - (size * 0.1), y + (size * 0.1)}, 6);

        } else {
            gc.setFill(revisadaGray);
            gc.fillRect(x + (size * 0.1), y + (size * 0.1), size * 0.8, size * 0.8);

            gc.setFill(revisadaDarkGray);
            gc.fillPolygon(new double[]{x, x + size, x + size - (size * 0.1), x + (size * 0.1), x + (size * 0.1), x}, new double[]{y, y, y + (size * 0.1), y + (size * 0.1), y + size - (size * 0.1), y + size}, 6);

            gc.setFill(revisadaLightGray);
            gc.fillPolygon(new double[]{x + size, x + size, x, x + (size * 0.1), x + size - (size * 0.1), x + size - (size * 0.1)}, new double[]{y, y + size, y + size, y + size - (size * 0.1), y + size - (size * 0.1), y + (size * 0.1)}, 6);
        }
    }

    public void clickIzquierdo(int x, int y) {
        if (!partidas.tablero[y][x].revisada) {
            int nroMinas = 0;
            if (!partidas.tablero[y][x].mina) {
                partidas.tablero[y][x].revisada = true;
                partidas.revisadas++;
                dibujarCasilla(partidas.tablero[y][x], x * size, y * size, size);
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i != 0 || j != 0) {
                            if (y + i < partidas.alto && y + i > -1 && x + j < partidas.ancho && x + j > -1) {
                                if (partidas.tablero[y + i][x + j].mina) {
                                    nroMinas++;
                                }
                            }
                        }
                    }
                }

                if (partidas.revisadas == partidas.total - partidas.minas) {
                    ganar();
                }

                if (nroMinas == 0) {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (i != 0 || j != 0) {
                                if (y + i < partidas.alto && y + i > -1 && x + j < partidas.ancho && x + j > -1) {
                                    clickIzquierdo(x + j, y + i);
                                }
                            }
                        }
                    }
                } else {
                    gc.setFill(colorNumeros[nroMinas - 1]);
                    gc.setFont(new Font(size - (size * 0.2)));
                    gc.fillText(Integer.toString(nroMinas), (x * size) + (size * 0.3), (y * size) + size - (size * 0.2));
                }
            } else {
                endGame(x, y);
            }
        }
    }


    public void endGame(int x, int y) {
        partidas.tablero[y][x].revisada = true;
        dibujarCasilla(partidas.tablero[y][x] ,x * size, y * size, size);
        partidas.endGame = true;
        minasLabel.setText("Perdiste");

        for (int i = 0; i < partidas.alto; i++) {
            for (int j = 0; j < partidas.ancho; j++) {

                if (partidas.tablero[i][j].mina) {
                    gc.drawImage(imagenMina, j * size + (size * 0.2), i * size + (size * 0.2), size - (size * 0.4), size - (size * 0.4));
                }
            }
        }
    }


    public void ganar() {
        minasLabel.setText("Ganaste");
        partidas.endGame = true;
    }

}
