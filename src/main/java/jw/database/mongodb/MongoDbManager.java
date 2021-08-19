package jw.database.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MongoDbManager {

    MongoClient mongoClient;
    MongoDatabase database;

    public MongoDbManager() {

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        ConnectionString connectionString = new ConnectionString("mongodb+srv://JW:123@jwdatabase.a15gw.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("minecraft");

        MongoCollection<MongoEntity> grades = database.getCollection("players", MongoEntity.class);
    }


    public MongoDatabase getDatabase()
    {
        return database;
    }

    public void AddCollection(String name) {
        database.createCollection(name);
    }


}
