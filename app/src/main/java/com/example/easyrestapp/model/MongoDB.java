package com.example.easyrestapp.model;
// import the MongoDB Java driver
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class MongoDB {

    public void connectToMongo(){
    // replace the <connection_string> with your MongoDB connection string
            String connectionString = "mongodb+srv://EasyRest:<password>@cluster0.6oimwzg.mongodb.net/?retryWrites=true&w=majority";
            MongoClientURI uri = new MongoClientURI(connectionString);

    // create a new MongoDB client
            MongoClient mongoClient = new MongoClient(uri);

    // access your MongoDB database and collection
            MongoDatabase database = mongoClient.getDatabase("EasyRest");
                    MongoCollection<Document> collection = database.getCollection("<collection_name>");

    // insert a document in the collection
            Document document = new Document("key", "value");
            collection.insertOne(document);

    // close the MongoDB client
            mongoClient.close();
    }

    // insert a document in the collection
//    Document document = new Document("key", "value");
//collection.insertOne(document);


}
