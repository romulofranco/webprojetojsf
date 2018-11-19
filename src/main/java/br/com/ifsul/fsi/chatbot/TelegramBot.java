package br.com.ifsul.fsi.chatbot;

import br.com.ifsul.fsi.web.model.dao.RequisitoDAO;
import br.com.ifsul.fsi.web.model.entity.Requisito;
import com.ibm.watson.developer_cloud.language_translator.v2.util.Language;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author romulo
 */
public class TelegramBot extends Thread {

    private static final boolean CHATBOT_SERVICE = true;

    private final static Logger logger = Logger.getLogger(TelegramBot.class.getName());

    private final String endpointTelegram = "https://api.telegram.org/";
    private final String endpointService = "http://localhost:2020/WebProjeto/service";

    private final String token;
    private boolean botListen = false;

    private Chatbot bot;

    private String lastMessage;

    public TelegramBot(String token, boolean listen) {
        this.token = token;
        this.botListen = listen;
        this.bot = new Chatbot();
    }

    public HttpResponse<JsonNode> sendMessage(Integer chatId, String text) throws UnirestException {
        logger.info("Enviando msg: " + chatId + " Msg: " + text);
        return Unirest.post(endpointTelegram + "bot" + token + "/sendMessage").field("chat_id", chatId).field("text", text)
                .asJson();
    }

    public String insertRequisito(Requisito req) {
        try {
            logger.info("Inserindo requisito " + req.getJson());
            HttpResponse<JsonNode> response = Unirest.post(endpointService + "/requisito/insert")
                    .header("Content-Type", "application/json")
                    .header("accept", "text/plain")
                    .body(req.getJson())
                    .asJson();

            return response.getStatusText();
        } catch (UnirestException e) {
            return "Falha ao inserir requisito " + e.getMessage();
        }
    }

    public HttpResponse<JsonNode> getUpdates(Integer offset) throws UnirestException {
        return Unirest.post(endpointTelegram + "bot" + token + "/getUpdates").field("offset", offset).asJson();
    }

    @Override
    public void run() {

        if (botListen) {
            logger.info("Bot preparado para receber msgs!");
            recebendoMsgs();
        } else {

        }

    }

    private void recebendoMsgs() {
        try {
            int last_update_id = 0; // controle das mensagens processadas
            HttpResponse<JsonNode> response;
            while (true) {
                response = getUpdates(last_update_id++);
                if (response.getStatus() == 200) {
                    JSONArray responses = response.getBody().getObject().getJSONArray("result");
                    if (responses.isNull(0)) {
                        continue;
                    } else {
                        last_update_id = responses.getJSONObject(responses.length() - 1).getInt("update_id") + 1;
                    }

                    for (int i = 0; i < responses.length(); i++) {
                        try {
                            JSONObject message = responses.getJSONObject(i).getJSONObject("message");
                            int chatID = message.getJSONObject("chat").getInt("id");
//                            String usuario = message.getJSONObject("chat").getString("username");
                            String texto = message.getString("text");

                            logger.info("Msg recebida: " + texto);

                            if (texto.equalsIgnoreCase("/ultimamsg")) {
                                sendMessage(chatID, this.lastMessage);
                            } else if (texto.equalsIgnoreCase("/analise")) {
                                WatsonHelper wh = new WatsonHelper();
                                String textEnglish = wh.getTranslation(this.lastMessage, Language.PORTUGUESE, Language.ENGLISH);
                                logger.info("Texto traduzido " + textEnglish);
                                String nlu = wh.getNLUAnalysis(textEnglish);

                                List<ToneScore> tones = wh.getToneAnalyzer(textEnglish);
                                String tone1 = "";
                                Double tone1Val = new Double(0);
                                if (tones.size() > 0) {
                                    if (tones.get(0) != null) {
                                        ToneScore tone = (ToneScore) tones.get(0);
                                        tone1 = tone.getToneName();
                                        tone1Val = tone.getScore();
                                    }
                                }

                                Requisito req = new Requisito("", nlu, tones.toString(), this.lastMessage, nlu, tone1, tone1Val);

                                if (TelegramBot.CHATBOT_SERVICE) {
                                    String responseStatus = this.insertRequisito(req);
                                    logger.info("Response " + responseStatus);
                                } else {
                                    req = new Requisito("", nlu, tones.toString(), this.lastMessage, nlu, tone1, tone1Val);
                                    RequisitoDAO.getInstance().insert(req);
                                }
                                sendMessage(chatID, "Ultima msg analisada: " + this.lastMessage + "\n" + nlu + "\n Requisito enviado para a base");

                            } else if (texto.equalsIgnoreCase("/teste")) {
                                sendMessage(chatID, "Parece que esse bot está funcionando...");
                            } else {
                                String msg = bot.respostaInteligente(texto);
                                sendMessage(chatID, msg);
                                this.lastMessage = texto;
                            }

                        } catch (Exception e) {
                            Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, e);
                        }

                    }
                }
            }
        } catch (UnirestException e) {
            Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
