package org.acme.getting.started.db;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;

import javax.inject.Singleton;

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

}
