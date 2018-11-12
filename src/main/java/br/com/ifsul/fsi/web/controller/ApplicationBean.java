package br.com.ifsul.fsi.web.controller;

import br.com.ifsul.fsi.chatbot.Params;
import br.com.ifsul.fsi.chatbot.TelegramBot;
import br.com.ifsul.fsi.util.Props;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.postgresql.core.Notification;

@ApplicationScoped
@ManagedBean(eager = true)
public class ApplicationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(ApplicationBean.class);

    @PostConstruct
    public void init() {
        try {
            TelegramBot botListen = new TelegramBot(Params.TOKEN_BOT, true);
            botListen.setDaemon(true);
            botListen.start();
            
            TelegramBot botSend = new TelegramBot(Params.TOKEN_BOT, false);
            botSend.setDaemon(true);
            botSend.start();

        } catch (Exception e) {
            logger.error(e);        
        }

        logger.info("ChatBot Server Initialized");
    }

}
