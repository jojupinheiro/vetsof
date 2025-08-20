package model.classes;

/**
 *
 * @author juliano
 */
public class Exame {

    private int idExame;
    private String nomeExame;
    private float valorExame;
    private String descricaoExame;

    public Exame(int idExame, String nomeExame, float valorExame, String descricaoExame) {
        this.idExame = idExame;
        this.nomeExame = nomeExame;
        this.valorExame = valorExame;
        this.descricaoExame = descricaoExame;
    }

    public Exame(String nomeExame, float valorExame, String descricaoExame) {
        this.nomeExame = nomeExame;
        this.valorExame = valorExame;
        this.descricaoExame = descricaoExame;
    }

    public int getIdExame() {
        return idExame;
    }

    public void setIdExame(int idExame) {
        this.idExame = idExame;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public float getValorExame() {
        return valorExame;
    }

    public void setValorExame(float valorExame) {
        this.valorExame = valorExame;
    }

    public String getDescricaoExame() {
        return descricaoExame;
    }

    public void setDescricaoExame(String descricaoExame) {
        this.descricaoExame = descricaoExame;
    }

    @Override
    public String toString() {
        return nomeExame;
    }

}
