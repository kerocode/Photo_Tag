package com.example.greenplatypus.phototag1.model;

import com.example.greenplatypus.phototag1.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Casey White on 10/26/2015.
 */
public class PhotoTagApp {

    static PhotoAlbum playerAlbum = new PhotoAlbum();

    static Player user;

    static HashMap<String, Player> players = new LinkedHashMap<>();

    private static boolean initialized = false;

    private static void prepared(){
        if(!initialized){
            loadPlayers();
        }
    }

    private PhotoTagApp() {
    }

    /**
     * for testing pull players from net
     */
    public static void loadPlayers(){
//        players = TetDataFactory.
        players = TestDataFactory.getPlayers();
    }

    public static boolean login(final String userName, final String password){
        prepared();
        System.out.println("Player login attempt: player:"+userName+" pwd:"+password);
        if(players.containsKey(userName)){
//            Player candidate = players.get(userName);
//            if(candidate.getPassword().equals(password)){
//                user = candidate;
//            }
            return false;
        } else {
            registerUser(userName, password);
            return true;
        }
    }
    static void registerUser(final String userName, final String password){
        user = new Player(userName, password);
        players.put(userName, user);
    }

    public static Player getUser() {
        prepared();
        return user;
    }

    public static PhotoAlbum getPlayerAlbum() {
        return playerAlbum;
    }

    public static boolean isLoggedIn(){
//        return user != null;
        return true;
    }

}
