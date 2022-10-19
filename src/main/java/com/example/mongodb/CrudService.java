package com.example.mongodb;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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


    public void addUsuario(Usuario usuario) throws JsonProcessingException {
        //1st Approach
        Document newDoc = new Document("nombre", usuario.getNombre())
                .append("apellido", usuario.getApellido())
                .append("edad", usuario.getEdad())
                .append("sexo", usuario.getSexo())
                .append("lenguajesAprendidos", usuario.getLenguajesAprendidos());
        usuariosCollection.insertOne(newDoc);

        //2nd Approach
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(usuario);
//        usuariosCollection.insertOne(Document.parse(json));

        //3er Approach
//        usuariosCollection.insertOne(usuario);

    }

    public void addUsuarios(List<Usuario> usuarios) throws JsonProcessingException {
        //1st Approach
        List<Document> documents = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            Document newDoc = new Document("nombre", usuario.getNombre())
                    .append("apellido", usuario.getApellido())
                    .append("edad", usuario.getEdad())
                    .append("sexo", usuario.getSexo())
                    .append("lenguajesAprendidos", usuario.getLenguajesAprendidos());
            documents.add(newDoc);
        }
        usuariosCollection.insertMany(documents);

        //2nd Approach
//        usuariosCollection.insertMany(usuarios);

    }

    public void deleteUsuarioByQuery(HashMap<String, Object> queryParams) {
        List<Bson> filters = new ArrayList<>();
        queryParams.forEach((k, v) -> filters.add(Filters.eq(k, v)));
        Bson finalFilter = Filters.and(filters);
        usuariosCollection.deleteMany(finalFilter);

        //usuariosCollection.deleteOne(finalFilter);

        //usuariosCollection.findOneAndDelete(finalFilter); //Atomic
    }

    public void updateUsuario(HashMap<String, Object> queryParams, HashMap<String, Object> updateParams) {
        List<Bson> filters = new ArrayList<>();
        queryParams.forEach((k, v) -> filters.add(Filters.eq(k, v)));
        Bson finalFilter = Filters.and(filters);

        List<Bson> updates = new ArrayList<>();
        updateParams.forEach((k, v) -> updates.add(Updates.set(k, v)));

        usuariosCollection.updateMany(finalFilter, updates);
    }

}
