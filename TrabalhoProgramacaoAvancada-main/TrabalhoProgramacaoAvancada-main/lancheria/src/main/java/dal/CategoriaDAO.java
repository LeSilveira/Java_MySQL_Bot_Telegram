package dal;

import java.sql.*;
import java.time.Instant;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.lang3.compare.ObjectToStringComparator;

import model.Categoria;
import model.Pedido;
import model.PedidoItem;

/**
 *
 * @author m109740
 */
public class CategoriaDAO {

    /**
     * Consultar Categoria registrada no banco de dados
     *
     * @return lista de categorias
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Categoria> consultar() throws ClassNotFoundException, SQLException {
        ArrayList<Categoria> lst = new ArrayList<>();
        String sql = "SELECT * FROM categoria;";

        //busca resultado do banco de acordo com o conteúdo da variável sql
        ResultSet rs = Conexao.getInstance().consultar(sql);

        //caso tenha informações no banco, popula e retorna a lista de categorias 
        while (rs.next()) {
            Categoria c = new Categoria();
            c.setId(rs.getInt("id"));
            c.setDescricao(rs.getString("descricao"));

            lst.add(c);
        }

        return lst;
    }

    /**
     * Cadastra a Categoria de acordo com o parâmetro descricao no banco de
 dados
     *
     * @param descricao
     * @return true ou false
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean cadastrarCat(String descricao) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO categoria (descricao) VALUES( ?)";

        PreparedStatement st = Conexao.getInstance().preparar(sql);

        st.setString(1, descricao);

        int ret = st.executeUpdate(); //realiza a inserção no banco

        return ret > 0; //salvo com sucesso
    }

    public boolean cadastrarCliente(int id, String nome) throws ClassNotFoundException, SQLException {
        
        String sql = "SELECT nome FROM Cliente WHERE id = " + id;
        PreparedStatement st = Conexao.getInstance().preparar(sql);
        ResultSet rs;
        rs = st.executeQuery(sql);

        if (rs.next() == false){
            sql = "INSERT INTO Cliente (id, nome) VALUES( ?, ?)";
        
            st = Conexao.getInstance().preparar(sql);

            st.setInt(1, id);
            st.setString(2, nome);

            int ret = st.executeUpdate(); //realiza a inserção no banco
            return true; //salvo com sucesso
        }
        return false;
    }

     public ArrayList<ArrayList<Object>> consultaCategoria(String cat) throws ClassNotFoundException, SQLException {
        String sql = "SELECT produto.id, produto.descricao, produto.preco FROM produto WHERE " + Integer.valueOf(cat) + " = produto.categoria_id";
        PreparedStatement st = Conexao.getInstance().preparar(sql);
        ResultSet rs;
        rs = st.executeQuery(sql);
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        int cont = 0;

        while(rs.next()){
            list.add(new ArrayList<Object>());
            list.get(cont).add(rs.getInt("id"));
            list.get(cont).add(rs.getString("descricao"));
            list.get(cont).add(rs.getFloat("preco"));

            cont =+1;
        }
        return list;
    }

    public void inserePedido(int iduser, ArrayList<PedidoItem> itens) throws NumberFormatException, SQLException, ClassNotFoundException{
       
        float preco_total;    
        String sql = "SELECT max(id) FROM pedido";
        PreparedStatement st = Conexao.getInstance().preparar(sql);
        ResultSet rs = st.executeQuery(sql);
        int id_pedido;
        
        if (rs.next() == false)
            id_pedido = 1;
        else
            id_pedido = rs.getInt("max(id)")+1;

        sql = "INSERT INTO pedido (id, cliente_id, data, finalizado, entregue) VALUES(?, ?, ?, 1, 0)";
        st = Conexao.getInstance().preparar(sql);

        st.setInt(1, id_pedido);
        st.setInt(2, Integer.valueOf(iduser));
        st.setTimestamp(3, Timestamp.from(Instant.now()));

        st.executeUpdate();

        for(PedidoItem i : itens)
        {
            sql = "SELECT preco FROM produto WHERE produto.id = " + i.getProduto_id();
            st = Conexao.getInstance().preparar(sql);
            rs = st.executeQuery(sql);

            while (rs.next()){
            
            preco_total = rs.getFloat("preco")*i.getQuantidade();
            sql = "INSERT INTO pedido_item (pedido_id, produto_id, quantidade, preco, observacao) VALUES(?, ?, ?, ?, ?)";
            st = Conexao.getInstance().preparar(sql);
            st.setInt(1, id_pedido);
            st.setInt(2, i.getProduto_id());
            st.setInt(3, i.getQuantidade());
            st.setFloat(4, preco_total);
            st.setString(5, i.getObservacao());

            st.executeUpdate();
           
            }

        }


        st.close();
        rs.close();
    }

    
        public boolean confereExiste(Class classe, ArrayList<PedidoItem> itens, int cont_i){
        boolean retu = false;
            if (classe == PedidoItem.class && itens.size()>=2){
                for (int a2 = 0; a2<itens.size(); a2++){
                    if (itens.get(a2).getProduto_id() == itens.get(cont_i).getProduto_id()){
                        retu = true;
                    }
                }
            }
        return retu;
        }

    /**
     * Deleta a Categoria do banco de acordo com o parâmetro informado
     *
     * @param c
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean deletar(Categoria c) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM categoria WHERE descricao = ?";

        PreparedStatement st = Conexao.getInstance().preparar(sql);

        st.setString(1, c.getDescricao());

        int ret = st.executeUpdate();//realiza a exclusão da Categoria

        return ret > 0; //excluído com sucesso
    }

    /**
     * Altera a Categoria no banco de dados ao informar um novo nome (novaCat)
     *
     * @param c
     * @param novaCat
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean alterar(Categoria c, String novaCat) throws ClassNotFoundException, SQLException {

        String sql = "UPDATE categoria set descricao = ? WHERE descricao = ?";

        PreparedStatement st = Conexao.getInstance().preparar(sql);

        st.setString(1, novaCat);
        st.setString(2, c.getDescricao());

        int ret = st.executeUpdate(); //realiza a alteração no banco

        return ret > 0; //excluído com sucesso
    }
}
