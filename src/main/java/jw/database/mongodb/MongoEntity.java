package jw.database.mongodb;

import jw.data.models.Entity;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class MongoEntity extends Entity
{

    @BsonProperty(value = "student_id")
    public int siema;

}
