import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ExampleServer {
    public static void main (String [] args)throws IOException{
        var s = new ServerSocket(8189);
        Socket incoming = s.accept();
        InputStream inputStream = incoming.getInputStream();
        OutputStream outputStream = incoming.getOutputStream();
        var in = new Scanner(inputStream, StandardCharsets.UTF_8);
        var out = new PrintWriter(new OutputStreamWriter(outputStream,StandardCharsets.UTF_8),true);
        out.println("Hello! Enter BYE to exit.");
        var done = false;
        while (!done){
            String line = in.nextLine();
            out.println("Echo: " + line);
            if (line.trim().equals("BYE")){
                done = true;
                incoming.close();
            }
        }
    }
}
