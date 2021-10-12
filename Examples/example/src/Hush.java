import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hush {
    public static void main (String [] args) throws NoSuchAlgorithmException {
        String str = "Hello world";
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        MessageDigest md5= MessageDigest.getInstance("MD5");
        byte [] bytes = md5.digest(str.getBytes());
        for (byte b : bytes){
            System.out.print(b);
        }
    }
}
