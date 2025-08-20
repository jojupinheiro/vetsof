package model.classes;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class ExameRealizado {
    int id;
    Exame exame;
    float valor;
    String observacao;
    Atendimento atendimento;
    String resultado;
    Pet pet;

    public ExameRealizado(int id, Exame exame, float valor, String observacao, Atendimento atendimento, String resultado) {
        this.id = id;
        this.exame = exame;
        this.valor = valor;
        this.observacao = observacao;
        this.atendimento = atendimento;
        this.resultado = resultado;
    }

    public ExameRealizado(Exame exame, float valor, String observacao, Atendimento atendimento, String resultado) {
        this.exame = exame;
        this.valor = valor;
        this.observacao = observacao;
        this.atendimento = atendimento;
        this.resultado = resultado;
    }

    public ExameRealizado(Exame exame, float valor, String observacao, String resultado) {
        this.exame = exame;
        this.valor = valor;
        this.observacao = observacao;
        this.resultado = resultado;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return exame.getNomeExame();
    }
    
}
