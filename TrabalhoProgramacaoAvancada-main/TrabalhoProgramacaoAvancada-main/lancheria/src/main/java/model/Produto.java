/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author maria
 */
public class Produto {
    private Integer  id;
    private Integer categoria_id;
    private String descricao;
    private Double preco;

    public Produto(Integer id, Integer categoria_id, String descricao, Double preco) {
        this.id = id;
        this.categoria_id = categoria_id;
        this.descricao = descricao;
        this.preco = preco;
    }
public Produto(){
    
}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Integer categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "produto{" + "id=" + id + ", categoria_id=" + categoria_id + ", descricao=" + descricao + ", preco=" + preco + '}';
    }
    
}
