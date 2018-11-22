package utils;

public class StringUtil {

    public static  boolean isEmptyString(String inputString){
        if(inputString == null || inputString.trim().equals("")){
            return true;
        }
        else{
            return false;
        }

    }
}
