package message;

public class ProcessMessage {

    public static int getLength (String message){
        StringBuffer length = new StringBuffer(message);
        length.deleteCharAt(0);
        length.delete(0,length.indexOf(""+(char)4)+1);
        return Integer.parseInt(length.substring(0,length.indexOf(""+(char)4)));
    }
}