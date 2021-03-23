package org.acme.getting.started.db;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrientDbService {

    private ODatabaseSession db;

    private OrientDbSchemeCreator orientDbSchemeCreator;

    public OrientDbService(ODatabaseSession db, OrientDbSchemeCreator orientDbSchemeCreator) {
        this.db = db;
        this.orientDbSchemeCreator = orientDbSchemeCreator;
        this.db.activateOnCurrentThread();
    }

    public OVertex addAuthor(String firstName) {
        OVertex vAuthor = db.newVertex("Author");
        vAuthor.setProperty("firstName", firstName);
        vAuthor.save();
        return vAuthor;
    }

    public OVertex addEditor(String firstName) {
        OVertex vEditor = db.newVertex("Editor");
        vEditor.setProperty("firstName", firstName);
        vEditor.save();
        return vEditor;
    }

    public OVertex addArticle(String title) {
     //   String query = "SELECT * from Article where name = ?";
        OVertex vArticle = db.newVertex("Article");
        vArticle.setProperty("title", title);
        vArticle.save();
        return vArticle;
    }

    public OEdge addRelateHas(OVertex vAuthor, OVertex vEditor) {
        OEdge edgeHas = db.newEdge(vAuthor, vEditor, "has");
        edgeHas.save();
        return edgeHas;
    }

    public OEdge addRelateWrote(OVertex vAuthor, OVertex vArticle) {
        OEdge edgeWrote = db.newEdge(vAuthor, vArticle, "wrote");
        edgeWrote.save();
        return edgeWrote;
    }

    public List<String> getArticlesByAuthor(String name) {

        String query = "SELECT expand(out('wrote')) from Author where firstName = ?";
        OResultSet rs = db.query(query, name);

        List<String> result = new ArrayList<>();

        while (rs.hasNext()) {
            OResult item = rs.next();
            String title = item.getProperty("title");
            result.add(title);
        }

        rs.close();
        return result;
    }

}
