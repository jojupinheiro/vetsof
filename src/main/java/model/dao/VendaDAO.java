package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.classes.Venda;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.db.DB;
import model.enums.FormaPagamento;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio
 * Aires - RS
 */
public class VendaDAO {

    private Connection con;

    public VendaDAO(Connection con) {
        this.con = con;
    }

    public List<Venda> getAll(int filtroSelecionado, String txtFiltro) {
        List<Venda> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT v.* FROM venda v ";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1 ->
                    filtroSql = " ";
                case 0 ->
                    filtroSql = "WHERE v.cpf_venda like ? ";
                case 1 ->
                    filtroSql = "WHERE v.nome_venda like ? ";
                case 2 ->
                    filtroSql = "WHERE v.vendedor_venda like ? ";
                case 3 ->
                    filtroSql = "WHERE v.formapagamento_venda like ? ";
                case 4 ->
                    filtroSql = "WHERE v.pk_idvenda = ? ";
                case 5 ->
                    filtroSql = "WHERE v.datahora_venda BETWEEN ? AND ? ";
                default -> {
                }
            }

            //preparando a String sql para execução
            sql += filtroSql;
            sql += "ORDER BY v.datahora_venda";
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            switch (filtroSelecionado) {
                case 0, 1, 2, 3 ->
                    stmt.setString(1, "%" + txtFiltro + "%");
                case 4 ->
                    stmt.setString(1, txtFiltro);
                case 5 -> {
                    String[] datas = txtFiltro.split(" ");     //Se for filtrar valores entre duas datas, elas são recebidas na forma de uma string
                    stmt.setString(1, datas[0]);               //com um espaço entre elas, e aqui são divididas em um vetor para serem adicionadas
                    stmt.setString(2, datas[1]);               //ao query
                }
                default -> {
                }
            }

            res = stmt.executeQuery();

            while (res.next()) {
                int idVend = res.getInt("pk_idvenda");
                LocalDateTime dataHoraVenda = res.getObject("datahora_venda", LocalDateTime.class);
                float valorTotalVenda = res.getFloat("valortotal_venda");
                String nomeVenda = res.getString("nome_venda");
                String cpfVenda = res.getString("cpf_venda");
                String vendedorVenda = res.getString("vendedor_venda");
                int parcelas = res.getInt("numeroparcelas_venda");
                FormaPagamento formaPagamento = FormaPagamento.getFormaPagamento(res.getInt("formapagamento_venda"));
                
                Venda venda = new Venda(idVend, dataHoraVenda, valorTotalVenda, nomeVenda, cpfVenda, vendedorVenda, formaPagamento, parcelas);
                list.add(venda);
            }

            sql = "SELECT ve.*, e.*, p.*, cp.* FROM venda_estoque ve "
                    + "join estoque e on (ve.fk_idestoque = e.pk_idestoque) "
                    + "join produto p on (e.fk_idproduto_estoque = p.pk_idproduto) "
                    + "join categoria_prod cp on (p.fk_idcategoria_prod_produto = cp.pk_idcategoria_prod)";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            int index = 0;
            while (res.next()) {
                int idVenda = res.getInt("ve.fk_idvenda");
                int idVendaEstoque = res.getInt("ve.pk_idvenda_estoque");
                int idEstoque = res.getInt("ve.fk_idestoque");
                int idProduto = res.getInt("e.fk_idproduto_estoque");
                int quantidadeVendida = res.getInt("ve.qtd_consumida_venda_estoque");
                float valorVenda = res.getFloat("e.valor_venda_estoque");
                float valorCusto = res.getFloat("e.valor_custo_estoque");
                String nomeProduto = res.getString("p.nome_produto");
                String categoriaProduto = res.getString("cp.nome_categoria_prod");
                
                Produto produto = new Produto(idProduto, nomeProduto, categoriaProduto);
                Estoque itemVendido = new Estoque(produto, quantidadeVendida);

                if (list.get(index).getId() == idVenda) {
                    list.get(index).addProduto(itemVendido);
                }else{
                    do{
                        index ++;
                    }while (list.get(index).getId() != idVenda);
                    list.get(index).addProduto(itemVendido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Venda venda) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO venda (datahora_venda, valortotal_venda, nome_venda, cpf_venda, vendedor_venda, numeroparcelas_venda, formapagamento_venda)"
                    + "VALUES (?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setObject(1, venda.getDataHora());
            stmt.setFloat(2, venda.getValorTotal());
            stmt.setString(3, venda.getNome());
            stmt.setString(4, venda.getCpf());
            stmt.setString(5, venda.getVendedor());
            stmt.setInt(6, venda.getNumeroParcelas());
            stmt.setInt(7, venda.getFormaPagamento().getIdFormaPagamento());

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    venda.setId(id);
                    
                    for (Estoque produto : venda.getProdutosVendidos()) {
                        sql = "INSERT INTO venda_estoque (fk_idvenda, fk_idestoque, qtd_consumida_venda_estoque) "
                                + "VALUES (?,?,?)";
                        stmt = con.prepareStatement(sql);
                        
                        stmt.setInt(1, venda.getId());
                        stmt.setInt(2, produto.getId());
                        stmt.setInt(3, produto.getQuantidadeConsumida());                        
                    }
                    
                    result = true;
                    //Depois daqui vai para o finally
                }
            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean excluir(Venda venda) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from venda where pk_idvenda = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, venda.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(Venda venda) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE venda SET \n"
                    + "datahora_venda = ?, valortotal_venda = ?, nome_venda = ?, cpf_venda = ?, vendedor_venda = ?, numeroparcelas_venda = ?, "
                    + "formapagamento_venda = ? WHERE pk_idvenda = ?;";
            String cpf = venda.getCpf();
            int idVenda = venda.getId();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idVenda);

            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
}
