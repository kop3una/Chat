package myApp.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import myApp.App;
import myApp.buttons.MyButton;
import myApp.dateBase.DateBaseHandler;
import myApp.file.MyFile;
import myApp.message.MessageHistory;
import myApp.user.InMessage;
import myApp.user.User;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainScene {

    private final User user = App.getUser();
    private final ArrayList<User> usersList = new ArrayList<>();
    private final ArrayList<MyButton> buttonsList = new ArrayList<>();
    private final MessageHistory messageHistory = new MessageHistory();
    private File sendFile;
    @FXML
    private Button registrationBtn;

    @FXML
    private TextField inputArea;

    @FXML
    private TextArea messageArea;

    @FXML
    private TextField chatInfo;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane addPrivateChatAnchPane;

    @FXML
    private Button addBtn;

    @FXML
    private TextField inputAddLogin;

    @FXML
    private TextField currentLogin;

    @FXML
    private TextArea errIncorrectInput;

    @FXML
    private TextArea errLoginNotFound;

    @FXML
    private TextArea errIsYourLogin;

    @FXML
    private TextArea errOutOfMemory;

    @FXML
    void initialize() throws IOException, SQLException {
        getUsersList(usersList);

        currentLogin.setText("");
        messageHistory.addUserMessageHistory(currentLogin.getText());
        MyButton button = new MyButton();
        vBox.getChildren().add(button.createGeneralChatButton());
        buttonsList.add(button);
        vBox.getChildren().get(buttonsList.size() - 1).addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (!currentLogin.getText().equals(button.getLogin())) {
                chatInfo.setText(button.getChatName());
                currentLogin.setText("");
                messageArea.setText(messageHistory.getMessage(currentLogin.getText()).toString());
                MyFile myFile = new MyFile();
                for (int i = 0; i < messageHistory.getFile(currentLogin.getText()).size();) {
                    byte[] buffer = messageHistory.getFile(currentLogin.getText()).get(0);
                    myFile.writeInFile(buffer, user.getLogin(),
                            currentLogin.getText(), messageHistory.getFileNameList(currentLogin.getText()).get(0));
                    messageHistory.getFile(currentLogin.getText()).remove(0);
                    messageHistory.getFileNameList(currentLogin.getText()).remove(0);
                }
            }

        });

        MyButton buttonFriend = new MyButton();
        vBox.getChildren().add(buttonFriend.createFriendButton());
        messageHistory.addUserMessageHistory(buttonFriend.getLogin());
        buttonsList.add(buttonFriend);
        vBox.getChildren().get(buttonsList.size() - 1).addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (!currentLogin.getText().equals(buttonFriend.getLogin())) {
                chatInfo.setText(buttonFriend.getChatName());
                currentLogin.setText(buttonFriend.getLogin());
                messageArea.setText(messageHistory.getMessage(buttonFriend.getLogin()).toString());
            }
        });

        chatInfo.setText("Общий чат");
        var socket = new Socket("192.168.136.1", 8189);
        user.setSocket(socket);
        sendUserInfo();
        InMessage inMessage = new InMessage(user.getSocket(), messageArea, messageHistory, this.user,
                this.buttonsList, this.currentLogin, this.chatInfo);
        inMessage.start();
    }

    @FXML
    void sendMessage(MouseEvent event) {
        try {


        if (!currentLogin.getText().equals("friend")) {
            outMessage();
            inputArea.setText("");
        } } catch (OutOfMemoryError e) {
            inputArea.setText("");
            addPrivateChatAnchPane.setVisible(true);
            inputAddLogin.setVisible(false);
            addBtn.setVisible(false);
            errOutOfMemory.setVisible(true);
        }
    }

    @FXML
    void sendMesgEnter(KeyEvent keyEvent) {
        if ((keyEvent.getCode() == KeyCode.ENTER) && (!addPrivateChatAnchPane.isVisible())) {
            outMessage();
            inputArea.setText("");
        } else if ((keyEvent.getCode() == KeyCode.ENTER) && (addPrivateChatAnchPane.isVisible())) {
            addBtn();
        }
    }

    @FXML
    void addPrivateChat() {
        System.out.println("Add");
        addPrivateChatAnchPane.setVisible(true);
    }

    @FXML
    void exitAddPrivateChat() {
        addPrivateChatAnchPane.setVisible(false);
        inputAddLogin.setVisible(true);
        addBtn.setVisible(true);
        errOutOfMemory.setVisible(false);
        errIncorrectInput.setVisible(false);
        errIsYourLogin.setVisible(false);
        errLoginNotFound.setVisible(false);
    }

    @FXML
    void addBtn() {
        String login = inputAddLogin.getText().trim();
        if (checkUser(login)) {
            login = inputAddLogin.getText().trim().substring(1);
            if (checkButton(login)) {
                MyButton button = new MyButton();
                vBox.getChildren().add(button.createButton(getUser(login)));
                buttonsList.add(button);
                messageHistory.addUserMessageHistory(button.getLogin());
                vBox.getChildren().get(buttonsList.size() - 1).addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    if (!currentLogin.getText().equals(button.getLogin())) {
                        chatInfo.setText(button.getChatName());
                        currentLogin.setText(button.getLogin());
                        messageArea.setText(messageHistory.getMessage(currentLogin.getText()).toString());
                        MyFile myFile = new MyFile();
                        for (int i = 0; i < messageHistory.getFile(currentLogin.getText()).size();) {
                            byte[] buffer = messageHistory.getFile(currentLogin.getText()).get(0);
                            myFile.writeInFile(buffer, user.getLogin(),
                                    currentLogin.getText(), messageHistory.getFileNameList(currentLogin.getText()).get(0));
                            messageHistory.getFile(currentLogin.getText()).remove(0);
                            messageHistory.getFileNameList(currentLogin.getText()).remove(0);
                        }
                    }
                });
            }
            addPrivateChatAnchPane.setVisible(false);
        }
    }

    @FXML
    void printLogin(KeyEvent keyEvent) {
        if (keyEvent.getCode() != KeyCode.ENTER) {
            errLoginNotFound.setVisible(false);
            errIncorrectInput.setVisible(false);
            errIsYourLogin.setVisible(false);
        }
    }

    @FXML
    void choiceFile() {
        MyFile myFile = new MyFile();
        sendFile = App.readFile();
        if (sendFile != null) {
            StringBuffer sendFileMessage = new StringBuffer(inputArea.getText());
            if (sendFileMessage.length() == 0) {
                sendFileMessage.append("Прикрепил файл: " + sendFile.getName());
            } else {
                sendFileMessage.append(" + прикрепил файл: " + sendFile.getName());
            }
        inputArea.setText(sendFileMessage.toString());

        } else if (!inputArea.getText().equals("")) {
            if (inputArea.getText().lastIndexOf("Прикрепил файл: ") != -1){
                inputArea.setText("");
            } else {
                int index = inputArea.getText().lastIndexOf(" + прикрепил файл: ");
                inputArea.setText(inputArea.getText().substring(0,index-1));
            }
        }
//        MyFile myFile = new MyFile();
//        File file = App.readFile();
//        myFile.calcFileExtension(file);
//        //   myFile.writeInFile(myFile.calcByteArr(file));
//        try {
//            var out = new DataOutputStream(user.getSocket().getOutputStream());
//            var outBuff = new BufferedOutputStream(out);
//            byte [] buffer = myFile.calcByteArr(file);
//            out.writeUTF(""+(char)4+buffer.length+(char)4+file.getName()+(char)4);
//            outBuff.write(buffer,0,buffer.length);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void outMessage() {
        MyFile myFile = new MyFile();
        try {
            var out = new DataOutputStream(user.getSocket().getOutputStream());
            var outStr = inputArea.getText();
            if (sendFile == null){
                out.writeUTF(formMessage(outStr));
                out.flush();
            } else {
                out.writeUTF(formMessageWithFile(outStr));
                out.flush();
                byte [] buffer = myFile.calcByteArr(sendFile);
                var outBuff = new BufferedOutputStream(user.getSocket().getOutputStream(),buffer.length);
                outBuff.write(buffer,0,buffer.length);
             //   outBuff.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //  try {
//            var out = new PrintWriter(new OutputStreamWriter(user.getSocket().getOutputStream(), StandardCharsets.UTF_8), true);


        // out.println(formMessage(outStr));
        //  } catch (IOException e) {
        //      e.printStackTrace();
        //  }
    }

    private void sendUserInfo() {
//        try {
//            var out = new PrintWriter(new OutputStreamWriter(user.getSocket().getOutputStream(), StandardCharsets.UTF_8), true);
//            var outStr = user.getName() + (char) 1 + user.getSurname() + (char) 1 + user.getLogin() + (char) 1 + user.getGender() + (char) 1 + user.getLocation() + (char) 1;
//            System.out.println(outStr);
//            out.println(outStr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            var out = new DataOutputStream(user.getSocket().getOutputStream());
            var outStr = user.getName() + (char) 1 + user.getSurname() + (char) 1 + user.getLogin() + (char) 1 + user.getGender() + (char) 1 + user.getLocation() + (char) 1;
            out.writeUTF(outStr);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUsersList(ArrayList<User> usersList) throws SQLException { //user list for button
        DateBaseHandler dbHandler = new DateBaseHandler();
        ResultSet resultSet = dbHandler.getUsers();
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLogin(resultSet.getString("login"));
            if (!this.user.getLogin().equals(user.getLogin())) {
                usersList.add(user);
            }
        }
    }

    private User getUser(String str) {
        for (User user : usersList) {
            if (user.getLogin().equals(str)) {
                return user;
            }
        }
        return null;
    }

    private boolean checkUser(String str) {
        if (!str.equals("")) {
            char[] loginArr = str.toCharArray();
            String login = String.copyValueOf(loginArr, 1, loginArr.length - 1);
            if (loginArr[0] != '@') {
                errIncorrectInput.setVisible(true);
            } else if (this.user.getLogin().equals(login)) {
                errIsYourLogin.setVisible(true);
            } else {
                for (User user : usersList) {
                    if (user.getLogin().equals(login)) {
                        return true;
                    }
                }
                errLoginNotFound.setVisible(true);
            }
        } else {
            errIncorrectInput.setVisible(true);
        }
        return false;
    }

    private boolean checkButton(String login) {
        for (MyButton button : buttonsList) {
            if (button.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

    private String formMessage(String message) {
        StringBuffer formMessage = new StringBuffer();
        if (currentLogin.getText().equals("")) {
            formMessage.append((char) 1);
        } else {
            formMessage.append((char) 2);
            formMessage.append(this.user.getLogin());
            formMessage.append((char) 2);
            formMessage.append(currentLogin.getText());
            formMessage.append((char) 2);
        }
        formMessage.append(message);
        return formMessage.toString();
    }

    private String formMessageWithFile(String message){
        MyFile myFile = new MyFile();
        StringBuffer sendFileMessage = new StringBuffer();
        sendFileMessage.append((char) 4);
        sendFileMessage.append(formMessage(message));
        sendFileMessage.append((char) 4);
        sendFileMessage.append(myFile.calcByteArr(sendFile).length);
        sendFileMessage.append((char) 4);
        sendFileMessage.append(sendFile.getName());
        sendFileMessage.append((char) 4);
        return sendFileMessage.toString();
    }
}
