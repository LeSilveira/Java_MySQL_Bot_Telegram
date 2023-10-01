package dal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 * @param <T>
 */
public class DAO<T> {

    private final Class classe;

    public DAO(Class classe) {
        this.classe = classe;
    }

    public boolean salvar(T obj) throws SQLException {
        String sql = "";
        Field[] campos = getCampos();
        Integer pk = null;
        try {
            for (int i = 0; i < campos.length; i++) {
                Field c = campos[i];

                String nome = c.getName();
                Object valor = c.get(obj);
                if (nome.equals("id")) {
                    if (valor == null) {//significa que ainda não foi inserido                       
                        sql = "INSERT INTO " + classe.getSimpleName() + " (";
                    } else {
                        pk = (int) valor;
                        sql = "UPDATE " + classe.getSimpleName() + " SET ";
                    }
                } else {
                    if (pk == null)//é insert
                    {
                        sql += nome;
                    } else//é um update
                    {
                        sql += nome + "=?";
                    }
                    if (i < campos.length - 1) {
                        sql += ", ";//último campo não precisa                    
                    }
                }
            }
            if (pk == null)//terminar de formatar o insert
            {
                sql += ", id) VALUES (" + repeat("?, ", campos.length).substring(0, (campos.length * 3) - 2) + ")";
            } else//terminar de formatar o update
            {
                sql += "  WHERE id=?";
            }

            PreparedStatement st = Conexao.getInstance().preparar(sql);
            int cont = 1;
            for (Field c : campos) {
                String nome = c.getName();

                if (!nome.equals("id")) {
                    Object valor = c.get(obj);
                    st.setObject(cont, valor);

                    cont++;
                }

            }
            st.setObject(cont, pk);
            int ret = st.executeUpdate();
            return ret > 0;
        } catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
        public boolean excluir(Integer id) throws SQLException {
        try {
            String sql = "";
            
            sql = "DELETE FROM " + classe.getSimpleName() + " WHERE id = ? ";
                        
            PreparedStatement st = Conexao.getInstance().preparar(sql);
            
            st.setInt(1, id);
            
            int ret = st.executeUpdate();
            if (ret > 0){
                return true;
            }
               
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<T> consultar() throws SQLException, ClassNotFoundException {
        return consultar(null);
    }

    /*
    // obter a lista de campos da classe e tornando-os acessíveis
     */
    private Field[] getCampos() {
        Field[] fields = classe.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
        return fields;
    }

    /**
     * Retorna o objeto referente a PK
     *
     * @param id
     * @return
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public T consultarId(Integer id) throws SQLException, ClassNotFoundException {
        return consultar("WHERE id=" + id).get(0);
    }

    public List<T> consultar(String where) throws SQLException, ClassNotFoundException {

        List<T> ret = new ArrayList<>();

        Field[] fields = getCampos();
        String sql = "SELECT * FROM " + classe.getSimpleName();
        if (where != null) {
            sql += " WHERE " + where;
        }

        ResultSet rs = Conexao.getInstance().preparar(sql).executeQuery();
        // setar os valores do resultset
        while (rs.next()) {
            try {
                Object obj = classe.getDeclaredConstructor().newInstance();

                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = rs.getObject(fieldName);
                    if (value != null) {
                        if (field.getType() == String.class) {
                            value = rs.getString(fieldName);
                        } else if (field.getType() == int.class) {
                            value = rs.getInt(fieldName);
                        } else if (field.getType() == float.class) {
                            value = rs.getFloat(fieldName);
                        } else if (field.getType() == double.class) {
                            value = rs.getDouble(fieldName);
                        } else if (field.getType() == boolean.class) {
                            value = rs.getBoolean(fieldName);
                        } else if (field.getType() == java.util.Date.class) {
                            value = rs.getDate(fieldName);
                        }
                    }
                    field.set(obj, value);
                }
                ret.add((T) obj);

            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    public List<T> consultar(String select, String where) throws SQLException, ClassNotFoundException {

        List<T> ret = new ArrayList<>();

        Field[] fields = getCampos();

        if (select == null) {
            select = "*";
        }
        String sql = "SELECT " + select + " FROM " + classe.getSimpleName();
        if (where != null) {
            sql += " WHERE " + where;
        }

        ResultSet rs = Conexao.getInstance().preparar(sql).executeQuery();
        // setar os valores do resultset
        while (rs.next()) {
            try {
                Object obj = classe.getDeclaredConstructor().newInstance();

                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = rs.getObject(fieldName);
                    if (value != null) {
                        if (field.getType() == String.class) {
                            value = rs.getString(fieldName);
                        } else if (field.getType() == int.class) {
                            value = rs.getInt(fieldName);
                        } else if (field.getType() == float.class) {
                            value = rs.getFloat(fieldName);
                        } else if (field.getType() == double.class) {
                            value = rs.getDouble(fieldName);
                        } else if (field.getType() == boolean.class) {
                            value = rs.getBoolean(fieldName);
                        } else if (field.getType() == java.util.Date.class) {
                            value = rs.getDate(fieldName);
                        }
                    }
                    field.set(obj, value);
                }
                ret.add((T) obj);

            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }



    private String repeat(String str, int cont) {
        String ret = "";
        for (int i = 0; i < cont; i++) {
            ret += str;
        }
        return ret;
    }

    /**
     * Consultar com sql Injecton devido tratamento de palavras com aspas
     * simples
     *
     * @param obj
     * @param select
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.InstantiationException
     */
    public List<T> consultarT(T obj, String select) throws SQLException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        String sql = "";
        ResultSet rs = null;

        Field[] fields = getCampos();

        List<T> ret = new ArrayList<>();
        Field[] campos = getCampos();
        for (Field campo : campos) {
            try {
                Field c = campo;
                String nome = c.getName();
                Object valor = c.get(obj);
                if (select != null) //possui dados
                {
                    sql = "SELECT " + select + "FROM " + classe.getSimpleName();
                } else {
                    sql = "SELECT * FROM " + classe.getSimpleName();
                }
                if (nome != null && valor != null) {
                    sql += nome + "=" + repeat("?, ", campos.length).substring(0, (campos.length * 3) - 2);
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        PreparedStatement st = Conexao.getInstance().preparar(sql);
        int cont = 1;
        for (Field c : campos) {

            Object valor = c.get(obj);
            if (valor != null) {
                st.setObject(cont, valor);

                cont++;
            }
            rs = st.executeQuery();
        }
        // setar os valores do resultset
        while (rs.next()) {
            try {
                Object objs = classe.getDeclaredConstructor().newInstance();

                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = rs.getObject(fieldName);
                    if (value != null) {
                        if (field.getType() == String.class) {
                            value = rs.getString(fieldName);
                        } else if (field.getType() == int.class) {
                            value = rs.getInt(fieldName);
                        } else if (field.getType() == float.class) {
                            value = rs.getFloat(fieldName);
                        } else if (field.getType() == double.class) {
                            value = rs.getDouble(fieldName);
                        } else if (field.getType() == boolean.class) {
                            value = rs.getBoolean(fieldName);
                        } else if (field.getType() == java.util.Date.class) {
                            value = rs.getDate(fieldName);
                        }
                    }
                    field.set(objs, value);
                }
                ret.add((T) objs);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
}
