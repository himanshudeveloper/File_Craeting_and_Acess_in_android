package com.example.app43;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class SDCARDChecker {


    public static void checkWheatherExternalStorageAvailableOrNot(Context context){

        boolean isExternalStorageAvailable = false;
        boolean isExternalStorageWritable = false;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)){
            // we can read and write the media
            isExternalStorageAvailable = isExternalStorageWritable = true;
            Toast.makeText(context , "read and write", Toast.LENGTH_SHORT).show();
        }else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            // we can only read the media
            isExternalStorageAvailable = true;
            isExternalStorageWritable = false;

            Toast.makeText(context , "read only ", Toast.LENGTH_SHORT).show();
        }else  {
            // we can neither read nor write
            isExternalStorageAvailable = isExternalStorageWritable = false;
            Toast.makeText(context, "neither read nor write", Toast.LENGTH_SHORT).show();
        }
    }





}
