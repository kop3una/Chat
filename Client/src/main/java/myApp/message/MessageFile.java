package myApp.message;

import java.util.ArrayList;

public class MessageFile {
    private String login;
    private ArrayList<byte []> bufferList = new ArrayList<>();
    private ArrayList<String> fileNameList = new ArrayList<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<byte[]> getBufferList() {
        return bufferList;
    }

    public ArrayList<String> getFileNameList(){
        return fileNameList;
    }

}
