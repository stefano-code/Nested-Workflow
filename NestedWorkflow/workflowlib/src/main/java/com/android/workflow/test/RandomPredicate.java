package com.android.workflow.test;

import com.android.workflow.Predicate;

import java.util.Random;

public class RandomPredicate implements Predicate<Void>
{
    Random rd = new Random(); // creating Random object
    @Override
    public boolean test(Void input) {
        boolean randomBoolean = rd.nextBoolean();
        System.out.println( "randomBoolean" + randomBoolean) ;
        return randomBoolean;
    }
}
