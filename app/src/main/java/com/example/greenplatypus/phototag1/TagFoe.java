package com.example.greenplatypus.phototag1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.greenplatypus.phototag1.utilites.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User is shown a full screen image of players selfie
 * top left corner name is displayed
 * top right corner score is displayed
 * bottom of screen is 'tag foe' button that pulls up back camera
 * pic and selected player selfie sent to face++
 * after pic is taken user is passed back to main screen
 */

public class TagFoe extends BasePhotoTagActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final String APP_TAG = "TagFoe";
    String mCurrentPhotoPath;

    private ImageView mImageView;
    private int [] selectedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_foe);

        selectedImg = new int [1];
        // get intent data
        Intent intent = getIntent();

        if(intent != null && intent.getExtras() != null) {
            Log.d("TagFoe", "On call back? intent exists now");

            // Selected image id
            int position = intent.getExtras().getInt("id");

            ImageAdapter imageAdapter = new ImageAdapter(this);

            mImageView = (ImageView) findViewById(R.id.full_image_view);
            Utils.debugNotNull("TagFoe", "imageView is not null ", mImageView);
            mImageView.setImageResource(imageAdapter.mThumbIds[position]);
            selectedImg[0] = imageAdapter.mThumbIds[position];
        }
    }

    @Override
    public  void onBackPressed(){

        navigator.navigateToWanted(this);
    }

    public void takePhotoSelfie(View view) {

        dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 &&(resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER)) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView = (ImageView) findViewById(R.id.full_image_view);
            mImageView.setImageBitmap(imageBitmap);
        }

    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
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

    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d(APP_TAG, "failed to create directory");
            }


            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


}
