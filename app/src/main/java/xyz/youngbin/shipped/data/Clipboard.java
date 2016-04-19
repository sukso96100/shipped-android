package xyz.youngbin.shipped.data;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youngbin on 16. 4. 19.
 */
public class Clipboard {

    public static String getFromClipboard(Context mContext){
        Log.d("CLIPDATA", "Getting Data from Clipboard");

        //Get Clipboard Instance
        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        //Get Data from Clipboard
        Log.d("CLIPDATA", clipboard.getPrimaryClip().toString());

        if(clipboard.hasPrimaryClip()
                &&clipboard.getPrimaryClipDescription()
                .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
            //If Clip Data is Plain Text
            //Save it to plainString
            String plainString = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            Log.d("CLIPDATA", plainString);

            //Find tracking number using pattern matcher
            Pattern pattern = Pattern.compile("(\\d+){8,}");
            Matcher matcher = pattern.matcher(plainString);

            ArrayList<String> Numbers = new ArrayList<>();
            while (matcher.find()){
                Log.d("CLIPDATA","Pattern Matcher +++++");
                Log.d("CLIPDATA", matcher.group());
                Numbers.add(matcher.group());
                Log.d("CLIPDATA","+++++ Pattern Matcher");
            }
            return Numbers.get(0);
        }
        return null;
    }
}
