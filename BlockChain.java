package block_chain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.xml.bind.DatatypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockChain
{
    private static ArrayList<JSONObject> blockArray = new ArrayList<>(100);
    private static int _index;
    private static Timestamp timestamp;
    private static String data;
    private String concatData;
    private static int difficulty;
    private static String previousHash;
    private static String sender = "";
    private static String recipient = "";
    private static BigInteger nonce = BigInteger.ZERO;
    private static String chainHash;
    private byte[] hash;
    StringBuilder sb = new StringBuilder();
    JSONObject jSonBlock = new JSONObject();


    public BlockChain() throws NoSuchAlgorithmException, JSONException {
        JSONObject jSonBlock = new JSONObject();
        difficulty = 1;
        data = "genesis";
        timestamp = getTime();
        nonce = nonce.add(BigInteger.valueOf(1));
        concatData = difficulty + data + timestamp + nonce;
        _index = 0;
        jSonBlock.put("difficulty", this.difficulty);
        jSonBlock.put("data", this.data);
        jSonBlock.put("TimeStamp", timestamp);

        setSha256(concatData, jSonBlock);

        while (!isDifficult(chainHash)) {
            nonce = nonce.add(BigInteger.valueOf(1));
            concatData = difficulty + data + sender + recipient + timestamp + nonce;
            setSha256(concatData, jSonBlock);
        }
    }

    public BlockChain(int difficulty, String userData, String sender, String recipient) throws NoSuchAlgorithmException, JSONException {
        JSONObject jSonBlock = new JSONObject();
        this.data = userData;
        this.concatData = "";
        this.difficulty = difficulty;
        timestamp = getTime();
        this.sender = sender;
        this.recipient = recipient;

        jSonBlock.put("difficulty", difficulty);
        jSonBlock.put("data", data);
        jSonBlock.put("sender", sender);
        jSonBlock.put("recipient", recipient);
        jSonBlock.put("TimeStamp", timestamp);


        assert nonce != null;
        nonce = nonce.add(BigInteger.valueOf(1));

        concatData = difficulty + data + sender + recipient + previousHash + timestamp +  nonce;
        setSha256(concatData, jSonBlock);

        while (!isDifficult(chainHash)) {
            nonce = nonce.add(BigInteger.valueOf(1));
            concatData = difficulty + data + sender + recipient + previousHash + timestamp + nonce;
            setSha256(concatData, jSonBlock);
        }
        _index++;
    }

    public boolean isDifficult(String hash) throws JSONException {
        String temp;
        String control = "";

        for (int i = 0; i < difficulty; i++) {
            control += "0";
        }

        temp = hash.substring(0, difficulty);

        if (temp.equals(control)) {
            return true;
        } else {
            return false;
        }
    }


    public void setSha256(String Data, JSONObject block) throws NoSuchAlgorithmException, JSONException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        hash = digest.digest(Data.getBytes(StandardCharsets.UTF_8));

        for (byte b : hash)
            sb.append(String.format("%02x", b));

        if (isDifficult(sb.toString())) {

            block.put("nonce", nonce);
            block.put("hash", sb.toString());
            blockArray.add(block);
        }
        previousHash = chainHash;
        chainHash = sb.toString();
        sb.delete(0, sb.length());
    }

    public static String viewBlockChain() {
        /* TODO
         * JSON TO STRING IN THIS METHOD
         */
        String output = "";
        for (int i = 0; i < blockArray.size(); i++) {
            output = blockArray.get(i).toString();
        }
        return output;
    }

    public static int terminate()
    {
        return -1;
    }

    public static Timestamp getTime()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp);
    }

}
