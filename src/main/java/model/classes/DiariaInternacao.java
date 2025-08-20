package model.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.classes.controleEstoque.Estoque;
import java.util.List;

/**
 * Classe para registrar os acontecimentos de cada diária na internação
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class DiariaInternacao {
    private int id;
    private int numeroDiaria;
    private String notas;
    private String tratamento;
    private String sinaisClinicos;
    private LocalDate data;
    private List<ServicoRealizado> listaServico;
    private List<ExameRealizado> listaExames;
    private List<Vacina> listaVacinas;
    private List<Estoque> listaConsumo;
    
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DiariaInternacao(int id, String notas, String tratamento, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames, List<Estoque> listaConsumo) {
        this.id = id;
        this.notas = notas;
        this.tratamento = tratamento;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
        this.listaConsumo = listaConsumo;
    }

    public DiariaInternacao(String notas, String tratamento, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames, List<Estoque> listaConsumo) {
        this.notas = notas;
        this.tratamento = tratamento;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
        this.listaConsumo = listaConsumo;
    }

    public DiariaInternacao(int numeroDiaria, String notas, String tratamento, String sinaisClinicos, LocalDate data, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames, List<Vacina> listaVacinas) {
        this.numeroDiaria = numeroDiaria;
        this.notas = notas;
        this.tratamento = tratamento;
        this.sinaisClinicos = sinaisClinicos;
        this.data = data;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
        this.listaVacinas = listaVacinas;
    }

    public DiariaInternacao() {
    }

    public int getNumeroDiaria() {
        return numeroDiaria;
    }

    public void setNumeroDiaria(int numeroDiaria) {
        this.numeroDiaria = numeroDiaria;
    }

    public List<Vacina> getListaVacinas() {
        return listaVacinas;
    }

    public void setListaVacinas(List<Vacina> listaVacinas) {
        this.listaVacinas = listaVacinas;
    }

    public String getSinaisClinicos() {
        return sinaisClinicos;
    }

    public void setSinaisClinicos(String sinaisClinicos) {
        this.sinaisClinicos = sinaisClinicos;
    }

    public String getStringData(){
        return data.format(dateFormatter);
    }
    
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public List<ServicoRealizado> getListaServico() {
        return listaServico;
    }

    public void setListaServico(List<ServicoRealizado> listaServico) {
        this.listaServico = listaServico;
    }

    public List<ExameRealizado> getListaExames() {
        return listaExames;
    }

    public void setListaExames(List<ExameRealizado> listaExames) {
        this.listaExames = listaExames;
    }

    public List<Estoque> getListaConsumo() {
        return listaConsumo;
    }

    public void setListaConsumo(List<Estoque> listaConsumo) {
        this.listaConsumo = listaConsumo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiariaInternacao other = (DiariaInternacao) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "DiariaInternacao{" + "id=" + id + ", numeroDiaria=" + numeroDiaria + ", notas=" + notas + ", tratamento=" + tratamento + ", sinaisClinicos=" + sinaisClinicos + ", data=" + data + ", listaServico=" + listaServico + ", listaExames=" + listaExames + ", listaVacinas=" + listaVacinas + ", listaConsumo=" + listaConsumo;
    }

   
    
}
