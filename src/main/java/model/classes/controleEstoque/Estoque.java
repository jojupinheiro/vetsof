package model.classes.controleEstoque;

import java.time.LocalDate;
import java.util.Objects;
import view.utils.Utils;

/**
 * Classe que demonstra os produtos em estoque
 * @author juliano
 */
public class Estoque {
    private int id;
    private Produto produto;
    private int quantidade;
    private float valorCusto;
    private float valorVenda;
    private LocalDate dtAquisicao;
    private LocalDate dtFabricacao;
    private LocalDate dtValidade;
    private int quantidadeConsumida;

    public Estoque(int id, Produto produto, int quantidade, LocalDate dtAquisicao, LocalDate dtFabricacao, LocalDate dtValidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.dtAquisicao = dtAquisicao;
        this.dtFabricacao = dtFabricacao;
        this.dtValidade = dtValidade;
    }

    public Estoque(Produto produto, int quantidade, LocalDate dtAquisicao, LocalDate dtFabricacao, LocalDate dtValidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.dtAquisicao = dtAquisicao;
        this.dtFabricacao = dtFabricacao;
        this.dtValidade = dtValidade;
    }

    public Estoque(Produto produto, int quantidade, float valorCusto, float valorVenda, LocalDate dtAquisicao, LocalDate dtFabricacao, LocalDate dtValidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.dtAquisicao = dtAquisicao;
        this.dtFabricacao = dtFabricacao;
        this.dtValidade = dtValidade;
    }

    public Estoque(int id, Produto produto, int quantidade, float valorCusto, float valorVenda, LocalDate dtAquisicao, LocalDate dtFabricacao, LocalDate dtValidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.dtAquisicao = dtAquisicao;
        this.dtFabricacao = dtFabricacao;
        this.dtValidade = dtValidade;
    }
    
    public Estoque(int id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Estoque(int id, Produto produto) {
        this.id = id;
        this.produto = produto;
    }

    public Estoque(Produto produto) {
        this.produto = produto;
    }

    public Estoque(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getQuantidadeConsumida() {
        return quantidadeConsumida;
    }

    public void setQuantidadeConsumida(int quantidadeConsumida) {
        this.quantidadeConsumida = quantidadeConsumida;
    }

    public float getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(float valorCusto) {
        this.valorCusto = valorCusto;
    }

    public float getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(float valorVenda) {
        this.valorVenda = valorVenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDtAquisicao() {
        return dtAquisicao;
    }

    public void setDtAquisicao(LocalDate dtAquisicao) {
        this.dtAquisicao = dtAquisicao;
    }

    public LocalDate getDtFabricacao() {
        return dtFabricacao;
    }

    public void setDtFabricacao(LocalDate dtFabricacao) {
        this.dtFabricacao = dtFabricacao;
    }

    public LocalDate getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(LocalDate dtValidade) {
        this.dtValidade = dtValidade;
    }
    
    public String getNomeProduto() {
        return produto.getNome();
    }
    
    public String getCategoriaProduto() {
        return produto.getCategoria();
    }
    
    public String getFabricanteProduto() {
        return produto.getFabricante();
    }
    
    public float getSubtotal(){
        return quantidadeConsumida * valorVenda;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.produto);
        hash = 97 * hash + this.quantidade;
        hash = 97 * hash + Objects.hashCode(this.dtAquisicao);
        hash = 97 * hash + Objects.hashCode(this.dtFabricacao);
        hash = 97 * hash + Objects.hashCode(this.dtValidade);
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
        final Estoque other = (Estoque) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.quantidade != other.quantidade) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (!Objects.equals(this.dtAquisicao, other.dtAquisicao)) {
            return false;
        }
        if (!Objects.equals(this.dtFabricacao, other.dtFabricacao)) {
            return false;
        }
        return Objects.equals(this.dtValidade, other.dtValidade);
    }

    @Override
    public String toString() {
        return produto.getNome() + " - " + quantidadeConsumida + " (" + Utils.imprimeValor("R$ " + String.valueOf(quantidadeConsumida * valorVenda)) + ")";
    }
    
    
}
