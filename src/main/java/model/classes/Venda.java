package model.classes;

import java.time.LocalDateTime;
import java.util.List;
import model.classes.controleEstoque.Estoque;
import model.enums.FormaPagamento;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class Venda {
    private int id;
    private LocalDateTime dataHora;
    private List<Estoque> produtosVendidos;
    private float valorTotal;
    private String nome;
    private String cpf;
    private String vendedor;
    private FormaPagamento formaPagamento;
    private int numeroParcelas;

    public Venda(int id, LocalDateTime dataHora, List<Estoque> produtosVendidos, float valorTotal, String nome, String cpf, String vendedor, FormaPagamento formaPagamento, int numeroParcelas) {
        this.id = id;
        this.dataHora = dataHora;
        this.produtosVendidos = produtosVendidos;
        this.valorTotal = valorTotal;
        this.nome = nome;
        this.cpf = cpf;
        this.vendedor = vendedor;
        this.formaPagamento = formaPagamento;
        this.numeroParcelas = numeroParcelas;
    }

    public Venda(LocalDateTime dataHora, List<Estoque> produtosVendidos, float valorTotal, String nome, String cpf, String vendedor, FormaPagamento formaPagamento, int numeroParcelas) {
        this.dataHora = dataHora;
        this.produtosVendidos = produtosVendidos;
        this.valorTotal = valorTotal;
        this.nome = nome;
        this.cpf = cpf;
        this.vendedor = vendedor;
        this.formaPagamento = formaPagamento;
        this.numeroParcelas = numeroParcelas;
    }

    public Venda(int id, LocalDateTime dataHora, float valorTotal, String nome, String cpf, String vendedor, FormaPagamento formaPagamento, int numeroParcelas) {
        this.id = id;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
        this.nome = nome;
        this.cpf = cpf;
        this.vendedor = vendedor;
        this.formaPagamento = formaPagamento;
        this.numeroParcelas = numeroParcelas;
    }

    public Venda() {
    }
    
    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public List<Estoque> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(List<Estoque> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void addProduto(Estoque produto){
        produtosVendidos.add(produto);
    }
    
    public float getDesconto(){
        float subTotal = 0;
        for(Estoque produto : produtosVendidos){
            subTotal += produto.getValorVenda();
        }
        return valorTotal - subTotal;
    }

    @Override
    public String toString() {
        return "Venda{" + "id=" + id + ", dataHora=" + dataHora + ", produtosVendidos=" + produtosVendidos + ", valorTotal=" + valorTotal + ", nome=" + nome + ", cpf=" + cpf + '}';
    }
    
    
}
