package com.abolton.HumanoidTweeter.datastores;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TweetBodyDictionary {
    // This entire class is created just to store some intros & outros for the different tweet types, to give the tweets
    // a bit of flavour and randomisation past the information they receive from the APIs. The tweet constructor calls
    // these methods, which return a randomised intro/outro to include in the tweet

    public String gamerIntros() {
        List<String> intros = new ArrayList<>();
        intros.add("Fellow Gamers!"); // 14 chars
        intros.add("Game aficionados,"); // 17 chars
        intros.add("My netizens,"); // 12 chars
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
        outros.add(". Read it and weep");

        return outros.get(new Random().nextInt(outros.size()));
    }

    public String youtubeIntros() {
        List<String> intros = new ArrayList<>();
        intros.add("Fellow Tubers!");
        intros.add("What's up YouTube,");
        intros.add("Tubes up!");
        intros.add("Subscribing now!");
        intros.add("To all the haters");
        intros.add("Commenting for the algorithm,");
        intros.add("HD ALERT!");
        intros.add("Watching non-stop");

        return intros.get(new Random().nextInt(intros.size()));
    }

    public String youtubeOutros() {
        List<String> outros = new ArrayList<>();
        outros.add(". Later Sk8ers!");
        outros.add(". Cannae stop watching");
        outros.add("! Give me more plox");
        outros.add("! Just inject it straight into my veins!");
        outros.add("! Subbing as we speak");
        outros.add(". Maybe I should give this a go?");
        outros.add(". It's no Pewdiepie but it'll do");
        outros.add(", scared the life outta me!");
        outros.add(", reaction vid incoming!");
        outros.add(", maybe we should watch party?");
        outros.add(", getting with that Youtube and Chill");

        return outros.get(new Random().nextInt(outros.size()));
    }

    public String movieIntros() {
        List<String> intros = new ArrayList<>();
        intros.add("Film enthusiasts!");
        intros.add("Cultured followers,");
        intros.add("Movie alert!");
        intros.add("Just gone to the theatre,");
        intros.add("Popcorn's out!");
        intros.add("Got my snacks ready,");
        intros.add("What a watch!");
        intros.add("Consider this,");

        return intros.get(new Random().nextInt(intros.size()));
    }

    public String movieOutros() {
        List<String> outros = new ArrayList<>();
        outros.add(". What other film was the main person in?");
        outros.add(". Praying for a sequel");
        outros.add("! Consider me satisfied!");
        outros.add("! Film of the year for me");
        outros.add("! Film of the decade 100%");
        outros.add(". See you at the Oscars!!");
        outros.add(". Where was Liam Neeson, thought he was in it?");
        outros.add(", pure fright-fest but loved it!");
        outros.add(", should I do a review?");
        outros.add(", get it on Netflix my dudes!");
        outros.add(", the perfect chinchilla movie");

        return outros.get(new Random().nextInt(outros.size()));
    }
}
