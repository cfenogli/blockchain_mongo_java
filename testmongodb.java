
package block_chain;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;


public class testmongodb
{

    public static void main(String[] args)
    {
    //MongoClientURI uri = new MongoClientURI( "mongodb://cfenoglio:Differentiable@cluster0-shard-00-00-jtcrh.mongodb.net:27017,cluster0-shard-00-01-jtcrh.mongodb.net:27017,cluster0-shard-00-02-jtcrh.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority" );


           // "mongodb+srv://cfenoglio:Differentiable@cluster0-jtcrh.mongodb.net/test?retryWrites=true&w=majority/readPreference=secondary&replicaSet=your_replSet_name&ssl=true");

    //MongoClientURI uri = new MongoClientURI("mongodb+srv://DAVE:DAVE@cluster0-jtcrh.mongodb.net/test?retryWrites=true&w=majority");


    MongoClientURI uri = new MongoClientURI(
            "mongodb+srv://CALIB:CALIB@lasershark-6ei09.mongodb.net/test?retryWrites=true&w=majority");

    MongoClient mongoClient = new MongoClient(uri);
    MongoDatabase database = mongoClient.getDatabase("test");


    //MongoClient mongoClient = new MongoClient(uri);
    //MongoDatabase database = mongoClient.getDatabase("test");

	MongoCollection <Document> collection = database.getCollection("test");

	System.out.println(collection);

	Document doc = new Document("Testing", "Newinfo"); //.append("type123", "database321").append("count", 47);

    collection.insertOne(doc);
	mongoClient.close();

	System.out.println("My god, we made it bois");
    }

}