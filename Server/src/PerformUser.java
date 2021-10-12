import date.Date;
import message.ProcessMessage;
import user.User;

import java.io.*;
import java.util.ArrayList;

public class PerformUser implements Runnable {
    private final ArrayList<User> usersList;
    private final User user;

    public PerformUser(User user, ArrayList<User> usersList) {
        this.user = user;
        this.usersList = usersList;
    }

    public void run() {
        try {
            var in = new DataInputStream(user.getSocket().getInputStream());
            while (true){
                String line = in.readUTF();
                System.out.println(line);
                if (line.charAt(0) != (char) 4){
                    sendMail(usersList,line);
                } else {
                    int bytesRead = 0;
                    byte [] byteBuffer = new byte[ProcessMessage.getLength(line)];
                    BufferedInputStream inBuff = new BufferedInputStream(user.getSocket().getInputStream(),byteBuffer.length);
                    System.out.println(inBuff.available());
                    while (bytesRead != byteBuffer.length){
                        int bytesReadForOnes = inBuff.read(byteBuffer, bytesRead, byteBuffer.length-bytesRead);
                        System.out.println(bytesReadForOnes);
                        bytesRead += bytesReadForOnes;
                    }

                    sendMailAndFile(usersList,line,byteBuffer);
                }


            }
        } catch (IOException e) {
            System.out.println("Соединение с пользователем разорвано");
        }
    }

    private void sendMail(ArrayList<User> users, String line) {
        for (int i = 0; i < users.size(); i++) {
            try {
                var out = new DataOutputStream(users.get(i).getSocket().getOutputStream());
                //out.println(this.user.getNameSurname());
                out.writeUTF(Date.getTime() + " " + this.user.getNameSurname() + line);
                out.flush();
            } catch (IOException e) {
                System.out.println("Удаление отключившегося пользователя");
                usersList.remove(users.get(i));
                i--;
            }
        }
    }

    private void sendMailAndFile (ArrayList<User> users, String line, byte [] buffer){
        for (int i = 0; i < users.size(); i++) {
            try {
                var out = new DataOutputStream(users.get(i).getSocket().getOutputStream());
                var outBuff = new BufferedOutputStream(users.get(i).getSocket().getOutputStream(), buffer.length);
                out.writeUTF(Date.getTime() + " " + this.user.getNameSurname() + line);
                out.flush();
                outBuff.write(buffer,0, buffer.length);
                outBuff.flush();
            } catch (IOException e) {
                System.out.println("Удаление отключившегося пользователя");
                usersList.remove(users.get(i));
                i--;
            }
        }
    }
}
