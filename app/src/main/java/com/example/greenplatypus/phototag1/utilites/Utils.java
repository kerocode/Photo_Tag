package com.example.greenplatypus.phototag1.utilites;

import android.util.Log;

/**
 * Created by Casey White on 10/26/2015.
 */
public class Utils {

    /**
     * Convenience method for debug logging
     * @param tag
     * @param message
     */
    public static void debug(final String tag, final String message){
        Log.d(tag, message);
    }

    /**
     * Convenience method for debug logging for checking an object instances instance and logging it cleanly
     * Work around for androids non Java casting of boolean to string
     * @param tag
     * @param message
     * @param candidate tested for null/existence
     */
    public static void debugNotNull(final String tag, final String message, final Object candidate){
        debug(tag, message +((candidate==null)?" true":" false"));
    }
}
