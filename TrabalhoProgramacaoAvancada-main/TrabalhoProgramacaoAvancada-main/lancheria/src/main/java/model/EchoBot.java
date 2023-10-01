package model;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.*;

import dal.CategoriaDAO;

public class EchoBot  extends TelegramLongPollingBot {
    String respUser;

    int menuselect = 0;
    int cont_itens = 0;

    boolean end = false;
    boolean confereExiste;

    CategoriaDAO auxBD = new CategoriaDAO();
    ArrayList<PedidoItem> pedidos_itens = new ArrayList<>();
    

    
    @Override
    public String getBotUsername() {
        return DadosBot.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return DadosBot.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                var mensagem = responder(update);
                execute(mensagem);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TelegramApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }
    }

    private SendMessage responder(Update update) throws ClassNotFoundException, SQLException, ParseException {

        var chatId = update.getMessage().getChatId().toString();
        var iduser = update.getMessage().getChatId();
        var nome = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
        var resposta = " ";
        

        switch(menuselect)
        {
            
            case 0:
            if (auxBD.cadastrarCliente(iduser.intValue(), nome)){
                resposta = "Olá, escolha uma das categorias de lanche abaixo para dar uma olhada:\n[1] Hamburger\n[2] Pizza\n[3] Pastel\n[4] Sushi";
            }else{
                resposta = "Identificamos que você já está cadastrado, escolha uma das categorias de lanche abaixo para dar uma olhada:\n[1] Hamburger\n[2] Pizza\n[3] Pastel\n[4] Sushi";
            }

            menuselect = 1;
            break;

            case 1:
            respUser = update.getMessage().getText().toString();

            if (respUser.length() == 1){
            try {

                    ArrayList<ArrayList<Object>> lista = auxBD.consultaCategoria(respUser);
                    resposta = "Certo, aqui estão as opções desta categoria:\nID      Descrição      Preço";

                    for (ArrayList<Object> i : lista){
                        resposta = resposta + "\n["+ i.get(0) +"]  -  " + i.get(1) + "   -    R$ " + i.get(2);
                    }
                    
                    resposta = resposta + "\n\nPor favor, selecione uma opção.";

                    menuselect = 2;

                } catch (ClassNotFoundException e) {
                    resposta = "Erro";
                    e.printStackTrace();

                } catch (SQLException e) {
                    resposta = "Erro SQL";
                    e.printStackTrace();
                }
            }
            
            else{
                resposta = "Por favor, insira uma opção válida.";
            }

            break;

            case 2:

            
            respUser = update.getMessage().getText();
            pedidos_itens.add(new PedidoItem());
            pedidos_itens.get(cont_itens).setProduto_id(Integer.valueOf(respUser));;

            confereExiste = auxBD.confereExiste(pedidos_itens.get(cont_itens).getClass(), pedidos_itens, cont_itens);     

            if (confereExiste){
                resposta = "Identificamos que você já tem esse produto em seu pedido, gostaria de adicionar mais?\n[1] Sim\n[2] Não";
                
                System.out.println(confereExiste);
            }else{
                System.out.println(confereExiste);
            resposta = "Certo, digite a quantidade que deseja:";
            }

            menuselect = 3;
            break;

            case 3:

            respUser = update.getMessage().getText();
            pedidos_itens.get(cont_itens).setQuantidade(Integer.valueOf(respUser));
            resposta = "Se tiver alguma observação pode digitar, caso não tenha, apenas digite '1'.";

            menuselect = 4;
            break;

            case 4:
            
            respUser = update.getMessage().getText();
           
            if (respUser.toLowerCase().equals("1") || respUser.toLowerCase().equals("um")){
                pedidos_itens.get(cont_itens).setObservacao("Sem observação");
            }
            else{
                pedidos_itens.get(cont_itens).setObservacao(respUser);
            }
            resposta = "Deseja adicionar mais algum produto ao pedido?\n[1]Sim\n[2]Não";
            menuselect = 5;
            break;

            case 5:

            respUser = update.getMessage().getText().toLowerCase();
            if (respUser.toLowerCase().equals("sim")  || respUser.toLowerCase().equals("s") || respUser.toLowerCase().equals("quero") || respUser.toLowerCase().equals("1") 
                || respUser.toLowerCase().equals("quero")){
                menuselect = 1;
                cont_itens+=1;
                resposta = "Certo, vamos voltar à seleção de produtos.\n\n" + 
                        "[1] Hambúrger\n" +
                        "[2] Pizza\n" +
                        "[3] Pastel\n" +
                        "[4] Sushi";
                break;
            }


            if (respUser.toLowerCase().equals("não") || respUser.toLowerCase().equals("n") || respUser.toLowerCase().equals("não quero") ||
                 respUser.toLowerCase().equals("nao quero") || respUser.toLowerCase().equals("2") || respUser.toLowerCase().equals("nao"))
            {
                auxBD.inserePedido(iduser.intValue(), pedidos_itens);
                resposta = "Certo, pedido finalizado, em 40 minutos pode retirar!";
                end = true;
                break;
            }

            else{
                resposta = ("Por favor, insira uma resposta válida");
            }
        }

        if (end){
            menuselect = 0;  
            end = false;
        }
        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }

 

    private java.sql.Timestamp getData() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parsedDate = formatter.parse(formatter.toString());
        java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        return timestamp;
    }


}
