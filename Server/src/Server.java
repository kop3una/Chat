import factory.UserFactory;
import user.ProcessUserInfo;
import user.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ArrayList<User> usersList = new ArrayList<>(); //
    private Thread thread;
    private UserFactory UserFactory = new UserFactory();
    private User user;
    public void start (){
        try (var s = new ServerSocket(8189)) { // приходит подключение
            while (true) {
                Socket incoming = s.accept();
                user = UserFactory.newUser(incoming);
                ProcessUserInfo.processUserInfo(user);
                usersList.add(user);
                System.out.println("User: @"+user.getLogin()+" on Server");
                thread = new Thread(new PerformUser(user, usersList));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}