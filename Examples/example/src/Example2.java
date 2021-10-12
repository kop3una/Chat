import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Example2 {
    public static void main (String [] args) throws IOException {
        var in = new Scanner(System.in);
        String address = in.nextLine();
        if (address.length() > 0){
            InetAddress [] addresses = InetAddress.getAllByName(address);
            for (InetAddress a : addresses){
                System.out.println(a);
            }
        } else {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println(localHostAddress);
        }
    }
}
