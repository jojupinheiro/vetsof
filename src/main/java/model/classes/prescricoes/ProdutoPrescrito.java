package model.classes.prescricoes;

/**
 *
 * @author juliano
 */
public class ProdutoPrescrito {
    String formaUso;
    String produto;
    String Quantidade;
    String posologia;

    public ProdutoPrescrito(String formaUso, String produto, String Quantidade, String posologia) {
        this.formaUso = formaUso;
        this.produto = produto;
        this.Quantidade = Quantidade;
        this.posologia = posologia;
    }
    
    

    public String getFormaUso() {
        return formaUso;
    }

    public void setFormaUso(String formaUso) {
        this.formaUso = formaUso;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String Quantidade) {
        this.Quantidade = Quantidade;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }
    
}
