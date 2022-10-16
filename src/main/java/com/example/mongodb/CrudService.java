package com.example.mongodb;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Service
public class CrudService {

    MongoCollection<Document> usuariosCollection;


    @Autowired
    public CrudService(MongoLocalClient mongoLocalClient) {
        this.usuariosCollection = mongoLocalClient.getUsuariosCollection();
    }

    public String getUsuarios(String nombre) {
        Bson projectionFields = Projections.fields(Projections.excludeId());

        MongoCursor<Document> cursor = usuariosCollection.find(eq("nombre", nombre))
                .projection(projectionFields)
                .sort(Sorts.ascending("nombre"))
                .iterator();

        List<String> response = new ArrayList<>();

        try {
            while(cursor.hasNext()) {
                response.add(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        return response.toString();
    }


}
