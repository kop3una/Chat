package myApp.user;

import java.net.Socket;

public class User {
    private String name;
    private String surname;
    private String login;
    private String password;
    private String gender;
    private String location;
    private Socket socket;

    public User () {

    }

    @Override
    public String toString() {
        return this.name + " " + this.surname + "    @"+ this.login+
                ((!this.gender.equals("")) ?"    Пол: "+ this.gender : "" )+
                ((!this.location.equals("")) ?"  г: "+ this.location : "" );
    }

    public User(String name, String surname, String login, String password, String gender, String location) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
