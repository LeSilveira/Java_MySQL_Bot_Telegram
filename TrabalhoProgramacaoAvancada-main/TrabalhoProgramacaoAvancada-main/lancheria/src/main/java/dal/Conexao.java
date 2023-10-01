package dal;

import java.sql.*;

/**
 *
 * @author m109740
 */
public class Conexao {

    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost/" + "lancheria";
    private final String usuario = "root";
    private final String senha = "vx81GNZU@";//informe a sua senha
    private Connection conexao;

    //construtor privado
    private Conexao() throws ClassNotFoundException, SQLException { //quem chamou
        Class.forName(driver);//carrega o driver
        //abre a conexao
        conexao = DriverManager.getConnection(url, usuario, senha);
    }
    //padrao de projeto singleton
    private static Conexao instancia = null;

    public static Conexao getInstance() throws ClassNotFoundException, SQLException {
        if (instancia == null) {
            instancia = new Conexao();
        }
        return instancia;
    }

    public PreparedStatement preparar(String sql) throws SQLException {
        return conexao.prepareStatement(sql);
    }

    public ResultSet consultar(String sql) throws SQLException {
        PreparedStatement st = preparar(sql);
        ResultSet rs = st.executeQuery();
        return rs;
    }
}
