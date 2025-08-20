package model.classes.prescricoes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.classes.Atendimento;
import model.classes.Clinica;
import model.classes.Pet;
import model.classes.Veterinario;

/**
 *
 * @author juliano
 */
public class Prescricao {

    private int id;
    private Atendimento atendimento;
    private Map<String, Map<String, String[]>> listaProdutos = new TreeMap<>();
    Map<String, String[]> produtosReceitados;
    private String observacoes;
    private LocalDate data;
    private Clinica clinica;
    private Veterinario veterinario;
    private Pet pet;
    
    public Prescricao() {
    }

    public Prescricao(int id, Map<String, Map<String, String[]>> listaProdutos, String observacoes, LocalDate data, Clinica clinica, Veterinario veterinario, Pet pet) {
        this.id = id;
        this.listaProdutos = listaProdutos;
        this.observacoes = observacoes;
        this.data = data;
        this.clinica = clinica;
        this.veterinario = veterinario;
        this.pet = pet;
    }

    public Prescricao(int id, Map<String, Map<String, String[]>> listaProdutos, String observacoes) {
        this.id = id;
        this.listaProdutos = listaProdutos;
        this.observacoes = observacoes;
    }
    
    public Prescricao(int id, Atendimento atendimento, Map<String, String[]> produtosReceitados, String observacoes) {
        this.id = id;
        this.atendimento = atendimento;
        this.produtosReceitados = produtosReceitados;
        this.observacoes = observacoes;
    }

    public void adicionarProduto(ProdutoPrescrito prod) {
        String formaDeUso = prod.getFormaUso();
        String produto = prod.getProduto();
        String[] atributosProduto = new String[2];
        atributosProduto[0] = prod.getQuantidade();
        atributosProduto[1] = prod.getPosologia();

        if (listaProdutos.containsKey(formaDeUso)) {
            listaProdutos.get(formaDeUso).put(produto, atributosProduto);
        } else {
            this.produtosReceitados = new TreeMap<>();
            this.produtosReceitados.put(produto, atributosProduto);
            this.listaProdutos.put(formaDeUso, produtosReceitados);
        }
    }
    
    public void removerProduto(ProdutoPrescrito prod){
        listaProdutos.get(prod.formaUso).remove(prod.produto);
        if (listaProdutos.get(prod.formaUso) == null){
            listaProdutos.remove(prod);
        }
    }
    
    public void limparListaProdutos(){
        listaProdutos.clear();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final Prescricao other = (Prescricao) obj;
        return this.id == other.id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Map<String, Map<String, String[]>> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(Map<String, Map<String, String[]>> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }
    
    public String getNomeVeterinario(){
        return veterinario.getNome();
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Pet getPet() {
        return pet;
    }
    
    public String getNomePet(){
        return pet.getNomePet();
    }
    
    public String getNomeEspecie() {
        return pet.getEspecie().getNome();
    }
    
    public String getNomeTutor() {
        return pet.getTutorPet().getNome();
    }
    
    public String getNomeClinica() {
        return clinica.getNomeClinica();
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
}
