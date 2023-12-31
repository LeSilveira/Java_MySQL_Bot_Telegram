/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dal.DAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Auxiliar;
import model.Cliente;
import model.Pedido;
import model.PedidoItem;
import model.Produto;
import model.RelatorioPedidos;

/**
 *
 * @author Patrícia
 */
public class FrmListaPedidos extends javax.swing.JFrame {



    /**
     * Creates new form FrmListaPedidos
     */
    public FrmListaPedidos() {
        initComponents();
    }

        /**
     * Atualiza tabela que lista pedidos a serem entregues
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public void preencheTabPedidos() throws IOException, FileNotFoundException, ClassNotFoundException {

        try {
            DefaultTableModel modelo = (DefaultTableModel) tb_pedidos.getModel();

            modelo.setNumRows(0);

            DAO<Pedido> daop = new DAO<>(Pedido.class);
             DAO<Cliente> daoCli = new DAO<>(Cliente.class);


            Auxiliar a = new Auxiliar();
            
            for (Pedido p : daop.consultar("finalizado = 1")) {
                for (Cliente c : daoCli.consultar("id =" + p.getCliente())) {
                    
                    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy" + " HH:mm");

                    modelo.addRow(new Object[]{
                        p.getId(),
                        c.getNome(),
                        sd.format(p.getData()),
                        NumberFormat.getCurrencyInstance().format(a.valorTotalPedido(p.getId()))
                    });
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FrmListaProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    /**
     * Atualiza tabela que lista itens do pedido selecionado
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public void preencheTabItem() throws IOException, FileNotFoundException, ClassNotFoundException {
        if (tb_pedidos.getSelectedRow() != -1) {
            try {
                DefaultTableModel modelo = (DefaultTableModel) tb_itens.getModel();

                modelo.setNumRows(0);

                DAO<PedidoItem> daoItem = new DAO<>(PedidoItem.class);
                DAO<Produto> daoP = new DAO<>(Produto.class);


                for (PedidoItem p : daoItem.consultar("pedido_id =" + tb_pedidos.getValueAt(tb_pedidos.getSelectedRow(), 0))) {
                      for (Produto pr : daoP.consultar("id =" +  p.getProduto_id())) {
                        modelo.addRow(new Object[]{
                            p.getProduto_id(),
                            pr.getDescricao(),
                            p.getObservacao(),
                            p.getQuantidade(),
                            NumberFormat.getCurrencyInstance().format(p.getPreco()),
                            NumberFormat.getCurrencyInstance().format(p.getQuantidade() * p.getPreco())
                        });
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmListaProduto.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
                JOptionPane.showMessageDialog(this, "Selecione um pedido", null, JOptionPane.WARNING_MESSAGE, null);

        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        bntAtualizar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_pedidos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_itens = new javax.swing.JTable();
        btnRelatorio = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Consulta de Pedidos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        bntAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button-Refresh-icon.png"))); // NOI18N
        bntAtualizar.setText("Atualizar Pedidos");
        bntAtualizar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button-Refresh-icon.png"))); // NOI18N
        bntAtualizar.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button-Refresh-icon.png"))); // NOI18N
        bntAtualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bntAtualizar.setIconTextGap(5);
        bntAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAtualizarActionPerformed(evt);
            }
        });

        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Accept-icon.png"))); // NOI18N
        btnFinalizar.setText("Finalizar Entrega");
        btnFinalizar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Accept-icon.png"))); // NOI18N
        btnFinalizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnFinalizar.setIconTextGap(5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedidos a serem entregues"));

        tb_pedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Pedido", "Cliente", "Data/Hora", "Valor Total"
            }
        ));
        tb_pedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_pedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_pedidos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 212, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Itens do Pedido Selecionado"));

        tb_itens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód. Produto", "Descrição", "Observação", "Qtde", "Vl.Unit.", "Vl.Total"
            }
        ));
        jScrollPane2.setViewportView(tb_itens);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Accept-icon.png"))); // NOI18N
        btnRelatorio.setText("Gerar relatório");
        btnRelatorio.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Accept-icon.png"))); // NOI18N
        btnRelatorio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRelatorio.setIconTextGap(5);
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });

        jButton1.setText("Configurar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bntAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(29, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(53, 53, 53)
                        .addComponent(bntAtualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFinalizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRelatorio))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAtualizarActionPerformed
        try {
            preencheTabPedidos();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FrmListaPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bntAtualizarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            preencheTabPedidos(); //preenche tabela de pedidos a serem entregues
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FrmListaPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void tb_pedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_pedidosMouseClicked
        try {
            preencheTabItem();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FrmListaPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_pedidosMouseClicked

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        try {
            DAO<Pedido> daop = new DAO<>(Pedido.class);
            
            for (Pedido p : daop.consultar("finalizado = 1")) {
                
                RelatorioPedidos rp = new RelatorioPedidos(p);
                rp.gerarCabecalho();
                rp.gerarCorpo();
                rp.gerarRodape();
                rp.imprimir();
                
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FrmListaPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_btnRelatorioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmListaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmListaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmListaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmListaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmListaPedidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAtualizar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tb_itens;
    private javax.swing.JTable tb_pedidos;
    // End of variables declaration//GEN-END:variables
}
