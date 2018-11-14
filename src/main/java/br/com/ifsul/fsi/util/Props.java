package br.com.ifsul.fsi.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Props implements Serializable {

    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(Props.class);

    private static Props INSTANCE;

    private Properties props;

    private Props() throws Exception {
        carregar();
    }

    public synchronized static Props getInstance() throws Exception {
        if (INSTANCE == null) {
            INSTANCE = new Props();
        }
        return INSTANCE;
    }

    public String getConfig(String chave) {
        return props.getProperty(chave);
    }

    private void carregar() throws Exception {
        try {
            logger.debug("Carregando properties");
            props = new Properties();
            InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
            props.load(in);
            // in.close();

        } catch (Exception e) {
            throw e;
        }
    }
}
