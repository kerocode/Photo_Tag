package com.example.greenplatypus.phototag1;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import android.graphics.Bitmap;

public class PlayerDataSource extends Service {

    // Binder object to give to client Activities
    private final IBinder playerDataSourceBinder = new LocalBinder();

    // THE DATABASE (and its helper)
    private SQLiteDatabase playerDatabase;
    private PlayerDatabaseHelper playerDatabaseHelper;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "players.db";
    public static final String TABLE_PLAYERS = "players";
    public static final String COL_NAME = "name";
    public static final String COL_SCORE = "score";
    public static final String COL_SELFIE = "selfie";

    public static final int INDEX_NAME = 0;
    public static final int INDEX_SCORE = 1;
    public static final int INDEX_SELFIE = 2;


    // Constructor. I don't know why it needs a Context....?
    public PlayerDataSource(Context context) {
        playerDatabaseHelper = new PlayerDatabaseHelper(context);
    }

    // Opener and Closer
    public void open() throws SQLException {
        playerDatabase = playerDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        playerDatabaseHelper.close();
    }


    // Bondage Gear ///////////////////////////////////////////////////////////////////
    @Override
    public IBinder onBind(Intent intent) {
        return playerDataSourceBinder;
    }

    // Binder class that creates Binder reference to give to other activities
    public class LocalBinder extends Binder {
        PlayerDataSource getService() {
            // returns this instance of the DataSource so clients can use it
            return PlayerDataSource.this;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////


    // Helper Methods for the publicly facing methods

    private ContentValues prepareValuesForPlayer(Player p) {
        // ContentValues makes loading a record really easy
        ContentValues values = new ContentValues();

        // Prepare name and score
        values.put("name", p.getName());
        values.put("score", p.getScore());

        // Prepare selfie for loading into database
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        p.getSelfie().compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] selfie = baos.toByteArray();
        values.put("selfie", selfie);

        return values;
    }

    private Player playerFactory(Cursor cursor) {
        Player p = new Player(cursor.getString(INDEX_NAME));
        p.setScore(cursor.getInt(INDEX_SCORE));

        if (cursor.getBlob(INDEX_SELFIE) != null)

        {
            byte[] selfie = cursor.getBlob(INDEX_SELFIE);
            if (selfie != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(selfie);

                p.setSelfie(BitmapFactory.decodeStream(bais));
            }
        }

        return p;
    }


    // PUBLIC INTERFACE METHODS ///////////////////////////////////////////////////////////////

    // Adapted from pages 97,188 of "Learning Mobile App Development" by Iverson & Eierman
    public boolean addPlayer(Player p) {
        boolean didItWork = false;

        try {
            ContentValues startValues = prepareValuesForPlayer(p);

            // Now add these values to the database
            didItWork = playerDatabase.insert(TABLE_PLAYERS, null, startValues) > 0;

        }catch (Exception e) {}

        return didItWork;
    }

    public Player getPlayer(String name) {
        Player player;
        try {
            //TODO: replace with single db.query() call
            String query = "SELECT " + COL_NAME + "," + COL_SCORE + "," + COL_SELFIE +
                    "FROM " + TABLE_PLAYERS + "WHERE " + COL_NAME + "=" + name +";";
            Cursor cursor = playerDatabase.rawQuery(query, null);

            player = playerFactory(cursor);

        } catch (Exception e ) { return  null; }

        return player;
    }

    // Adapted from pages 117 of "Learning Mobile App Development" by Iverson & Eierman
    public List<Player> getPlayers() {
        List<Player> players = new LinkedList<Player>();

        try {
            String query = "SELECT * FROM " + TABLE_PLAYERS + ";";
            Cursor cursor = playerDatabase.rawQuery(query, null);

            cursor.moveToFirst();

            while( !cursor.isAfterLast() ){
                Player p = playerFactory(cursor);
                players.add(p);
            }

        } catch (Exception e) {
            players = new LinkedList<Player>();
        }

        return players;
    }

    public void removePlayer(String name) {
        String query = "DELETE FROM " + TABLE_PLAYERS + " WHERE " + COL_NAME + "=" + name + ";";
        playerDatabase.rawQuery(query, null);
    }

    // Adapted from pages 97,188 of "Learning Mobile App Development" by Iverson & Eierman
    public boolean updatePlayer(Player p) {

        boolean didItWork = false;

        try {
            ContentValues updateValues = prepareValuesForPlayer(p);

            didItWork = playerDatabase.update(TABLE_PLAYERS, updateValues,
                    "name="+p.getName(), null) > 0;
        } catch (Exception e) {}

        return didItWork;
    }






    //////////////////////////////////////////////////////////////////////////////////////////////

    public class PlayerDatabaseHelper extends SQLiteOpenHelper {

        public void imAlive(){
            Log.i("HEY LISTEN", "PlayerDatabaseHelper is alive!");
        }

        /**
         * Create a helper object to create, open, and/or manage a database.
         * This method always returns very quickly.  The database is not actually
         * created or opened until one of {@link #getWritableDatabase} or
         * {@link #getReadableDatabase} is called.
         *
         */
        public PlayerDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "Create TABLE " + TABLE_PLAYERS + "(" +
                    COL_NAME + " TEXT PRIMARY KEY, " +
                    COL_SCORE + " INTEGER, " +
                    COL_SELFIE + " BLOB " +
                    ");";

            db.execSQL(query);
        }

        // This is required. It wipes the database if the scheme changes, not a problem for us.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP_TABLE_IF_EXISTS" + TABLE_PLAYERS);
            onCreate(db);
        }
    }
}
