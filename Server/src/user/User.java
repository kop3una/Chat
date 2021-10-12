package user;

import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class User {
    private int status;
    private final Socket socket;
    private String name;
    private String surname;
    private String login;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    private String gender;
    private String location;

    public User(Socket incomingSocket) {
        this.socket = incomingSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNameSurname (){
        String nameSurnameSrt = (getName()+" "+getSurname()+": ");
        return new String(nameSurnameSrt.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8);
    }
}