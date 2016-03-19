package com.example.greenplatypus.phototag1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.greenplatypus.phototag1.model.PhotoAlbum;
import com.example.greenplatypus.phototag1.model.PhotoTagApp;
import com.example.greenplatypus.phototag1.utilites.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * User is to take a Selfie for game
 * Possible UI overlay on front facing Camera
 * Button that takes user to front facing Camera
 * after selfie is taken, use face++ to check that picture is of a face
 * if positive pass user to main page
 * if negative redisplay take selfie page
 *
 *  An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class TakeSelfie extends BasePhotoTagActivity {

    private static final int ACTION_TAKE_PHOTO_S = 2;
    public Bitmap mImageBitmap;
    private ImageView mImageView;
    private String mCurrentPhotoPath;

    private PhotoAlbum photoAlbum = null;

    static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void takeSelfieWithCamera(View v) {
        dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.os.Debug.startMethodTracing();

        setContentView(R.layout.activity_take_selfie);

        mImageView = (ImageView) findViewById(R.id.takeSelfieImageView);
        photoAlbum = PhotoTagApp.getPlayerAlbum();
        mImageBitmap = null;
        Button.OnClickListener navigateOnClickListener =
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        navigateToWanted();
                    }
                };
        Button navigateButton = (Button) findViewById(R.id.buttonTakeSelfieToWanted);
       /* if (navigateButton == null) {
            Log.e("TakeSelfie", "Missing navigation button!");
        } else {*/
            navigateButton.setOnClickListener(navigateOnClickListener);
        //}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onBackPressed() {
        navigator.navigateToLogin(this);
    }

    public void navigateToWanted() {
        navigator.navigateToWanted(this);
    }

    /**
     * Handles camera return call
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TakeSelfie", "onActivityResult " + requestCode + " " + resultCode + " " + data);
        if (requestCode == ACTION_TAKE_PHOTO_S && (resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER)) {
            Log.d("TakeSelfie", "setting image now");
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
            Utils.debugNotNull("TakeSelfie", "data passed? ", mImageBitmap);
            mImageView.setImageBitmap(mImageBitmap);
        }
    }

    // Some lifecycle callbacks so that the image can survive orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELFIE_STORAGE_KEY, mImageBitmap);
        outState.putBoolean(SELFIE_EXISTS_STORAGE_KEY, (mImageBitmap != null));
        super.onSaveInstanceState(outState);
    }

    /**
     * Handler for restoring state
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageBitmap = savedInstanceState.getParcelable(SELFIE_STORAGE_KEY);

        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(
                savedInstanceState.getBoolean(SELFIE_EXISTS_STORAGE_KEY) ?
                        ImageView.VISIBLE : ImageView.INVISIBLE
        );

    }

    Player getUser() {
        return PhotoTagApp.getUser();
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, actionCode);
        }
    }

    private void updateUser(Bitmap selfieFile) {
        if (selfieFile == null) {
            throw new IllegalArgumentException("no selfieFile to save");
        } else {
            Player user = getUser();
            user.setSelfie(selfieFile);
        }
    }

    private void handleSmallCameraPhoto(Intent intent) {

        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
        /*Bundle extras = intent.getExtras();
        mImageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(mImageBitmap);*/
        updateUser(mImageBitmap);
    }

    /**
     * Keep going looking into this
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = makeSelfieFileName();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    String makeSelfieFileName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return imageFileName;
    }
}
