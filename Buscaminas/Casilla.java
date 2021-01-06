package FXJava.Buscaminas;

public class Casilla {
    public boolean mina;
    public boolean marcada;
    public boolean revisada;


    Casilla(boolean inputMina) {
        this.mina = inputMina;
        this.marcada  = false;
        this.revisada  = false;
    }

}
