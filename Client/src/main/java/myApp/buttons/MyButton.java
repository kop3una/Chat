package myApp.buttons;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import myApp.user.User;

import java.util.ArrayList;


public class MyButton extends Button {
    private String chatName;
    private String login;
    private final ArrayList<Button> buttonsList = new ArrayList<>();

    public MyButton() {

    }


    public Button createGeneralChatButton() {
        this.login ="";
        this.chatName = "Общий чат";
        Font font = new Font("Impact", 15);
        Button button = new Button(chatName);
        button.setFocusTraversable(false);
        button.setPrefHeight(50);
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color:  #F39C63; -fx-background-radius: 0; -fx-border-color:  #2E3348");
        button.setTextFill(Color.WHITE);
        button.setWrapText(true);
        button.setFont(font);
        return button;
    }

    public Button createFriendButton(){
        this.login = "friend";
        this.chatName = "Друзья";
        Font font = new Font("Impact", 15);
        Button button = new Button(chatName);
        button.setFocusTraversable(false);
        button.setPrefHeight(50);
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color:  #F39C63; -fx-background-radius: 0; -fx-border-color:  #2E3348");
        button.setTextFill(Color.WHITE);
        button.setWrapText(true);
        button.setFont(font);
        return button;
    }

    public Button createButton(User user) {
        this.login = user.getLogin();
        this.chatName = user.getName() + " " + user.getSurname() + "  @" + user.getLogin();
        Font font = new Font("Impact", 10);
        Button button = new Button(chatName);
        button.setFocusTraversable(false);
        button.setPrefHeight(50);
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color:  #F39C63; -fx-background-radius: 0;  -fx-border-color:  #2E3348");
        button.setTextFill(Color.WHITE);
        button.setWrapText(true);
        button.setFont(font);
        return button;
    }

    public String getLogin() {
        return login;
    }

    public String getChatName(){
        return this.chatName;
    }

}
