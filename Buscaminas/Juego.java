package FXJava.Buscaminas;

import java.util.Random;

public class Juego {
    int alto;
    int ancho;
    int minas;
    int total;
    int revisadas;
    int marcadas;
    public Casilla[][] tablero;
    boolean endGame;
    boolean ganador;

    public Juego(int alto, int ancho, int minas) {
        int xMina, yMina;
        Random r = new Random();
        this.total = alto * ancho;
        this.marcadas = 0;
        this.revisadas = 0;
        this.endGame = false;
        this.alto = alto;
        this.ancho = ancho;
        this.minas = minas;
        this.tablero = new Casilla[this.alto][this.ancho];


        for (int i = 0; i < this.alto; i++) {

            for (int j = 0; j < this.ancho; j++) {

                this.tablero[i][j] = new Casilla(false);

            }

        }

        for (int i = 0; i < minas; i++) {
            xMina = r.nextInt(this.ancho);
            yMina = r.nextInt(this.alto);

            if (this.tablero[yMina][xMina].mina) {
                i--;
            }

            this.tablero[yMina][xMina].mina = true;
        }
    }

    public void reset(){
        int xMina, yMina;
        Random r = new Random();
        this.total = alto * ancho;
        this.marcadas = 0;
        this.revisadas = 0;
        this.endGame = false;
        this.tablero = new Casilla[this.alto][this.ancho];


        for (int i = 0; i < this.alto; i++) {

            for (int j = 0; j < this.ancho; j++) {

                this.tablero[i][j] = new Casilla(false);

            }

        }

        for (int i = 0; i < minas; i++) {
            xMina = r.nextInt(this.ancho);
            yMina = r.nextInt(this.alto);

            if (this.tablero[yMina][xMina].mina) {
                i--;
            }

            this.tablero[yMina][xMina].mina = true;
        }
    }
}
