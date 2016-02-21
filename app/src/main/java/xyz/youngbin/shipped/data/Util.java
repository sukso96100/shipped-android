package xyz.youngbin.shipped.data;

import android.content.Context;

import xyz.youngbin.shipped.R;

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

    public static String typeValToTypeString(Context mContext, String TypeVal){
        String[] mTypeVal = mContext.getResources().getStringArray(R.array.type_val);
        String[] mType = mContext.getResources().getStringArray(R.array.type);
        int index = 0;
        for(int i=0; i<mTypeVal.length; i++){
            if(mTypeVal[i].equals(TypeVal)){
                index = i;
                break;
            }
        }
        return mType[index];
    }

    public static String carrierValtoCarrierString(Context mContext, String TypeVal, String CarrierVal){
        String[] mCarrierVal;
        String[] mCarrier;
        switch (TypeVal){
            default:
                mCarrierVal = mContext.getResources().getStringArray(R.array.carriers_mails_val);
                mCarrier = mContext.getResources().getStringArray(R.array.carriers_mails);
                break;
            case "mails":
                mCarrierVal = mContext.getResources().getStringArray(R.array.carriers_mails_val);
                mCarrier = mContext.getResources().getStringArray(R.array.carriers_mails);
                break;
        }

        int index = 0;
        for(int i=0; i<mCarrierVal.length; i++){
            if(mCarrierVal[i].equals(CarrierVal)){
                index = i;
                break;
            }
        }
        return mCarrier[index];
    }
}
