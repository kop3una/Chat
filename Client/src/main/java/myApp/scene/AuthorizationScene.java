package myApp.scene;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import myApp.App;
import myApp.animation.Shake;
import myApp.dateBase.DateBaseHandler;
import myApp.user.User;

public class AuthorizationScene {

    @FXML
    TextField loginField;

    @FXML
    TextField passwordField;

    @FXML
    void initialize() throws SQLException {
        DateBaseHandler dbHandler = new DateBaseHandler();
        ResultSet resultSet = dbHandler.getUsers();
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("surname"));
            System.out.println(resultSet.getString("gender"));
            System.out.println(resultSet.getString("city"));
        }
    }

    @FXML
    private void registration() throws IOException {
        App.setRoot("Registration");
    }

    @FXML
    private void authBtn (MouseEvent mouseEvent) throws SQLException, NoSuchAlgorithmException, IOException {
        String loginText = loginField.getText().trim();
        String passwordText = passwordField.getText().trim();

        if ( mouseEvent.getButton() == MouseButton.PRIMARY ){
            if (!loginText.equals("") && !passwordText.equals("")){
                loginUser(loginText, passwordText);
            }
            else {
                System.out.println("Enter Login and Password");
                Shake();
            }
        }
    }

    @FXML
    void autnBtnEnter(KeyEvent keyEvent) throws SQLException, NoSuchAlgorithmException, IOException {
        String loginText = loginField.getText().trim();
        String passwordText = passwordField.getText().trim();
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (!loginText.equals("") && !passwordText.equals("")){
                loginUser(loginText, passwordText);
            }
            else {
                System.out.println("Enter Login and Password");
                Shake();
            }
        }
    }

    private void loginUser(String loginText, String passwordText) throws SQLException, NoSuchAlgorithmException, IOException {
        DateBaseHandler dbHandler = new DateBaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(passwordText);
        ResultSet resSet = dbHandler.getUser(user);
        int count = 0;
        while (resSet.next()){
            user.setName(resSet.getString("name"));
            user.setSurname(resSet.getString("surname"));
            user.setGender(resSet.getString("gender"));
            user.setLocation(resSet.getString("city"));
            count++;
        }

        if (count >= 1){
            System.out.println("Success");
            App.setUser(user);
            App.setRoot("Main");
        }
        else {
            System.out.println("Error");
            Shake();
        }
    }

    private void Shake (){
        Shake loginAnimation = new Shake(loginField);
        Shake passwordAnimation = new Shake(passwordField);
        loginAnimation.playAnimation();
        passwordAnimation.playAnimation();
    }

}
