package model.classes;

/**
 * Classe destinada a cadastrar as vacinas que serão applicadas nos animais.
 * @author João Juliano Pinheiro
 */
public class ProdutoVacina {
    int id;
    String nomeVacina;
    String tipoVacina;
    int idTipoVacina;
    String laboratorioVacina;
    float valorVacina;

    public ProdutoVacina(int id, String nomeVacina, String tipoVacina, int idTipoVacina, String laboratorioVacina, float valorVacina) {
        this.id = id;
        this.nomeVacina = nomeVacina;
        this.tipoVacina = tipoVacina;
        this.idTipoVacina = idTipoVacina;
        this.laboratorioVacina = laboratorioVacina;
        this.valorVacina = valorVacina;
    }

    public ProdutoVacina(String nomeVacina, String tipoVacina, int idTipoVacina, String laboratorioVacina, float valorVacina) {
        this.nomeVacina = nomeVacina;
        this.tipoVacina = tipoVacina;
        this.idTipoVacina = idTipoVacina;
        this.laboratorioVacina = laboratorioVacina;
        this.valorVacina = valorVacina;
    }

    public ProdutoVacina(String tipoVacina, int idTipoVacina) {
        this.tipoVacina = tipoVacina;
        this.idTipoVacina = idTipoVacina;
    }

    public ProdutoVacina(String tipoVacina) {
        this.tipoVacina = tipoVacina;
    }

    public float getValorVacina() {
        return valorVacina;
    }

    public void setValorVacina(float valorVacina) {
        this.valorVacina = valorVacina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }

    public void setTipoVacina(String tipoVacina) {
        this.tipoVacina = tipoVacina;
    }

    public int getIdTipoVacina() {
        return idTipoVacina;
    }

    public void setIdTipoVacina(int idTipoVacina) {
        this.idTipoVacina = idTipoVacina;
    }

    public String getLaboratorioVacina() {
        return laboratorioVacina;
    }

    public void setLaboratorioVacina(String laboratorioVacina) {
        this.laboratorioVacina = laboratorioVacina;
    }

    @Override
    public String toString() {
        return tipoVacina + " - " + nomeVacina;
    }
    
}
