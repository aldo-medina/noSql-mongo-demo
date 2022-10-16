package com.example.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoLocalClient {

    @Value("${mongo.uri}")
    String uri;

    public MongoCollection<Document> getUsuariosCollection() {
        MongoClient mongoClient = MongoClients.create(this.uri);
        MongoDatabase database = mongoClient.getDatabase("noSql");
        return database.getCollection("usuarios");
    }

}
