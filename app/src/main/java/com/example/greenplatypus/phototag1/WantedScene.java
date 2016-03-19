package com.example.greenplatypus.phototag1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by kero on 10/19/2015.
 *
 * Displays Player data from database
 * On a grid, 2x4 to 3x6 before side scrolling
 * thumbnails of players selfie with user selfie displayed top left
 * overlayed on every thumbnail, players username and score
 * User taps top left thumbnail, user is passed to RetakeSelfie screen
 * User taps any other thumbnail, user is taken to TagFoe screen for that player
 */

public class WantedScene extends BasePhotoTagActivity {
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted_scene);
        passedState = savedInstanceState;
        gv = (GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new ImageAdapter(this));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), TagFoe.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }

    public  GridView  getGridView(){
        return gv;
    }
    @Override
    public  void onBackPressed(){
        navigator.navigateToTakeSelfie(this);
    }


}
/*
* you need to get this scene link to takeSelfie scene so after taking selfie you should saved and then go to WantedScene
* alse you need to upload selected character (player) who been choosed from wanted scence to face++ to anyalized  or you can saved some where till
* user tag a person and then upload both to face++ to compare then and return results
* please look at Face++ documantion at face plus plus website
* */