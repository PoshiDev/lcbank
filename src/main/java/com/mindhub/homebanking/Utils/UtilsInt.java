package com.mindhub.homebanking.Utils;

public class UtilsInt {

    public static int randomNumberInt(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

}
