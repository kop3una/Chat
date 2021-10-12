import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Example1 {
    public static void main (String [] args) throws IOException {
        var s = new Socket("192.168.136.1",8189);
        Thread thread = new Thread(new InMessage(s));
        thread.start();
        thread = new Thread(new OutMessage(s));
        thread.start();
   }
}

class InMessage implements Runnable{
    private Socket socket;
    InMessage(Socket incomingSocket){
        socket = incomingSocket;
    }

    @Override
    public void run() {
        try {
            var in = new Scanner(socket.getInputStream());
            while (in.hasNextLine()){
                String line = in.nextLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class OutMessage implements Runnable{
    private Socket socket;
    private Scanner console = new Scanner(System.in);
    OutMessage(Socket incomingSocket){
        socket = incomingSocket;
    }

    @Override
    public void run() {
        String outStr = "";
        try {
            var out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            while (!outStr.equals("BYE")){
                outStr = console.nextLine();
                out.println(outStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}