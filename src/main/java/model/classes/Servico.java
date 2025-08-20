package model.classes;

/**
 *
 * @author juliano
 */
public class Servico {
    private int idServico;
    private String nomeServico;
    private float valorServico;
    private String descricaoServico;

    public Servico(int idServico, String nomeServico, float valorServico, String descricaoServico) {
        this.idServico = idServico;
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.descricaoServico = descricaoServico;
    }

    public Servico(String nomeServico, float valorServico, String descricaoServico) {
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.descricaoServico = descricaoServico;
    }
    
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public float getValorServico() {
        return valorServico;
    }

    public void setValorServico(float valorServico) {
        this.valorServico = valorServico;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    @Override
    public String toString() {
        return nomeServico;
    }

}
