package com.example.easyrestapp.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MongoDB {

    public static void main() {


        Executor executor = Executors.newSingleThreadExecutor();
        // Replace the connection string with your own
        String connectionString = "mongodb+srv://EasyRest:121197@cluster0.6oimwzg.mongodb.net/?retryWrites=true&w=majority";
//        MongoClient mongoClient = MongoClients.create(connectionString);

//        executor.execute(()-> {
            // Connect to MongoDB Atlas

                    // Replace the placeholder with your MongoDB deployment's connection string
                    String uri = "<connection string uri>";
                    try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                        MongoDatabase database = mongoClient.getDatabase("easyRest");
                        MongoCollection<Document> collection = database.getCollection("Dish.dishes");
//                        Document doc = collection.find(eq("title", "Back to the Future")).first();
//                        if (doc != null) {
//                            System.out.println(doc.toJson());
//                        } else {
//                            System.out.println("No matching documents found.");
//                        }
                }
         //   });

    }
}
