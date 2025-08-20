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
 * @author juliano
 */
public class ProdutoDAO {
    private Connection con;

    public ProdutoDAO(Connection con) {
        this.con = con;
    }
        
    public List<Produto> getAll(int filtroSelecionado, String txtFiltro) {
        List<Produto> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT p.*, cp.* FROM produto p " +
                         "JOIN categoria_prod cp ON (cp.pk_idcategoria_prod = p.fk_idcategoria_prod_produto) " +
                         "ORDER BY p.nome_produto";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = "";
                    break;
                case 0:
                    filtroSql = "WHERE ";
                    break;
                case 1:
                    filtroSql = "WHERE ";
                    break;
                case 2:
                    filtroSql = "WHERE ";
                    break;
                case 3:
                    filtroSql = "WHERE ";
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
                

                //Adiciona o objeto Produto na lista de Produtos
                list.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<Produto> getProdutosDaCategoria(int idCategoria) {
        List<Produto> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT p.*, cp.* FROM produto p " +
                         "JOIN categoria_prod cp ON (cp.pk_idcategoria_prod = p.fk_idcategoria_prod_produto) " +
                         "WHERE cp.pk_idcategoria_prod = ? " +
                         "ORDER BY p.nome_produto";

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idCategoria);

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
                

                //Adiciona o objeto Produto na lista de Produtos
                list.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<Produto> getCategoriasDeProduto() {
        List<Produto> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT cp.* FROM categoria_prod cp " +
                         "ORDER BY cp.nome_categoria_prod";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Produto
                int idCategoriaProduto = res.getInt("pk_idcategoria_prod");
                String nomeCategoriaProduto = res.getString("nome_categoria_prod");
                
                Produto categoriaProduto = new Produto(idCategoriaProduto, nomeCategoriaProduto);

                //Adiciona o objeto Produto na lista de Produtos
                list.add(categoriaProduto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public boolean inserirProduto(Produto produto) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO produto (fk_idcategoria_prod_produto, descricao_produto, nome_produto, fabricante_produto) "
                    + "VALUES (?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???
            int idCategoriaProduto = produto.getIdCategoriaProduto();
            String descricao = produto.getDescricao();
            String nome = produto.getNome();
            String fabricante = produto.getFabricante();
            
            stmt.setInt(1, idCategoriaProduto);
            stmt.setString(2, descricao);
            stmt.setString(3, nome);
            stmt.setString(4, fabricante);
            

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
                    produto.setId(id);
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
    
    public boolean inserirCategoriaDeProduto(Produto produto) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO categoria_prod (nome_categoria_prod) "
                    + "VALUE (?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???
            String nomeCategoria = produto.getCategoria();
            
            stmt.setString(1, nomeCategoria);
            
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
                    produto.setId(id);
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
    
    public boolean excluirProduto(Produto produto) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM produto WHERE pk_idproduto = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, produto.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
        public boolean excluirCategoriaDeProduto(Produto produto) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM categoria_prod WHERE pk_idcategoria_prod = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, produto.getIdCategoriaProduto());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editarProduto(Produto produto) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE produto SET  "
                    + "fk_idcategoria_prod_produto = ?, descricao_produto = ?, nome_produto = ?, fabricante_produto = ? "
                    + "WHERE pk_idproduto = ? ";
            
            int idCategoriaProduto = produto.getIdCategoriaProduto();
            String descricao = produto.getDescricao();
            String nome = produto.getNome();
            String fabricante = produto.getFabricante();
            int idProduto = produto.getId();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCategoriaProduto);
            stmt.setString(2, descricao);
            stmt.setString(3, nome);
            stmt.setString(4, fabricante);
            stmt.setInt(5, idProduto);

            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editarCategoriaDeProduto(Produto produto) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE categoria_prod SET  "
                    + "nome_categoria_prod = ? "
                    + "WHERE pk_idcategoria_prod = ? ";
            
            String nomeCategoria = produto.getCategoria();
            int idCategoriaProduto = produto.getIdCategoriaProduto();

            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, nomeCategoria);
            stmt.setInt(2, idCategoriaProduto);

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
