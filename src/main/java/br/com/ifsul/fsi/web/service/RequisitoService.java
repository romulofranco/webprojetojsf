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
        Gson gson = new Gson();
        Requisito requisito = gson.fromJson(json, Requisito.class);
        
        return Response.status(200).entity("OK").build();
    }
}
