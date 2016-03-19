package com.example.greenplatypus.phototag1.model;

import com.example.greenplatypus.phototag1.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Casey White on 10/26/2015.
 */
public class TestDataFactory {
    public static String playerName_1 = "Casey";
    public static String playerName_2 = "Victor";
    public static String playerName_3 = "Kero";
    public static String playerName_4 = "Keith";
    public static String playerName_5 = "Jordan";
    public static String playerName_6 = "Syed";

    public static String[] aPlayerNames = {playerName_1,playerName_2,playerName_3,playerName_4,playerName_5,playerName_6};
    public static List<String> playerNames = Arrays.asList(aPlayerNames);

    public static  HashMap<String, Player> players = new LinkedHashMap<>(6);

    public static  HashMap<String, Player> playersLite = new LinkedHashMap<>(6);

    public static HashMap<String, Player> getPlayers() {
        if(players.size()==0){
            buildTestPlayers();
        }
        return players;
    }

    public static HashMap<String, Player> getPlayersLite() {
        return playersLite;
    }

    public static void buildTestPlayers(){
        players.put(playerName_1, new Player(playerName_1));
        players.put(playerName_2, new Player(playerName_2));
        players.put(playerName_3, new Player(playerName_3));
        players.put(playerName_4, new Player(playerName_4));
        players.put(playerName_5, new Player(playerName_5));
        players.put(playerName_6, new Player(playerName_6));
    }
    public static void buildTestPlayersLite(){
        players.put(playerName_1, new Player(playerName_1));
        players.put(playerName_2, new Player(playerName_2));
        players.put(playerName_3, new Player(playerName_3));
        players.put(playerName_4, new Player(playerName_4));
        players.put(playerName_5, new Player(playerName_5));
        players.put(playerName_6, new Player(playerName_6));
    }

}
