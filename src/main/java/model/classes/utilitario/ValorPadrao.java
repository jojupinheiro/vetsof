package model.classes.utilitario;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class ValorPadrao {
    
    int id;
    int codigoValorPadrao;
    float valorPadraoNumeral;
    String valorPadraoString;

    public ValorPadrao(int id, int codigoValorPadrao, float valorPadraoNumeral, String valorPadraoString) {
        this.id = id;
        this.codigoValorPadrao = codigoValorPadrao;
        this.valorPadraoNumeral = valorPadraoNumeral;
        this.valorPadraoString = valorPadraoString;
    }

    public ValorPadrao(int codigoValorPadrao, float valorPadraoNumeral, String valorPadraoString) {
        this.codigoValorPadrao = codigoValorPadrao;
        this.valorPadraoNumeral = valorPadraoNumeral;
        this.valorPadraoString = valorPadraoString;
    }

    public ValorPadrao(int codigoValorPadrao) {
        this.codigoValorPadrao = codigoValorPadrao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoValorPadrao() {
        return codigoValorPadrao;
    }

    public void setCodigoValorPadrao(int codigoValorPadrao) {
        this.codigoValorPadrao = codigoValorPadrao;
    }

    public float getValorPadraoNumeral() {
        return valorPadraoNumeral;
    }

    public void setValorPadraoNumeral(float valorPadraoNumeral) {
        this.valorPadraoNumeral = valorPadraoNumeral;
    }

    public String getValorPadraoString() {
        return valorPadraoString;
    }

    public void setValorPadraoString(String valorPadraoString) {
        this.valorPadraoString = valorPadraoString;
    }

    @Override
    public String toString() {
        return "ValorPadrao{" + "id=" + id + ", codigoValorPadrao=" + codigoValorPadrao + ", valorPadraoNumeral=" + valorPadraoNumeral + ", valorPadraoString=" + valorPadraoString + '}';
    }
    
    
}
