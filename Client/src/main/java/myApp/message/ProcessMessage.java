package myApp.message;

public  class ProcessMessage {

    private ProcessMessage(){

    }

    public static String getFirstLogin (String message){
        StringBuffer login = new StringBuffer();
        char [] messageArr = message.toCharArray();
        int indexMessage = 0;
        while ( (messageArr[indexMessage] != (char) 1) && (messageArr[indexMessage]!= (char) 2) ){
            indexMessage++;
        }
        if (messageArr[indexMessage] == (char)1 ){
            return "";
        } else if (messageArr[indexMessage] == (char)2 ){
            indexMessage++;
            for (; (indexMessage < messageArr.length) && (messageArr[indexMessage] !=(char) 2); indexMessage++){
                login.append(messageArr[indexMessage]);
            }
            return login.toString();
        }
        return "";
    }

    public static String getSecondLogin (String message){
        StringBuffer login = new StringBuffer();
        char [] messageArr = message.toCharArray();
        int indexMessage = 0;
        while ( (messageArr[indexMessage] != (char) 1) && (messageArr[indexMessage]!= (char) 2) ){
            indexMessage++;
        }
        if (messageArr[indexMessage] == (char)1 ){
            return "";
        } else if (messageArr[indexMessage] == (char)2 ){
            indexMessage++;
            for (; (indexMessage < messageArr.length) && (messageArr[indexMessage] !=(char) 2); indexMessage++){
            }
            indexMessage++;
            for (; (indexMessage < messageArr.length) && (messageArr[indexMessage] !=(char) 2); indexMessage++){
                login.append(messageArr[indexMessage]);
            }
            return login.toString();
        }
        return "";
    }

    public static String getMessage (String message){
        char [] messageArr = message.toCharArray();
        StringBuffer messageNew = new StringBuffer();
        int indexMessage = 0;
        while ( (messageArr[indexMessage] != (char) 1) && (messageArr[indexMessage]!= (char) 2) ){
            messageNew.append(messageArr[indexMessage]);
            indexMessage++;
        }

        if (messageArr[indexMessage] == (char)1){
            indexMessage++;
            while (indexMessage < message.length()){
                messageNew.append(messageArr[indexMessage]);
                indexMessage++;
            }
            return messageNew.toString();
        } else if (messageArr[indexMessage] == (char) 2){
            indexMessage++;
            while (messageArr[indexMessage] != (char)2){
                indexMessage++;
            }
            indexMessage++;
            while (messageArr[indexMessage] != (char)2){
                indexMessage++;
            }
            indexMessage++;
            while (indexMessage < message.length()){
                messageNew.append(messageArr[indexMessage]);
                indexMessage++;
            }
            return messageNew.toString();
        }
        return "";
    }

    public static String friendRequest (String message){
        char [] messageArr = message.toCharArray();
        StringBuffer messageNew = new StringBuffer();
        int indexMessage = 0;
        while ( (messageArr[indexMessage] != (char) 1) && (messageArr[indexMessage]!= (char) 2) ){
            messageNew.append(messageArr[indexMessage]);
            indexMessage++;
        }
        indexMessage++;
        messageNew.append("@");
        while ((messageArr[indexMessage] != (char) 1) && (messageArr[indexMessage]!= (char) 2)){
            messageNew.append(messageArr[indexMessage]);
            indexMessage++;
        }

        messageNew.append(" хочет отправить вам сообщение, добавьте его в друзья");

       return messageNew.toString();
    }

    public static String friendRequestFile (String message, String login){
        StringBuffer date = new StringBuffer(message);
        date.delete(message.indexOf(""+(char)4),message.length());
        return date.append(" @"+login+" хочет отправить вам сообщение, добавьте его в друзья").toString();
    }

    public static int getLength (String message){
        StringBuffer length = new StringBuffer(message);
        length.delete(0,length.indexOf(""+(char)4)+1);
        length.delete(0,length.indexOf(""+(char)4)+1);
        return Integer.parseInt(length.substring(0,length.indexOf(""+(char)4)));
    }

    public static String getFileName (String message){
        StringBuffer length = new StringBuffer(message);
        length.delete(0,length.indexOf(""+(char)4)+1);
        length.delete(0,length.indexOf(""+(char)4)+1);
        length.delete(0,length.indexOf(""+(char)4)+1);
        return length.substring(0,length.indexOf(""+(char)4));
    }

    public static boolean isFileMessage (String message){
        if (message.indexOf((char) 4 ) == -1){
            return false;
        } else {
            return true;
        }
    }

    private static String getMessageExcFile (String message){
        StringBuffer newMessage = new StringBuffer(message);
        newMessage.delete(0,newMessage.indexOf(""+(char)4)+1);
        newMessage.delete(newMessage.indexOf(""+(char) 4),newMessage.length()-1);
        return newMessage.toString();
    }

    public static String getFirstLoginFile (String message) {
        return (getFirstLogin(getMessageExcFile(message)));
    }

    public static String getSecondLoginFile (String message) {
        return (getSecondLogin(getMessageExcFile(message)));
    }

    public static String getMessageFile (String message){
        StringBuffer date = new StringBuffer(message);
        date.delete(message.indexOf(""+(char)4),message.length());
        String newMessage = new String(message);
        newMessage = getMessageExcFile(message);
        newMessage = getMessage(newMessage);
        return date.append(newMessage).toString();
    }
}
