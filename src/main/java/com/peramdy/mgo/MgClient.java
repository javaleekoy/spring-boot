package com.peramdy.mgo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author pd 2018/3/19.
 */
@Component
public class MgClient {

    @Autowired
    private MgConf mgConf;

    MongoClient client = null;


    private MongoClient createMongoClient() {
        MongoClient mongo = new MongoClient(mgConf.getUrl(), mgConf.getPort());
        return mongo;
    }


    /**
     * get db
     *
     * @param dbName
     * @return
     */
    public MongoDatabase getDatabase(String dbName) {
        client = createMongoClient();
        return client.getDatabase(dbName);
    }

    /**
     * drop db
     *
     * @param dbName
     */
    public void dropDatabase(String dbName) {
        try {
            client = createMongoClient();
            client.dropDatabase(dbName);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * create collection
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     */
    public void createCollection(String dbName, String collectionName) {
        try {
            client = createMongoClient();
            MongoDatabase db = client.getDatabase(dbName);
            db.createCollection(collectionName);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * get collection
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @return
     */
    public MongoCollection getCollection(String dbName, String collectionName) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection mongoCollection = mongoDatabase.getCollection(collectionName);
            return mongoCollection;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * drop collection
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     */
    public void dropCollection(String dbName, String collectionName) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection mongoCollection = mongoDatabase.getCollection(collectionName);
            mongoCollection.drop();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * 插入数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param doc            数据
     */
    public void insertOne(String dbName, String collectionName, Document doc) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
            mongoCollection.insertOne(doc);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * 插入数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param docs           数据
     */
    public void insertMany(String dbName, String collectionName, List<Document> docs) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
            mongoCollection.insertMany(docs);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * 删除数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param map            数据
     * @return
     */
    public DeleteResult deleteOne(String dbName, String collectionName, Map<String, Object> map) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
//        Bson filters = Filters.and(Filters.eq("", ""), Filters.eq("", ""));
            List<Bson> list = new ArrayList<Bson>();
            for (String key : map.keySet()) {
                list.add(Filters.eq(key, map.get(key)));
            }
            DeleteResult deleteResult = mongoCollection.deleteOne(Filters.and(list));
            return deleteResult;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * 删除数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param map            数据
     * @return
     */
    public DeleteResult deleteMany(String dbName, String collectionName, Map<String, Object> map) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
            List<Bson> list = new ArrayList<Bson>();
            for (String key : map.keySet()) {
                list.add(Filters.eq(key, map.get(key)));
            }
            DeleteResult deleteResult = mongoCollection.deleteMany(Filters.and(list));
            return deleteResult;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * 更新数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param filters        查询条件
     * @param update         更新条件
     * @return
     */
    public UpdateResult updateOne(String dbName, String collectionName, Map<String, Object> filters, Map<String, Object> update) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
            List<Bson> list = new ArrayList<Bson>();
            for (String key : filters.keySet()) {
                list.add(Filters.eq(key, filters.get(key)));
            }
            Document document = new Document();
            for (String key : update.keySet()) {
                document.append("$set", new Document(key, update.get(key)));
            }
            UpdateResult updateResult = mongoCollection.updateOne(Filters.and(list), document);
            return updateResult;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * 更新数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param filters        查询条件
     * @param update         更新条件
     * @return
     */
    public UpdateResult updateMany(String dbName, String collectionName, Map<String, Object> filters, Map<String, Object> update) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
            List<Bson> list = new ArrayList<Bson>();
            for (String key : filters.keySet()) {
                list.add(Filters.eq(key, filters.get(key)));
            }
            Document document = new Document();
            for (String key : update.keySet()) {
                document.append("$set", new Document(key, update.get(key)));
            }
            UpdateResult updateResult = mongoCollection.updateMany(Filters.and(list), document);
            return updateResult;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * 查询数据
     *
     * @param dbName         库名称
     * @param collectionName 集合名称
     * @param map            查询条件
     * @return
     */
    public FindIterable<Document> findAnd(String dbName, String collectionName, Map<String, Object> map) {
        try {
            MongoDatabase mongoDatabase = getDatabase(dbName);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
            BasicDBObject basicDBObject = new BasicDBObject();
            for (String key : map.keySet()) {
                basicDBObject.put(key, map.get(key));
            }
            FindIterable<Document> findIterable = mongoCollection.find(basicDBObject);
            for (Document doc : findIterable) {
                System.out.println(doc);
            }
            return findIterable;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

}