package xyz.youngbin.shipped.data;

/**
 * Created by youngbin on 16. 2. 19.
 */
public class Util {
    public Util(){}
    // CONVERTER
    // http://stackoverflow.com/questions/9053685/android-sqlite-saving-string-array
    private static String strSeparator = "__,__";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
