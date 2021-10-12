package myApp.user;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import myApp.buttons.MyButton;
import myApp.file.MyFile;
import myApp.message.MessageHistory;
import myApp.message.ProcessMessage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class InMessage extends Thread implements Runnable {

    private final Socket socket;
    private final TextArea messageArea;
    private final MessageHistory messageHistory;
    private final User user;
    private final ArrayList<MyButton> buttonsList;
    private final TextField currentLogin;
    private final TextField chatInfo;
    private final String FRIEND_LOGIN = "friend";

    public InMessage(Socket incomingSocket, TextArea messageArea, MessageHistory messageHistory, User user,
                     ArrayList<MyButton> buttonsList, TextField currentLogin, TextField chatInfo) {
        this.socket = incomingSocket;
        this.messageArea = messageArea;
        this.messageHistory = messageHistory;
        this.user = user;
        this.buttonsList = buttonsList;
        this.currentLogin = currentLogin;
        this.chatInfo = chatInfo;
    }

    @Override
    public void run() {
        try {
            String firstLogin;
            String secondLogin;
            var in = new DataInputStream(socket.getInputStream());
            while (true) {
                String line = in.readUTF();
                if (!ProcessMessage.isFileMessage(line)) {
                    firstLogin = ProcessMessage.getFirstLogin(line);
                    secondLogin = ProcessMessage.getSecondLogin(line);
                    if (firstLogin.equals(this.user.getLogin())) {
                        messageHistory.getMessage(secondLogin).append(ProcessMessage.getMessage(line) + "\n");
                        if (currentLogin.getText().equals(secondLogin)) {
                            messageArea.setText(messageHistory.getMessage(secondLogin).toString());
                        }
                    } else if (secondLogin.equals(this.user.getLogin())) {
                        if (checkButton(firstLogin)) {
                            messageHistory.getMessage(firstLogin).append(ProcessMessage.getMessage(line) + "\n");
                            if (currentLogin.getText().equals(firstLogin)) {
                                messageArea.setText(messageHistory.getMessage(firstLogin).toString());
                            }
                        } else {
                            messageHistory.addUserMessageHistory(firstLogin);
                            messageHistory.getMessage(firstLogin).append(ProcessMessage.getMessage(line) + "\n");
                            messageHistory.getMessage(FRIEND_LOGIN).append(ProcessMessage.friendRequest(line) + "\n");
                            messageArea.setText(messageHistory.getMessage(FRIEND_LOGIN).toString());
                            chatInfo.setText("Друзья");
                        }
                    }
                    if (firstLogin.equals("")) {
                        messageHistory.getMessage(firstLogin).append(ProcessMessage.getMessage(line) + "\n");
                        if (currentLogin.getText().equals("")) {
                            messageArea.setText(messageHistory.getMessage(firstLogin).toString());
                        }
                    }
                } else {
                    int bytesRead = 0;
                    MyFile myFile = new MyFile();
                    byte[] byteBuffer = new byte[ProcessMessage.getLength(line)];
                    BufferedInputStream inBuff = new BufferedInputStream(in, byteBuffer.length);
                    while (bytesRead != byteBuffer.length) {
                        int bytesReadForOnes = inBuff.read(byteBuffer, bytesRead, byteBuffer.length - bytesRead);
                        System.out.println(bytesReadForOnes);
                        bytesRead += bytesReadForOnes;
                    }
                    firstLogin = ProcessMessage.getFirstLoginFile(line);
                    secondLogin = ProcessMessage.getSecondLoginFile(line);
                    if (firstLogin.equals(this.user.getLogin())) {
                        messageHistory.getMessage(secondLogin).append(ProcessMessage.getMessageFile(line) + "\n");
                        messageHistory.getFile(secondLogin).add(byteBuffer);
                        messageHistory.getFileNameList(secondLogin).add(ProcessMessage.getFileName(line));
                        if (currentLogin.getText().equals(secondLogin)) {
                            messageArea.setText(messageHistory.getMessage(secondLogin).toString());
                            for (int i = 0; i < messageHistory.getFile(secondLogin).size();) {
                                byte[] buffer = messageHistory.getFile(secondLogin).get(0);
                                myFile.writeInFile(buffer, user.getLogin(),
                                        secondLogin, messageHistory.getFileNameList(secondLogin).get(0));
                                messageHistory.getFile(secondLogin).remove(0);
                                messageHistory.getFileNameList(secondLogin).remove(0);
                            }
                        }
                    } else if (secondLogin.equals(this.user.getLogin())) {
                        if (checkButton(firstLogin)) {
                            messageHistory.getMessage(firstLogin).append(ProcessMessage.getMessageFile(line) + "\n");
                            messageHistory.getFile(firstLogin).add(byteBuffer);
                            messageHistory.getFileNameList(firstLogin).add(ProcessMessage.getFileName(line));
                            if (currentLogin.getText().equals(firstLogin)) {
                                messageArea.setText(messageHistory.getMessage(firstLogin).toString());
                                for (int i = 0; i < messageHistory.getFile(firstLogin).size();) {
                                    byte[] buffer = messageHistory.getFile(firstLogin).get(0);
                                    myFile.writeInFile(buffer, user.getLogin(),
                                            secondLogin, messageHistory.getFileNameList(firstLogin).get(0));
                                    messageHistory.getFile(firstLogin).remove(0);
                                    messageHistory.getFileNameList(firstLogin).remove(0);
                                }
                            }
                        } else {
                            messageHistory.addUserMessageHistory(firstLogin);
                            messageHistory.getMessage(firstLogin).append(ProcessMessage.getMessageFile(line) + "\n");
                            messageHistory.getFile(firstLogin).add(byteBuffer);
                            messageHistory.getFileNameList(firstLogin).add(ProcessMessage.getFileName(line));
                            ProcessMessage.friendRequestFile(line, firstLogin);
                            messageHistory.getMessage(FRIEND_LOGIN).append(ProcessMessage.friendRequestFile(line, firstLogin) + "\n");
                            messageArea.setText(messageHistory.getMessage(FRIEND_LOGIN).toString());
                            chatInfo.setText("Друзья");
                        }
                    }
                    if (firstLogin.equals("")) {
                        messageHistory.getMessage(firstLogin).append(ProcessMessage.getMessageFile(line) + "\n");
                        messageHistory.getFile(firstLogin).add(byteBuffer);
                        messageHistory.getFileNameList(firstLogin).add(ProcessMessage.getFileName(line));
                        if (currentLogin.getText().equals("")) {
                            messageArea.setText(messageHistory.getMessage(firstLogin).toString());
                            for (int i = 0; i < messageHistory.getFile(firstLogin).size();) {
                                byte[] buffer = messageHistory.getFile(firstLogin).get(0);
                                myFile.writeInFile(buffer, user.getLogin(),
                                        secondLogin, messageHistory.getFileNameList(firstLogin).get(0));
                                messageHistory.getFile(firstLogin).remove(0);
                                messageHistory.getFileNameList(firstLogin).remove(0);
                            }
                        }
                    }
                }

                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkButton(String login) {
        for (MyButton button : buttonsList) {
            if (button.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

}
