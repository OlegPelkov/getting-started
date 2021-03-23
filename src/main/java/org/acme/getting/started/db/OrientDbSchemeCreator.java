package org.acme.getting.started.db;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import io.quarkus.runtime.Startup;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Startup
@Singleton
public class OrientDbSchemeCreator {

    private static final Logger LOG = Logger.getLogger(OrientDbSchemeCreator.class);

    @Inject
    private ODatabaseSession db;

    public OrientDbSchemeCreator(ODatabaseSession db) {
        this.db = db;
    }

    @PostConstruct
    public void createSchema() {

        OClass article = db.createClassIfNotExist("Article", "V");

        if(!article.existsProperty("title")){
            article.createProperty("title", OType.STRING);
        }

        OClass writerClass = db.createClassIfNotExist("Writer", "V");

        if(!writerClass.isStrictMode()) {
            writerClass.setStrictMode(true);
        }

        if(!writerClass.existsProperty("firstName")){
            writerClass.createProperty("firstName", OType.STRING);
        }

        OClass authorClass = db.createClassIfNotExist("Author", "Writer");
        if(!authorClass.existsProperty("level")) {
            authorClass.createProperty("level", OType.INTEGER).setMax("3");
        }

        OClass editorClass = db.createClassIfNotExist("Editor", "Writer");
        if(!editorClass.existsProperty("level")) {
            editorClass.createProperty("level", OType.INTEGER).setMin("3");
        }

        OClass wroteClass = db.createClassIfNotExist("wrote", "E");
        OClass hasClass = db.createClassIfNotExist("has", "E");
        LOG.info("created Schema");
    }
}
