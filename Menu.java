
import java.util.Scanner;
import org.json.JSONException;
//import static Block_Chain.*;
import java.security.NoSuchAlgorithmException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import com.mongodb.Block;
import org.json.JSONObject;


public class Menu

{

    public static void main(String[] args) throws JSONException, NoSuchAlgorithmException {
        Scanner menu = new Scanner(System.in);
        int choice;
        int difficulty;
        int data;
        String sender;
        String recipient;
        String userSearch;
        String recipSearch;
        int gtSearch;
        int ltSearch;
        Block_Chain genBlock = new Block_Chain();

        MongoClientURI uri = new MongoClientURI(
        				"mongodb://DAVE:DAVE@cluster0-shard-00-00-0gdb9.mongodb.net:27017,cluster0-shard-00-01-0gdb9.mongodb.net:27017,cluster0-shard-00-02-0gdb9.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("blockchain");

        MongoCollection <Document> collection = database.getCollection("blocks");
        System.out.println(collection );
        
        collection.insertOne(Block_Chain.getBlock(0));

        do {
            System.out.println(
                            "1 - Add to blockchain \n" +
                            "2 - View the blockchain \n" +
                            "3 - Terminate \n" +
            				"4 - Push to cloud \n" +
            				"5 - search by sender \n" + 
            				"6 - search by recipient \n" + 
            				"7 - search for transaction greater than or equal to value \n" +
            				"8 - search for transaction less than or equal to value \n");
    

            choice = menu.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Please enter a difficulty for the blockchain:");
                    difficulty = menu.nextInt();
                    System.out.println("Please enter data for the blockchain:");
                    data = menu.nextInt();
                    System.out.println("Please enter sender infomation: ");
                    sender = menu.next();
                    System.out.println("Please enter recipient information: ");
                    recipient = menu.next();
                    genBlock.addToChain(difficulty, data, sender, recipient);
                    
                    break;
                case 2:
                    Block_Chain.viewBlockChain();
                    break;
                case 3:
                    choice = Block_Chain.terminate();
                    break;
                case 4:
                	for (int i = 1; i < genBlock.blockArray.size(); i++) {
                		collection.insertOne(genBlock.getBlock(i));
                	}
                	break;
                case 5:
                	System.out.println("Enter the sender you want to search for");
                	userSearch = menu.next();
                	  FindIterable<Document> targets = collection.find(Filters.eq("sender",userSearch));
                	  ArrayList<JSONObject> output = new ArrayList<>();
                	  
                	//Use a for each loop
                	  for(Document d : targets)
                	  {
                	   output.add(new JSONObject(d.toJson().toString()));
                	  }
                	  
                	  for (int i = 0; i < output.size(); i++)
                	  {
                		  System.out.print("\n" + output.get(i) + "\n");
                	  }
                	break;
                case 6:
	                	System.out.println("Enter the recipient you want to search for");
	                	recipSearch = menu.next();
	            	  FindIterable<Document> targets2 = collection.find(Filters.eq("recipient",recipSearch));
	            	  ArrayList<JSONObject> output2 = new ArrayList<>();
                	  
                	//Use a for each loop
                	  for(Document d : targets2)
                	  {
                	   output2.add(new JSONObject(d.toJson().toString()));
                	  }
                	  for (int i = 0; i < output2.size(); i++)
                		  System.out.print("\n" + output2.get(i) + "\n");
                	break;
                case 7:
                	System.out.println("Enter a data value which you want to find blocks greater than or equal to");
                	gtSearch = menu.nextInt();
                	  FindIterable<Document> targets3 = collection.find(Filters.gte("data",gtSearch));
                	  ArrayList<JSONObject> output3 = new ArrayList<>();
                	  
                	//Use a for each loop
                	  for(Document d : targets3)
                	  {
                	   output3.add(new JSONObject(d.toJson().toString()));
                	  }
                	  for (int i = 0; i < output3.size(); i++)
                		  System.out.print("\n" + output3.get(i) + "\n");
                	break;
                case 8: 
                	System.out.println("Enter a data value which you want to find blocks lesser than or equal to");
                	ltSearch = menu.nextInt();
                	  FindIterable<Document> targets4 = collection.find(Filters.lte("data",ltSearch));
                	  ArrayList<JSONObject> output4 = new ArrayList<>();
                	  
                	//Use a for each loop
                	  for(Document d : targets4)
                	  {
                	   output4.add(new JSONObject(d.toJson().toString()));
                	  }
                	  for (int i = 0; i < output4.size(); i++)
                		  System.out.print("\n" + output4.get(i) + "\n");
                	break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

        } while ((choice > 0 && choice <= 8));

        mongoClient.close();




    }
}