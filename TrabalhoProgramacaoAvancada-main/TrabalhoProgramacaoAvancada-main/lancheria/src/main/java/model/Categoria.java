package model;

/**
 *Classe categoria 
 * @author maria
 */
public class Categoria {
    private Integer id;
    private String descricao;

    public Categoria(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public Categoria(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
