package myApp.file;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class MyFile {
    FileChooser fileChooser = new FileChooser();

    public File readFile(Stage stage) {
        fileChooser.setTitle("Выбор файла");
        return fileChooser.showOpenDialog(stage);
    }

    public byte[] calcByteArr(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            buffer = new byte[fileIn.available()];
            fileIn.read(buffer, 0, buffer.length);
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void writeInFile(byte[] buffer, String userLogin,String login, String fileName) {
        try {
            File dirUser;
            File dirChat;
            dirUser = new File("..\\"+userLogin);
            if (dirUser.mkdir()){
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is also do it");
            }

           // dirChat = new File(dirUser.getName()+"\\"+login);
            if (login.equals("")) {
                dirChat = new File("..\\"+dirUser.getName()+"\\Общий чат");
            } else {
                dirChat = new File("..\\"+dirUser.getName()+"\\"+login);
            }

            if (dirChat.mkdir()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is also do it");
            }
            File newFile = new File(dirChat + "\\"+ fileName);
            boolean created = newFile.createNewFile();
            if (created) {
                System.out.println("File has been created");
                FileOutputStream file = new FileOutputStream(dirChat + "\\"+ fileName);
                file.write(buffer, 0, buffer.length);
            } else {
                created = false;
                int countFile = 1;
                while (!created){
                    newFile = new File(dirChat + "\\("+countFile+") "+fileName);
                    countFile++;
                    created = newFile.createNewFile();
                }
                countFile--;
                FileOutputStream file = new FileOutputStream(dirChat + "\\("+countFile+") "+fileName);
                file.write(buffer,0, buffer.length);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
