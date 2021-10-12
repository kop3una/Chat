package factory;

import user.User;

import java.net.Socket;

public class UserFactory {
    public User newUser(Socket incomingSocket) {
        return new User(incomingSocket);
    }
}
