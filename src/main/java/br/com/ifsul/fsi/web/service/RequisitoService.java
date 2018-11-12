package br.com.ifsul.fsi.web.service;

import br.com.ifsul.fsi.web.model.dao.RequisitoDAO;
import br.com.ifsul.fsi.web.model.entity.Requisito;
import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONObject;
import org.slf4j.helpers.Util;

@Path("/requisito")
public class RequisitoService implements Serializable {

    final static Logger logger = Logger.getLogger(RequisitoService.class);

    private static final long serialVersionUID = 1L;

    @Path("/all")
    @GET
    @Produces("application/json; charset=utf-8")
    public Response getAll() throws JSONException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        List<Requisito> list = RequisitoDAO.getInstance().getAll(Requisito.class);
        String result = gson.toJson(list);

        logger.debug("getAll - sending: " + result);

        return Response.status(200).entity(result).build();
    }

    @Path("/insert")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response insert(String json) {
        logger.info("insert - receiving : " + json);
        JSONObject jsonObject = new JSONObject(json);
        String username = (String) jsonObject.get("username");
        String log = (String) jsonObject.get("log");
        Integer diag = (Integer) jsonObject.get("diag");

        byte[] logDecoded = DatatypeConverter.parseBase64Binary(log);
        String logDecodedStr = new String(logDecoded);

       
        return Response.status(200).entity("OK").build();
    }
}
