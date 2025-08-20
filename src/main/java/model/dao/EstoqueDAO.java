package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class EstoqueDAO {

    private Connection con;

    public EstoqueDAO(Connection con) {
        this.con = con;
    }
    
    public List<Estoque> getAll(int filtroSelecionado, String txtFiltro) {
        List<Estoque> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT e.*, p.*, cp.* FROM estoque e " +
                         "JOIN produto p ON (e.fk_idproduto_estoque = p.pk_idproduto) " +
                         "JOIN categoria_prod cp ON (cp.pk_idcategoria_prod = p.fk_idcategoria_prod_produto) ";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = "";
                    break;
                case 0:
                    filtroSql = "WHERE cp.nome_categoria_prod like ? ";
                    break;
                case 1:
                    filtroSql = "WHERE e.aquisicao_estoque BETWEEN ? AND ? ";
                    break;
                case 2:
                    filtroSql = "WHERE e.fabricacao_estoque BETWEEN ? AND ? ";
                    break;
                case 3:
                    filtroSql = "WHERE e.validade_estoque BETWEEN ? AND ? ";
                    break;
                case 4:
                    filtroSql = "WHERE p.fabricante_produto like ? ";
                    break;
                case 5:
                    filtroSql = "WHERE p.nome_produto like ? ";
                    break;
                case 6:
                    filtroSql = "WHERE cp.pk_idcategoria_prod = ? ";
                    break;
                default:
                    break;
            }
            
            //preparando a String sql para execução
            sql += filtroSql;
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            switch (filtroSelecionado){
                case 0:
                case 4:
                case 5:
                    stmt.setString(1, "%" + txtFiltro + "%");
                    break;
                case 1:
                case 2:
                case 3:
                    String[] datas = txtFiltro.split(" ");
                    stmt.setString(1, datas[0]);
                    stmt.setString(2, datas[1]);
                    break;
                case 6:
                    stmt.setInt(1, Integer.parseInt(txtFiltro));
                default:
                    break;
                
            }
            
            
            if (filtroSelecionado >= 0 && filtroSelecionado <= 3) {
                stmt.setString(1, "%" + txtFiltro + "%");
            }
            
            

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Produto
                int idProduto = res.getInt("pk_idproduto");
                int idCategoriaProduto = res.getInt("pk_idcategoria_prod");
                String nomeProduto = res.getString("nome_produto");
                String categoriaProduto = res.getString("nome_categoria_prod");
                String descricaoProduto = res.getString("descricao_produto");
                String fabricanteProduto = res.getString("fabricante_produto");
                
                Produto produto = new Produto(idProduto, nomeProduto, categoriaProduto, descricaoProduto, fabricanteProduto);
                produto.setIdCategoriaProduto(idCategoriaProduto);
                
                //Atributos do produto no Estoque
                int idEstoque = res.getInt("pk_idestoque");
                int quantidade = res.getInt("quantidade_estoque");
                float valorCusto = res.getFloat("valor_custo_estoque");
                float valorVenda = res.getFloat("valor_venda_estoque");
                LocalDate dtAquisicao = res.getDate("aquisicao_estoque") != null ? res.getDate("aquisicao_estoque").toLocalDate() : null;
                LocalDate dtFabricacao = res.getDate("fabricacao_estoque") != null ? res.getDate("fabricacao_estoque").toLocalDate() : null;
                LocalDate dtValidade = res.getDate("validade_estoque") != null ? res.getDate("validade_estoque").toLocalDate() : null;
                
                //Cria o objeto Estoque
                Estoque produtoEmEstoque = new Estoque(idEstoque, produto, quantidade, valorCusto, valorVenda, dtAquisicao, dtFabricacao, dtValidade);

                //Adiciona o objeto Estoque na lista de Produtos em estoque
                list.add(produtoEmEstoque);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<Estoque> getConsumoNaInternacao(int idDiariaInternacao){
        List<Estoque> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT e.*, ci.*, di.*, p.*, cp.* FROM estoque e " +
                         "JOIN produto p ON (p.pk_idproduto = e.fk_idproduto_estoque) " +
                         "JOIN categoria_prod cp ON (p.fk_idcategoria_prod_produto = cp.pk_idcategoria_prod) " +
                         "JOIN consumo_internacao ci ON (e.pk_idestoque = ci.fk_idestoque_consumo_internacao) " +
                         "JOIN diaria_internacao di ON (di.pk_iddiaria_internacao = ci.fk_iddiaria_internacao_consumo_internacao) " +
                         "WHERE di.pk_iddiaria_internacao = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idDiariaInternacao);
            
            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Produto
                int idProduto = res.getInt("pk_idproduto");
                int idCategoriaProduto = res.getInt("pk_idcategoria_prod");
                String nomeProduto = res.getString("nome_produto");
                String categoriaProduto = res.getString("nome_categoria_prod");
                String descricaoProduto = res.getString("descricao_produto");
                String fabricanteProduto = res.getString("fabricante_produto");
                
                Produto produto = new Produto(idProduto, nomeProduto, categoriaProduto, descricaoProduto, fabricanteProduto);
                produto.setIdCategoriaProduto(idCategoriaProduto);
                
                //Atributos do produto no Estoque
                int idEstoque = res.getInt("pk_idestoque");
                int quantidade = res.getInt("quantidade_estoque");
                int quantidadeConsumida = res.getInt("quantidade_consumo_internacao");
                float valorCusto = res.getFloat("valor_custo_estoque");
                float valorVenda = res.getFloat("valor_venda_estoque");
                LocalDate dtAquisicao = res.getDate("aquisicao_estoque") != null ? res.getDate("aquisicao_estoque").toLocalDate() : null;
                LocalDate dtFabricacao = res.getDate("fabricacao_estoque") != null ? res.getDate("fabricacao_estoque").toLocalDate() : null;
                LocalDate dtValidade = res.getDate("validade_estoque") != null ? res.getDate("validade_estoque").toLocalDate() : null;
                
                int quantidadeAtualizada = quantidade - quantidadeConsumida < 0 ? 0 : quantidade - quantidadeConsumida;
                
                String sql2 = "UPDATE estoque SET quantidade_estoque = ? WHERE pk_idestoque = ? ";
                PreparedStatement stmt2 = con.prepareStatement(sql2);
                
                stmt2.setInt(1, quantidadeAtualizada);
                stmt2.setInt(2, idEstoque);
                stmt2.executeUpdate();
                
                //Cria o objeto Estoque
                Estoque produtoEmEstoque = new Estoque(idEstoque, produto, quantidadeAtualizada, valorCusto, valorVenda, dtAquisicao, dtFabricacao, dtValidade);
                produtoEmEstoque.setQuantidadeConsumida(quantidadeConsumida);
                //Adiciona o objeto Estoque na lista de Produtos em estoque
                list.add(produtoEmEstoque);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public Estoque getProdutoEmEstoque(int idEstoque) {
        Estoque produtoEmEstoque = null;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT e.*, p.*, cp.* FROM estoque e " +
                         "JOIN produto p ON (e.fk_idproduto_estoque = p.pk_idproduto) " +
                         "JOIN categoria_prod cp ON (cp.pk_idcategoria_prod = p.fk_idcategoria_prod_produto) " +
                         "WHERE e.pk_idestoque = ? " +
                         "ORDER BY p.nome_produto";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idEstoque);

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Produto
                int idProduto = res.getInt("pk_idproduto");
                int idCategoriaProduto = res.getInt("pk_idcategoria_prod");
                String nomeProduto = res.getString("nome_produto");
                String categoriaProduto = res.getString("nome_categoria_prod");
                String descricaoProduto = res.getString("descricao_produto");
                String fabricanteProduto = res.getString("fabricante_produto");
                
                Produto produto = new Produto(idProduto, nomeProduto, categoriaProduto, descricaoProduto, fabricanteProduto);
                produto.setIdCategoriaProduto(idCategoriaProduto);
                
                //Atributos do produto no Estoque
                int quantidade = res.getInt("quantidade_estoque");
                float valorCusto = res.getFloat("valor_custo_estoque");
                float valorVenda = res.getFloat("valor_venda_estoque");
                LocalDate dtAquisicao = res.getDate("aquisicao_estoque") != null ? res.getDate("aquisicao_estoque").toLocalDate() : null;
                LocalDate dtFabricacao = res.getDate("fabricacao_estoque") != null ? res.getDate("fabricacao_estoque").toLocalDate() : null;
                LocalDate dtValidade = res.getDate("validade_estoque") != null ? res.getDate("validade_estoque").toLocalDate() : null;
                
                //Cria o objeto Estoque
                produtoEmEstoque = new Estoque(idEstoque, produto, quantidade, valorCusto, valorVenda, dtAquisicao, dtFabricacao, dtValidade);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return produtoEmEstoque;
        }
    }
    
    public boolean inserirProdutoNoEstoque(Estoque estoque) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO estoque (fk_idproduto_estoque, quantidade_estoque, validade_estoque, fabricacao_estoque, aquisicao_estoque, valor_custo_estoque, valor_venda_estoque) "
                    + "VALUES (?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setInt(1, estoque.getProduto().getId());
            stmt.setInt(2, estoque.getQuantidade());
            if (estoque.getDtValidade() == null){
                stmt.setDate(3, null);
            }else{
                stmt.setDate(3, Date.valueOf(estoque.getDtValidade()));
            }
            if (estoque.getDtFabricacao() == null){
                stmt.setDate(4, null);
            }else{
                stmt.setDate(4, Date.valueOf(estoque.getDtFabricacao()));
            }
            if(estoque.getDtAquisicao() == null){
                stmt.setDate(5, null);
            }else{
                stmt.setDate(5, Date.valueOf(estoque.getDtAquisicao()));
            }
            stmt.setFloat(6, estoque.getValorCusto());
            stmt.setFloat(7, estoque.getValorVenda());

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID da clínica no parâmetro que foi recebido pelo método
                    estoque.setId(id);
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
    
    public boolean excluir(Estoque estoque) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from estoque where pk_idestoque = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, estoque.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editar(Estoque estoque) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE estoque SET  "
                    + "fk_idproduto_estoque = ?, quantidade_estoque = ?, validade_estoque = ?, fabricacao_estoque = ?, aquisicao_estoque = ?, valor_custo_estoque = ?, valor_venda_estoque = ? "
                    + "WHERE pk_idestoque = ? ";
            int idProduto = estoque.getProduto().getId();
            int quantidade = estoque.getQuantidade();
            LocalDate validadeLD = estoque.getDtValidade();
            Date validade = validadeLD == null ? null : Date.valueOf(validadeLD);
            LocalDate fabricacaoLD = estoque.getDtFabricacao();
            Date fabricacao = fabricacaoLD == null ? null : Date.valueOf(fabricacaoLD);
            LocalDate aquisicaoLD = estoque.getDtAquisicao();
            Date aquisicao = aquisicaoLD == null ? null : Date.valueOf(aquisicaoLD);
            float valorCusto = estoque.getValorCusto();
            float valorVenda = estoque.getValorVenda();
            int idEstoque = estoque.getId();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idProduto);
            stmt.setInt(2, quantidade);
            stmt.setDate(3, validade);
            stmt.setDate(4, fabricacao);
            stmt.setDate(5, aquisicao);
            stmt.setFloat(6, valorCusto);
            stmt.setFloat(7, valorVenda);
            stmt.setInt(8, idEstoque);

            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean cadastrarConsumo(Estoque estoque) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE estoque SET  "
                    + "quantidade_estoque = ? "
                    + "WHERE pk_idestoque = ? ";
            
            int quantidade = estoque.getQuantidade();
            int idEstoque = estoque.getId();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idEstoque);

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
