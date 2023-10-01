/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Timestamp; 
/**
 *
 * @author Patrícia
 */
public class Pedido {
    private Integer  id;
    private Integer cliente_id;
    private Timestamp data;
    private boolean finalizado;
    private boolean entregue;

   public Pedido(){}

    public Pedido(Integer cliente_id, Timestamp data, boolean finalizado, boolean entregue) {
        this.cliente_id = cliente_id;
        this.data = data;
        this.finalizado = finalizado;
        this.entregue = entregue;
    }
   

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente() {
        return cliente_id;
    }

    public void setCliente(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }
    
}
