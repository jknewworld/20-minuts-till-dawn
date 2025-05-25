package com.P3.Model.Repo;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class Connection {
    private static Datastore database;

    public static Datastore getDatabase() {
        if (database == null) {
            try {
                String DB_URI = System.getProperty("DB_URI", "mongodb://localhost:27017");
                String DB = System.getProperty("DB_NAME", "test");
                database = Morphia.createDatastore(MongoClients.create(DB_URI), DB);
                database.getMapper().mapPackage("model");
                database.ensureIndexes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return database;
    }

}
