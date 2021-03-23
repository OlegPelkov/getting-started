package org.acme.getting.started;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.OVertex;
import org.acme.getting.started.db.OrientDbService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/data")
public class DataBookController {

    private OrientDbService orientDbService;

    public DataBookController(OrientDbService orientDbService) {
        this.orientDbService = orientDbService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/addArticle/{name}")
    public String addArticle(@PathParam("name") String name) {
        return orientDbService.addArticle(name).toJSON();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/addAuthor/{name}")
    public String addAuthor(@PathParam String name) {
        return orientDbService.addAuthor(name).toJSON();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/addEditor/{name}")
    public String addEditor(@PathParam String name) {
        return orientDbService.addEditor(name).toJSON();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/wrote")
    public String addWrote(@QueryParam("name") String name,  @QueryParam("article") String article) {
        OVertex vAuthor = orientDbService.addAuthor(name);
        OVertex vArticle = orientDbService.addArticle(article);
        return orientDbService.addRelateWrote(vAuthor, vArticle).toJSON();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/articlethisauth")
    public String getArticlesByAuthor(@QueryParam("name") String name) {
        List<String> result = orientDbService.getArticlesByAuthor(name);
        return result.toString();
    }
}