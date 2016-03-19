package com.example.greenplatypus.phototag1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;

import com.example.greenplatypus.phototag1.model.PhotoTagApp;

import java.util.List;

/**
 * Created by Casey White on 10/26/2015.
 */

public class Navigator {
    /**
     * Singleton instance of this class
     */
    private static Navigator singleton = null;

    public enum Screen {LOGIN, TAKE_SELFIE, RETAKE_SELFIE, WANTED, TAG_FOE};

    /**
     * @return singleton instanve of Navigato
     */

    public static Navigator getInstance() {
        if (singleton == null) {
            singleton = new Navigator();
        }
        return singleton;
    }

    /**
     * Private constructor for ensuring Singleton
     */

    private Navigator() {

    }

    public void navigateToLogin(final AppCompatActivity fromActivity) {
        Intent intent = new Intent(fromActivity, LoginScene.class);
        fromActivity.startActivity(intent);
    }

    public void navigateToWanted(final AppCompatActivity fromActivity) {
//        if (PhotoTagApp.isLoggedIn()) {
            Intent intent = new Intent(fromActivity, WantedScene.class);
            fromActivity.startActivity(intent);
//        }
    }

    public void navigateToTakeSelfie(final AppCompatActivity fromActivity) {
        if (PhotoTagApp.isLoggedIn()) {
            Intent intent = new Intent(fromActivity, TakeSelfie.class);
            fromActivity.startActivity(intent);
        }
    }

    public void navigateToTagFoe(final AppCompatActivity fromActivity) {
        if (PhotoTagApp.isLoggedIn()) {
            Intent intent = new Intent(fromActivity, TagFoe.class);
            fromActivity.startActivity(intent);
        }
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


}
