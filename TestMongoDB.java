
//package Block_Chain;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

public class TestMongoDB {

public static void main(String[] args) {

System.out.println("Hello world!");

//Replace dbstring with the connection string from your database
String dbstring = "mongodb://DAVE:DAVE@cluster0-shard-00-00-0gdb9.mongodb.net:27017,cluster0-shard-00-01-0gdb9.mongodb.net:27017,cluster0-shard-00-02-0gdb9.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";

MongoClientURI uri = new MongoClientURI(dbstring);

MongoClient mongoClient = new MongoClient(uri);
MongoDatabase database = mongoClient.getDatabase("testingDB");

MongoCollection<Document> collection = database.getCollection("test");
System.out.println(collection);

Document doc = new Document("FirstName", "LastName")
               .append("type", "database")
               .append("count", 1);

collection.insertOne(doc);
System.out.println("WE OUT HERE");
}

}  

