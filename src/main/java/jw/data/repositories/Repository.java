package jw.data.repositories;

import java.util.ArrayList;
import java.util.HashMap;

public interface Repository <T>
{
     Class<T> getEntityClass();

     T getOne(String id);

     ArrayList<T> getMany(HashMap<String,String> args);

     boolean insertOne(T data);

     boolean insertMany(ArrayList<T> data);

     boolean updateOne(String id,T data);

     boolean updateMany(HashMap<String,T> data);

     boolean deleteOne(String id,T data);

     boolean deleteMany(ArrayList<T> data);

     boolean loadData();

     boolean saveData();
}
