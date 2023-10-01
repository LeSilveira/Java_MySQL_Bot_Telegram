/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dal.DAO;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author maria
 */
public class RelatorioPedidos {
    private Pedido pedido;
    private Document documentoPDF;
    private String caminhoRelatorio = "Relatorio.pdf";

    public RelatorioPedidos(Pedido pedido) {
        this.pedido = pedido;
        this.documentoPDF = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter.getInstance(this.documentoPDF, new FileOutputStream(caminhoRelatorio));
            this.adicionarPaginacao();
            this.documentoPDF.open();
        } catch (DocumentException | FileNotFoundException e) {
        }
    }

    public void gerarCabecalho() {
        this.adicionarImagem("entrega.png");
        this.adicionarParagrafoTitulo();
        this.pularLinha();
        this.adicionarDadosCliente();
        this.pularLinha();
        this.adicionarQuebraDeSessao();
    }

    public void gerarCorpo() {
        this.adicionarParagrafoItensVendidosTitulo();
        PdfPTable tableProdutos = this.criarTabelaComCabecalho();
        this.adicionarProdutosATabela(tableProdutos);
        this.documentoPDF.add(tableProdutos);
        this.pularLinha();
        this.adicionarTotalDaVenda();
    }

    
    public void gerarRodape() {
        this.adicionarQuebraDeSessao();
        this.pularLinha();
        this.adicionarRodaPe();
    }

   
    public void imprimir() {
        if (this.documentoPDF != null && this.documentoPDF.isOpen()) {
            documentoPDF.close();
        }
    }

    private void adicionarPaginacao() {
        HeaderFooter paginacao = new HeaderFooter(new Phrase("Pág.", new Font(Font.BOLD)), true);
        paginacao.setAlignment(Element.ALIGN_RIGHT);
        paginacao.setBorder(Rectangle.NO_BORDER);
        documentoPDF.setHeader(paginacao);
    }

    private void adicionarDadosCliente() {
        Chunk chunkDataPedido = new Chunk();
        chunkDataPedido.append("Pedido: " + this.pedido.getId());
        chunkDataPedido.append(this.criarDataFormatada());

        Paragraph paragrafoDataPedido = new Paragraph();
        paragrafoDataPedido.add(chunkDataPedido);
        this.documentoPDF.add(paragrafoDataPedido);
    }

    private void adicionarQuebraDeSessao() {
        Paragraph paragrafoSessao = new Paragraph("__________________________________________________________");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentoPDF.add(paragrafoSessao);
    }

    private void adicionarParagrafoTitulo() {
        Paragraph paragrafoTitulo = new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        Chunk cTitulo = new Chunk("RELATÓRIO DE PEDIDOS");
        cTitulo.setFont(new Font(Font.COURIER, 24));
        cTitulo.setBackground(Color.lightGray, 2, 2, 2, 2);
        paragrafoTitulo.add(cTitulo);
        documentoPDF.add(paragrafoTitulo);
    }

    private void adicionarImagem(String caminhoImagem) {
        Image imgTitulo = null;
        try {
            imgTitulo = Image.getInstance(caminhoImagem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IMG Não encontrada", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        if (imgTitulo != null) {
            imgTitulo.setAlignment(Element.ALIGN_CENTER);
            this.documentoPDF.add(imgTitulo);
        }
    }

    private void pularLinha() {
        this.documentoPDF.add(new Paragraph(" "));
    }

    private String criarDataFormatada() {
        StringBuilder dataVenda = new StringBuilder();
        dataVenda.append(" - Data do pedido: ");
        dataVenda.append(this.pedido.getData());
        return dataVenda.toString();
    }

    private PdfPTable criarTabelaComCabecalho() {
        // tabela com 4 colunas
        PdfPTable tableProdutos = new PdfPTable(4);
        tableProdutos.setWidthPercentage(98);
        tableProdutos.setWidths(new float[] { 2f, 1f, 1f, 1f });

        PdfPCell celulaTitulo = new PdfPCell(new Phrase("PRODUTO"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("QUANTIDADE"));
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("VALOR UNI."));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("VALOR TOTAL"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        return tableProdutos;
    }

    private void adicionarProdutosATabela(PdfPTable tableProdutos) {
        try {
            int contador = 1;
            for (PedidoItem item : new DAO<PedidoItem>(PedidoItem.class).consultar("pedido_id = " + this.pedido.getId())) {
                
                PdfPCell celulaNome = new PdfPCell(new Phrase(item.getProduto_id()));
                PdfPCell celulaQuantidade = new PdfPCell(new Phrase(String.valueOf(item.getQuantidade())));
                celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celulaValor = new PdfPCell(new Phrase("R$ " + String.valueOf(item.getPreco())));
                celulaValor.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celulaTotalUnit = new PdfPCell(new Phrase("R$ " + String.valueOf(item.getPreco() * item.getQuantidade())));
                celulaTotalUnit.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                if (contador % 2 == 0) {
                    celulaNome.setBackgroundColor(Color.LIGHT_GRAY);
                    celulaQuantidade.setBackgroundColor(Color.LIGHT_GRAY);
                    celulaValor.setBackgroundColor(Color.LIGHT_GRAY);
                    celulaTotalUnit.setBackgroundColor(Color.LIGHT_GRAY);
                }
                
                tableProdutos.addCell(celulaNome);
                tableProdutos.addCell(celulaQuantidade);
                tableProdutos.addCell(celulaValor);
                tableProdutos.addCell(celulaTotalUnit);
                
                contador++;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RelatorioPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void adicionarTotalDaVenda() {
     Auxiliar a = new Auxiliar();
        Paragraph pTotal = new Paragraph();
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        pTotal.add(new Chunk("Total do pedido: R$ " + a.valorTotalPedido(this.pedido.getId()),
                new Font(Font.TIMES_ROMAN, 20)));
        this.documentoPDF.add(pTotal);
    }

    private void adicionarParagrafoItensVendidosTitulo() {
        Paragraph pItens = new Paragraph();
        pItens.setAlignment(Element.ALIGN_CENTER);
        pItens.add(new Chunk("ITENS ", new Font(Font.TIMES_ROMAN, 16)));
        documentoPDF.add(new Paragraph(pItens));
        documentoPDF.add(new Paragraph(" "));
    }

    private void adicionarRodaPe() {
        Paragraph pRodape = new Paragraph();
        pRodape.setAlignment(Element.ALIGN_CENTER);
        pRodape.add(new Chunk("www.blsoft.com.br/like", new Font(Font.TIMES_ROMAN, 14)));
        this.documentoPDF.add(pRodape);
    }
}
