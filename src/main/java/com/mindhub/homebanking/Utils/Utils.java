package com.mindhub.homebanking.Utils;

public class Utils {

    public static long randomNumber(long min, long max){
        return (long) ((Math.random() * (max - min)) + min);
    }
}
