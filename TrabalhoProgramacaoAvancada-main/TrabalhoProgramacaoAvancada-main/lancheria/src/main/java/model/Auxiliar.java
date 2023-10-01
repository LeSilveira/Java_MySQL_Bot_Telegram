/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dal.DAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class Auxiliar {
    
                DAO<PedidoItem> daoItem = new DAO<>(PedidoItem.class);
                Double soma = 0.00;

                public Double valorTotalPedido(Integer id){
                    try {
                        for (PedidoItem i : daoItem.consultar("pedido_id = " + id)) {
                            soma += i.getPreco() * i.getQuantidade();
                            
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Auxiliar.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Auxiliar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return soma;
                }
}
