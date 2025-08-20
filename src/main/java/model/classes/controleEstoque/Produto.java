package model.classes.controleEstoque;

/**
 *
 * @author juliano
 */
public class Produto {
    private int idProduto;
    private int idCategoriaProduto;
    private String nome;
    private String categoria;
    private String descricao;
    private String fabricante;

    public Produto(int id, String nome, String categoria, String descricao, String fabricante) {
        this.idProduto = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.fabricante = fabricante;
    }

    public Produto(String nome, String categoria, String descricao, String fabricante) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.fabricante = fabricante;
    }

    public Produto(int idProduto, String nome, String categoria) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.categoria = categoria;
    }

    public Produto(int idCategoriaProduto, String categoria) {
        this.idCategoriaProduto = idCategoriaProduto;
        this.categoria = categoria;
    }

    public Produto(String categoria) {
        this.categoria = categoria;
    }

    public int getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(int idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public int getId() {
        return idProduto;
    }

    public void setId(int id) {
        this.idProduto = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.idProduto;
        hash = 11 * hash + this.idCategoriaProduto;
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
        final Produto other = (Produto) obj;
        if (this.idProduto != other.idProduto) {
            return false;
        }
        return this.idCategoriaProduto == other.idCategoriaProduto;
    }
    
    

    @Override
    public String toString() {
        return categoria ;
    }

}
