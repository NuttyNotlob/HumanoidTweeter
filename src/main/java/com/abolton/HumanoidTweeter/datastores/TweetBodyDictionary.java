package com.abolton.HumanoidTweeter.datastores;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TweetBodyDictionary {

    public String gamerIntros() {
        List<String> intros = new ArrayList<>();
        intros.add("Fellow Gamers!"); // 14 chars
        intros.add("Game aficionados,"); // 17 chars
        intros.add("My netizens,"); // 12 chars
        intros.add("To my e-grills,"); // 15 chars
        intros.add("My PvP Pals,"); // 12 chars
        intros.add("Quickscope alert!"); // 17 chars
        intros.add("Speedhacks engaged!"); // 19 chars
        intros.add("Noobs beware!"); // 13 chars
        intros.add("Stat smurfers,"); // 14 chars

        return intros.get(new Random().nextInt(intros.size()));
    }

    public String gamerOutros() {
        List<String> outros = new ArrayList<>();
        outros.add(". Peace out!");
        outros.add(". Later virgins");
        outros.add(". Try and out-chad that!");
        outros.add("! I am now a no-lifer..");
        outros.add("! Behold your god");
        outros.add("! You wish you were me");
        outros.add(". Maybe I should stream??");
        outros.add(". Back to Runescape I go");
        outros.add(", the grind is real!!");
        outros.add(" like many of my fellow gamers");
        outros.add(", so happy to join y'all");
        outros.add(", 'tis a great day!");
        outros.add(", care to join me?");

        return outros.get(new Random().nextInt(outros.size()));
    }

}
