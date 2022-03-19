package game.utils;

import game.Game;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Utils {
    // Global variables
    public final static int GROUND = 0;
    public final static int WALL = 1;
    public final static int BOX = 2;
    public final static int DESTINATION = 3;
    public final static int PLAYER = 4;

    public final static String KEY = "x_^FRwP+2b3+6qcXk_PH";
    public final static String DEFAULT_PLAYER_EMOTE = "\uD83D\uDC7B";

    // Private variables
    private static byte[] key;
    private static SecretKeySpec secretKey;

    /*
     * Sorts a LinkedList of games based off of completed level and timestamp of completed games
     * Pre: LinkedList that contains Game objects within
     * Post: Returns a sorted Game array
     */
    public static Game[] insertionSort(LinkedList ls) {

        Game[] arr = new Game[ls.length];

        LinkedList.Node data = ls.getHead();
        int count = 0;
        do {
            if (data != null) {
                arr[count] = data.getData();
                data = data.getNext();
                count++;
            }
        } while (data != null);

        for (int i = 0; i < arr.length; i++) {
            Game gameToInsert = arr[i];

            boolean completed = false;

            for (int j = 0; j < i && !completed; j++) {
                if (gameToInsert.getLevel() > arr[j].getLevel()
                        || (gameToInsert.getLevel() == arr[j].getLevel()
                        && gameToInsert.getTimestamp() < arr[j].getTimestamp())) {
                    Game val1 = arr[j], val2 = arr[j+1];
                    arr[j] = gameToInsert;

                    for (int k = j+1; k < i+1; k++) {
                        arr[k] = val1;
                        val1 = val2;
                        if (k+1 < i) val2 = arr[k+1];
                    }
                    completed = true;
                }
            }
        }

        return arr;
    }

    /*
     * Formats an inputted string in to an appropriate key to be used for AES ECB encryption and decryption
     * Pre: String of a key
     */
    public static void setKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /*
     * Encrypts a string based on an inputted key using AES ECB encryption
     * Pre: Takes in a string, and a string key
     * Post: Returns an encrypted string
     */
    public static String encryptString(String text, String key) {
        try {
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Decrypts a string based on an inputted key using AES ECB decryption
     * Pre: Takes in an encrypted string, and a string key
     * Post: Returns a decrypted string, or null if it cannot be decrypted
     */
    public static String decryptString(String text, String key) {
        try {
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
        }
        catch (Exception e) {
            return null;
        }
    }
}
