package myApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myApp.file.MyFile;
import myApp.user.User;

import java.io.File;
import java.io.IOException;

public class App extends Application {

    private static Stage stage;
    private static MyScene myScene;
    private static final MyFile file = new MyFile();

    @Override
    public void start(Stage stage) throws IOException {
        myScene = new MyScene(loadFXML("Authorization"), 700, 400);
        stage.setTitle("Чат");
        stage.setScene(myScene);
        stage.show();
    }

    public static File readFile (){
        return file.readFile(stage);
    }

    public static void setRoot(String fxml) throws IOException {
        //scene.setRoot(loadFXML(fxml));
        myScene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println(new FXMLLoader(App.class.getResource(fxml + ".fxml")));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setUser (User user){
        myScene.setUser(user);
    }

    public static User getUser (){
        return MyScene.getUser();
    }
}

class MyScene extends Scene {
    private static User user;

    public MyScene(Parent root, double width, double height) {
        super(root, width, height);
    }

    public void setUser (User user) {
        this.user = user;
    }

    public static User getUser(){
        return user;
    }

}