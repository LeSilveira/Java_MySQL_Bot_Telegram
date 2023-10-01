/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Patrícia
 */
public class PedidoItem {
    private Integer pedido_id;
    private Integer produto_id;
    private Integer quantidade;
    private Double preco;
    private String observacao;

    public PedidoItem(Integer pedido_id, Integer produto_id, Integer quantidade, Double preco, String observacao) {
        this.pedido_id = pedido_id;
        this.produto_id = produto_id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.observacao = observacao;
    }

    public PedidoItem(){
        
    }

    public Integer getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
    }

    public Integer getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Integer produto_id) {
        this.produto_id = produto_id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    
}
