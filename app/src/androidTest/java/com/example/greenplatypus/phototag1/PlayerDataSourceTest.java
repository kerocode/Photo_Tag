package com.example.greenplatypus.phototag1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.test.runner.AndroidJUnit4;
import android.test.ServiceTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

/**
 * Created by kh on 11/13/15.
 *
 * Based roughly on http://developer.android.com/guide/components/bound-services.html
 */
@RunWith(AndroidJUnit4.class)
public class PlayerDataSourceTest extends ServiceTestCase<PlayerDataSource> {
    @Mock
    PlayerDataSource playerDataBase;
    private String TAG = "PlayerDataSourceTest";
    private boolean isBound = false;

    private ServiceConnection playerDataSourceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerDataSource.LocalBinder binder = (PlayerDataSource.LocalBinder) service;
            playerDataBase = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    public PlayerDataSourceTest() { super(PlayerDataSource.class);  }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    // Adapted from http://alvinalexander.com/java/jwarehouse/android-examples/platforms
    // /android-6/samples/ApiDemos/tests/src/com/example/android/apis/app/LocalServiceTest.java.shtml
    // Start the service before each test
//    @Before
//    public void startPlayerDataSource(){
//        isBound = true;
//        Intent startIntent = new Intent();
//        startIntent.setClass(getSystemContext(), PlayerDataSource.class);
//        startService( startIntent );
//    }

//    @BeforeClass
//    protected void onStart() {
//        super.onStart();
//        Intent intent = new Intent(this, PlayerDataSource.class);
//        bindService(intent, playerDataSourceConnection, Context.BIND_AUTO_CREATE);
//        Log.i(TAG, "Connected to PlayerDataSource");
//    }

//    /**
//     * Test basic startup/shutdown of Service
//     * http://stackoverflow.com/questions/2300029/servicetestcaset-getservice
//     */
//    @Test
//    public void testStartable() {
//        Intent startIntent = new Intent();
//        startIntent.setClass(getContext(), PlayerDataSource.class);
//        startService(startIntent);
//        assertNotNull(getService());
//    }

    /**
     * Test binding to service
     * http://stackoverflow.com/questions/2300029/servicetestcaset-getservice
     */
    @Before
    public void testBindable() {
        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), PlayerDataSource.class);
        IBinder binder = bindService(startIntent);
        assertNotNull(binder);
    }

    @Test
    public void addPlayerToPlayerDataSource(){
//        Intent intent = new Intent();
//        intent.setClass(getContext(), PlayerDataSource.class);
//        //bindService(intent, playerDataSourceConnection, Context.BIND_AUTO_CREATE);
//        bindService(intent);

        if (isBound) {
            Player BOB= new Player("Bob");
            // First, add a player to the database. Note that its an anonymous object.
            playerDataBase.addPlayer( BOB );

            // Then, pull it back out.
            Player bob = playerDataBase.getPlayer("Bob");

            // Finally, test that its the right player
            Assert.assertTrue( bob.getName().equals("Bob") );
        }
        else {
            Assert.fail(TAG + " addPlayerToPlayerDataSource did not connect to PlayerDataSource");
        }

    }


    @Test
    public void updatePlayerInPlayerDataSource(){
        if (isBound) {
            {
                // Create a new player and add to database
                Player joe = new Player("Joe");
                playerDataBase.addPlayer(joe);

                // Change something about the player
                joe.setScore(999);

                // Update it in database
                playerDataBase.updatePlayer(joe);
            }

            // Check that changes were made
            Player joe2 = playerDataBase.getPlayer("Joe");
            assertTrue(joe2.getScore() == 999);

        }
        else {
            Assert.fail(TAG + " addPlayerToPlayerDataSource did not connect to PlayerDataSource");
        }
    }

    @Test
    public void getPlayersFromPlayerDataSource(){
        // Add some players to the database
        playerDataBase.addPlayer( new Player("Adam"));
        playerDataBase.addPlayer( new Player("Eve" ));

        // Then pull them out
        List players = playerDataBase.getPlayers();

        // Test
        assertTrue( players.size() == 2 );
        Player p = (Player) players.get(0);
        assertTrue( p.getName() == "Adam" || p.getName() == "Eve");
    }

//    @AfterClass
//    protected void onStop(){
//        super.onStop();
//        if (isBound) {
//            unbindService(playerDataSourceConnection);
//            isBound = false;
//        }
//    }

}