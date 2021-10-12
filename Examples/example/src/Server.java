import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {
    static int countUser = 0;
    public static void main(String[] args) {
        try (var s = new ServerSocket(8189)) {
            while (true) {
                Socket incoming = s.accept();
                countUser++;
                System.out.println("Users on server: " + countUser);
                Runnable r = new ThreadEchoHandler(incoming);
                var t = new Thread(r);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadEchoHandler implements Runnable {
    private final Socket incoming;

    ThreadEchoHandler(Socket incomingSocket) {
        incoming = incomingSocket;
    }

    public void run() {
        try (InputStream inStream = incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream();
             var in = new Scanner(inStream, StandardCharsets.UTF_8);
             var out = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true)) {
            out.println("Hello! Enter BYE to exit.");
            var done = false;
            while (!done){
                String line = in.nextLine();
                out.println("Echo: "+ line);
                if (line.trim().equals("BYE")){
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
