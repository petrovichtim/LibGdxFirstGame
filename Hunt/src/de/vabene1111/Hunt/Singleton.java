package de.vabene1111.Hunt;

import java.util.ArrayList;

public class Singleton {
    private static Singleton mInstance = null;
    
    static ArrayList<Integer> multiplier;
    
    private Singleton(){
        multiplier = new ArrayList<Integer>();
    }
 
    public static Singleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new Singleton();
        }
        return mInstance;
    }
}