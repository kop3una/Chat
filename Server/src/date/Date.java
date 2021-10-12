package date;

import java.nio.charset.StandardCharsets;

public class Date {
    public static String getTime (){
        java.util.Date date = new java.util.Date();
        String dateStr = (getHours(date)+":" +getMinute(date)+":"+getSeconds(date));
        return new String(dateStr.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8);
    }

    private static String getMinute (java.util.Date date){
        return (date.getMinutes()<10) ? "0"+date.getMinutes(): ""+date.getMinutes();
    }
    private static String getHours (java.util.Date date){
        return (date.getHours()<10) ? "0"+date.getHours(): ""+date.getHours();
    }
    private static String getSeconds (java.util.Date date){
        return (date.getSeconds()<10) ? "0"+date.getSeconds(): ""+date.getSeconds();
    }
}
