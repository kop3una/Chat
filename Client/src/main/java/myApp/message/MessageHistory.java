package myApp.message;

import java.util.ArrayList;

public class MessageHistory {

    private final ArrayList<Message> messageUserList = new ArrayList<>();
    private final ArrayList<MessageFile> messageFileUserList = new ArrayList<>();

    public void addUserMessageHistory(String login) {
        if (checkMessage(login)) {
            Message message = new Message();
            MessageFile messageFile = new MessageFile();
            message.setLogin(login);
            messageFile.setLogin(login);
            messageUserList.add(message);
            messageFileUserList.add(messageFile);
        }
    }

    public StringBuffer getMessage (String login){
        for (Message message : messageUserList){
            if (message.getLogin().equals(login)){
                return message.getMessage();
            }
        }
        return null;
    }

    public ArrayList<byte []> getFile (String login){
        for (MessageFile messageFile: messageFileUserList){
            if (messageFile.getLogin().equals(login)){
                return messageFile.getBufferList();
            }
        }
         return null;
    }

    public ArrayList<String> getFileNameList (String login){
        for (MessageFile messageFile: messageFileUserList){
            if (messageFile.getLogin().equals(login)){
                return messageFile.getFileNameList();
            }
        }
        return null;
    }

    private boolean checkMessage(String login) {
        for (Message message : messageUserList) {
            if (message.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

}
