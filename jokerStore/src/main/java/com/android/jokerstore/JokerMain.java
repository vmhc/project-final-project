package com.android.jokerstore;

import java.util.ArrayList;

public class JokerMain {

    static ArrayList<String> storeJoker = new ArrayList<>();

    public static String randomJoker(){
        initStore();
        return storeJoker.get((int)(Math.random()*storeJoker.size()));
    }

    private static void initStore(){
        storeJoker.add("joker 0");
        storeJoker.add("joker 1");
        storeJoker.add("joker 2");
        storeJoker.add("joker 3");
        storeJoker.add("joker 4");
        storeJoker.add("joker 5");
        storeJoker.add("joker 6");
        storeJoker.add("joker 7");
        storeJoker.add("joker 8");
        storeJoker.add("joker 9");
    }


}
