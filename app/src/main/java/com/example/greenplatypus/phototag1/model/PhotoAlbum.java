package com.example.greenplatypus.phototag1.model;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.greenplatypus.phototag1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kero on 10/16/2015.
 */
public class PhotoAlbum {
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    public static final String CAMERA_DIR = "/dcim/";
    private String albumName = "PhotoTagPlayerAlbum";

    private File playerAlbum = null;

    public PhotoAlbum(){

    }

    public File getAlbumStorageDir(){
        if(albumName == null){
            throw new IllegalStateException("Missing album name");
        }
        if(playerAlbum == null){
            this.playerAlbum =  new File(
                    Environment.getExternalStorageDirectory()
                            + CAMERA_DIR
                            + albumName
            );
        }
        return playerAlbum;
    }

    static final int MEDIUM_QUALITY = 50;

    public File saveBitMapToFile(final Bitmap selfie){
        if(selfie == null){
            throw new IllegalArgumentException("Missing selfie to save");
        }

        File selfieFile = null;
        try {
            selfieFile = createImageFile();
            OutputStream outStream = new FileOutputStream(selfieFile);
            selfie.compress(Bitmap.CompressFormat.PNG, MEDIUM_QUALITY, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selfieFile;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";

        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = getAlbumStorageDir();
            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
//            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
            Log.v("PhotTagApp","External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }
}

