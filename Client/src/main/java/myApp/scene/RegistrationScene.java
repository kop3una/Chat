package myApp.scene;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import myApp.App;
import myApp.animation.Shake;
import myApp.dateBase.DateBaseHandler;
import myApp.user.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationScene {

    DateBaseHandler dbHandler = new DateBaseHandler();

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    private TextField signUpLogin;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private CheckBox manCheckBox;

    @FXML
    private CheckBox womanCheckBox;

    @FXML
    private TextField signUpLocation;

    @FXML
    void initialize()  {
        manCheckBox.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> womanCheckBox.setSelected(false));
        womanCheckBox.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> manCheckBox.setSelected(false));
    }

    @FXML
    private void registration() throws SQLException, NoSuchAlgorithmException, IOException {

        User user = new User(signUpName.getText().trim(),signUpSurname.getText().trim(),signUpLogin.getText().trim(),
                signUpPassword.getText().trim(),getGender(manCheckBox,womanCheckBox).trim(),signUpLocation.getText().trim());
        ResultSet resSet = dbHandler.chekLogin(user);
        int count = 0;
        while (resSet.next()){
            count++;
        }
        if ( (count >= 1) || (user.getPassword().trim().equals("")) || (user.getName().trim().equals("")) || (user.getLogin().trim().equals("friend")) ){
            Shake();
        } else {
            dbHandler.signUpUser(user);
            App.setRoot("Authorization");
        }
    }

    @FXML
    private void exit () throws IOException {
        App.setRoot("Authorization");
    }

    private String getGender(CheckBox manCheckBox, CheckBox womanCheckBox) {
        if (manCheckBox.isSelected()) {
            return "Мужской";
        }
        if (womanCheckBox.isSelected()) {
            return "Женский";
        }
        return "";
    }

    private void Shake(){
        Shake loginAnimation = new Shake(signUpLogin);
        Shake passwordAnimation = new Shake(signUpPassword);
        Shake nameAnimation = new Shake(signUpName);
        loginAnimation.playAnimation();
        passwordAnimation.playAnimation();
        nameAnimation.playAnimation();
    }
}

