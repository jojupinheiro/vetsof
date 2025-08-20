package model.classes.prescricoes;

/**
 * Classe para definir a formatação que será utilizada na prescrição
 * Autor: João Juliano Pinheiro
 */
public class FormatacaoPrescricao {
    int id;
    int item;
    int recuo;
    int modelo;
    int tamanho;
    int alinhamento;
    int espacamento;
    boolean presente;
    boolean modeloAtivo;
    boolean negrito;
    boolean italico;
    String texto;
    String cor;
    String fonte;

    public FormatacaoPrescricao(int id, int item, int recuo, int modelo, boolean presente, boolean modeloAtivo, boolean negrito, 
            boolean italico, String texto, String cor, int alinhamento, String fonte, int tamanho, int espacamento) {
        this.id = id;
        this.item = item;
        this.recuo = recuo;
        this.modelo = modelo;
        this.presente = presente;
        this.modeloAtivo = modeloAtivo;
        this.negrito = negrito;
        this.italico = italico;
        this.texto = texto;
        this.cor = cor;
        this.alinhamento = alinhamento;
        this.fonte = fonte;
        this.tamanho = tamanho;
        this.espacamento = espacamento;
    }

    public FormatacaoPrescricao(int item, int recuo, int modelo, boolean presente, boolean modeloAtivo, boolean negrito, 
            boolean italico, String texto, String cor, int alinhamento, String fonte, int tamanho, int espacamento) {
        this.item = item;
        this.recuo = recuo;
        this.modelo = modelo;
        this.presente = presente;
        this.modeloAtivo = modeloAtivo;
        this.negrito = negrito;
        this.italico = italico;
        this.texto = texto;
        this.cor = cor;
        this.alinhamento = alinhamento;
        this.fonte = fonte;
        this.tamanho = tamanho;
        this.espacamento = espacamento;
    }

    public FormatacaoPrescricao() {
    }

    public int getEspacamento() {
        return espacamento;
    }

    public void setEspacamento(int espacamento) {
        this.espacamento = espacamento;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getRecuo() {
        return recuo;
    }

    public void setRecuo(int recuo) {
        this.recuo = recuo;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public boolean isModeloAtivo() {
        return modeloAtivo;
    }

    public void setModeloAtivo(boolean modeloAtivo) {
        this.modeloAtivo = modeloAtivo;
    }

    public boolean isNegrito() {
        return negrito;
    }

    public void setNegrito(boolean negrito) {
        this.negrito = negrito;
    }

    public boolean isItalico() {
        return italico;
    }

    public void setItalico(boolean italico) {
        this.italico = italico;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAlinhamento() {
        return alinhamento;
    }

    public void setAlinhamento(int alinhamento) {
        this.alinhamento = alinhamento;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    @Override
    public String toString() {
        return "FormatacaoPrescricao{" + "id=" + id + ", item=" + item + ", recuo=" + recuo + ", modelo=" + modelo + ", presente=" + presente + ", modeloAtivo=" + modeloAtivo + ", negrito=" + negrito + ", italico=" + italico + ", texto=" + texto + ", cor=" + cor + ", alinhamento=" + alinhamento + ", fonte=" + fonte + '}';
    }
    
}
