package jw.database.mongodb;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jw.data.models.Entity;
import jw.data.repositories.Repository;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class MongoRepository<T extends Entity> implements Repository<T> {

    private final Class<T> aClass;
    private final MongoCollection<T> collection;

    public MongoRepository(MongoDatabase database, Class<T> _class)
    {
       this.aClass = _class;
       var array =  Lists.newArrayList(database.listCollectionNames());
       if(!array.contains(_class.getSimpleName()))
       {
           database.createCollection(_class.getSimpleName());
       }
        this.collection = database.getCollection(_class.getSimpleName(),_class);
    }

    @Override
    public Class<T> getEntityClass() {
        return aClass;
    }

    @Override
    public T getOne(String id) {
       return collection.find( new Document("id", id)).first();
    }

    @Override
    public ArrayList<T> getMany(HashMap<String, String> args)
    {
        return null;
    }

    @Override
    public ArrayList<T> getMany() {
        return Lists.newArrayList(collection.find());
    }

    @Override
    public boolean insertOne(T data)
    {
         collection.insertOne(data);
         return true;
    }

    @Override
    public boolean insertMany(ArrayList<T> data) {
        collection.insertMany(data);
        return true;
    }

    @Override
    public boolean updateOne(String id, T data) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", id); // (1)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", data); // (3)

        collection.updateOne(query, updateObject);
        return true;
    }

    @Override
    public boolean updateMany(HashMap<String, T> data) {
        return true;
    }

    @Override
    public boolean deleteOne(T data) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", data.id); // (1)
        collection.deleteOne(query);
        return true;
    }

    @Override
    public boolean deleteMany(ArrayList<T> data) {
        return false;
    }


}
