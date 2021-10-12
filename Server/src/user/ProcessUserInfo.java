package user;

import java.io.DataInputStream;
import java.io.IOException;

public class ProcessUserInfo {

    private ProcessUserInfo (){

    }

    public static void processUserInfo (User user) throws IOException {
        var in = new DataInputStream(user.getSocket().getInputStream());
        String userInfo = in.readUTF();
        char [] userInfoArr = userInfo.toCharArray();
        StringBuffer fieldUser = new StringBuffer();
        int i = 0;
        for (; i < userInfoArr.length; i++){
            if (userInfoArr[i] != 1){
                fieldUser = fieldUser.append(userInfoArr[i]);
            } else {
                user.setName(fieldUser.toString());
                fieldUser.setLength(0);
                break;
            }
        }
        i++;
        for (; i < userInfoArr.length; i++){
            if (userInfoArr[i] != 1){
                fieldUser = fieldUser.append(userInfoArr[i]);
            } else {
                user.setSurname(fieldUser.toString());
                fieldUser.setLength(0);
                break;
            }
        }
        i++;
        for (; i < userInfoArr.length; i++){
            if (userInfoArr[i] != 1){
                fieldUser = fieldUser.append(userInfoArr[i]);
            } else {
                user.setLogin(fieldUser.toString());
                fieldUser.setLength(0);
                break;
            }
        }
        i++;
        for (; i < userInfoArr.length; i++){
            if (userInfoArr[i] != 1){
                fieldUser = fieldUser.append(userInfoArr[i]);
            } else {
                user.setGender(fieldUser.toString());
                fieldUser.setLength(0);
                break;
            }
        }
        i++;
        for (; i < userInfoArr.length; i++){
            if (userInfoArr[i] != 1){
                fieldUser = fieldUser.append(userInfoArr[i]);
            } else {
                user.setLocation(fieldUser.toString());
                fieldUser.setLength(0);
                break;
            }
        }
    }
}
