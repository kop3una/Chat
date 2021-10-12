package myApp.message;

public class Message {
    private String login;
    private final StringBuffer message = new StringBuffer();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public StringBuffer getMessage (){
        return message;
    }
}
