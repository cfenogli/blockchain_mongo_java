package block_chain;

import java.util.Scanner;
import org.json.JSONException;
import static block_chain.BlockChain.*;
import java.security.NoSuchAlgorithmException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;


public class Menu

{

    public static void main(String[] args) throws JSONException, NoSuchAlgorithmException {
        Scanner menu = new Scanner(System.in);
        int choice;
        int difficulty;
        String data;
        String sender;
        String recipient;
        BlockChain genBlock = new BlockChain();

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://bradley:bradley@cluster0-zubla.mongodb.net/blocks?retryWrites=true&w=majority\n");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("blockchain");

        MongoCollection <Document> collection = database.getCollection("blocks");
        System.out.println(collection );

        Document doc = new Document("blockchain", "blocks");
        doc.append("GenesisBlock",genBlock);

        collection.insertOne(doc);

        do {
            System.out.println(
                            "1 - Add to blockchain \n" +
                            "2 - View the blockchain \n" +
                            "3-Terminate \n");

            choice = menu.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Please enter a difficulty for the blockchain:");
                    difficulty = menu.nextInt();
                    System.out.println("Please enter data for the blockchain:");
                    data = menu.next();
                    System.out.println("Please enter sender infomation: ");
                    sender = menu.next();
                    System.out.println("Please enter recipient information: ");
                    recipient = menu.next();
                    BlockChain bradleyCoin = new BlockChain(difficulty, data, sender, recipient);
                    doc.append("BradleyCoin", bradleyCoin);
                    collection.insertOne(doc);
                    break;
                case 2:
                    System.out.println("The Blockchain is:\n" + viewBlockChain());
                    break;
                case 3:
                    choice = terminate();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

        } while ((choice > 0 && choice < 7));

        //collection.insertOne(doc);
        mongoClient.close();




    }
}